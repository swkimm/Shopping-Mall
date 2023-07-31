<template>
  <h1>상품디테일 {{ props.pId }}</h1>

  <div class="container text-center">
    <div class="row">
      <div class="col">
        <div class="mb-3">
          <img :src="selectedImage" class="img-fluid" style="width: 40rem" alt=""/>
        </div>
        <div class="mb-3">
          <div class="image-grid">
            <img v-for="(fileName, index) in product.fileName" :key="index"
                 :src="getThumbnailImageURL(fileName)"
                 class="thumbnail-image"
                 alt=""
                 @click="selectImage(fileName)"
            />
          </div>
        </div>
      </div>
      <div class="col productInfo">
        <h2>{{ product.pbrand }} {{ product.pname }}</h2><br>
        <hr>
        <br>
        <h5><strong>가격: {{ formatPrice(product?.pprice) }}원</strong></h5>
        <br>
        <br>
        <h5>색상 : {{ product.pcolor }}</h5>
        <h5>사이즈 : {{ product.psize }}</h5>
        <h5>브랜드 : {{ product.pbrand }}</h5><br>
        <h5>수량</h5>
        <select class="form-select" v-model="quantity" style="width: 100px;">
          <option v-for="n in 10" :key="n" :value="n">{{ n }}개</option>
        </select>
        <br/><br/>
        <button @click="addToCart(product.pid, quantity)">장바구니 추가</button>
      </div>
      <div>
        <hr>
        <h1>상세 설명</h1><br>
        <p style="text-align: left">{{ product.pdesc }}</p><br>
        <div v-for="(videoLink, index) in youtubeLink" :key="index">
          <iframe width="700" height="350" :src="videoLink.linkName" title="YouTube video player"
                  frameborder="0"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                  allowfullscreen></iframe>

        </div>
        <hr>
        <h1>추천 상품</h1>
        <div class="product-container">
          <div class="arrow-btn left-arrow" @click="prevPage" v-if="currentPage > 0">
            <i class="fa-solid fa-angle-left fa-2xl"></i>
          </div>
          <div class="card-wrapper">
            <div class="card" v-for="(event, index) in visibleEvents" :key="event.pid"
                 @click="goToProductDetail(event.pid)">
              <div v-if="props.pId !== event.pid">
                <div class="image-wrapper">
                  <img :src="getFirstImageURL(event)" alt="">
                </div>
                <div class="card-body">
                  <div class="card-text" style="text-align: left">
                    <p>{{ event.pbrand }} {{ event.pname }}</p>
                    <h5><strong>{{ formatPrice(event?.pprice) }}원</strong></h5>
                  </div>
                </div>
              </div>
            </div>
            <div class="arrow-btn right-arrow" @click="nextPage" v-if="showNextArrow">
              <i class="fa-solid fa-angle-right fa-2xl"></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import {useRouter} from 'vue-router';
import {computed, defineProps, onMounted, ref, watch} from "vue";
import store from "@/store/store";

const router = useRouter();
const user = computed(() => store.state.user);
const memberId = computed(() => store.getters.getMemberId);

const props = defineProps({
  pId: {
    type: [Number, String],
    required: true
  },
});

let key = 0; // 초기 키 값

const product = ref({});
const selectedImage = ref('');
const youtubeLink = ref([]);
const eventProduct = ref([]);
const quantity = ref('1');
const isInResult = ref(0);
const itemsPerPage = 4;

// Current page number
const currentPage = ref(0);
const currentProductId = computed(() => {
  return props.pId;
});

const visibleEvents = computed(() => {
  const startIndex = currentPage.value * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;

  return eventProduct.value.filter((event) => event.pid !== props.pId).slice(startIndex, endIndex);
});


// Computed property to determine whether to show the right arrow button
const showNextArrow = computed(() => {
  const totalPages = Math.ceil(eventProduct.value.length / itemsPerPage);
  return currentPage.value < totalPages - 1;
});

// Function to go to the previous page
const prevPage = () => {
  currentPage.value = Math.max(0, currentPage.value - 1);
};

// Function to go to the next page
const nextPage = () => {
  currentPage.value = Math.min(currentPage.value + 1, Math.ceil(eventProduct.value.length / itemsPerPage) - 1);
};

function addCommasToNumber(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

// 가격을 1000 단위 콤마(,)가 포함된 형식으로 변환하는 메소드
function formatPrice(price) {
  if (price) {
    return addCommasToNumber(price);
  } else {
    return ''; // or any default value you prefer when the price is not available
  }
}

const isInCart = async () => {
  try {
    const response = await axios.post(`/api/cart/isInCart/${props.pId}`, {
      memberId: memberId.value
    });
    isInResult.value = response.data;
    console.log("isInCart", response.data);
    return isInResult.value === 1; // Return true if the product is in the cart
  } catch (error) {
    alert("장바구니에 동일한 상품이 있는지 체크 오류입니다.");
    return false;
  }
};

const addToCart = async (pId, quantity) => {
  if (!user.value) {
    const result = confirm("로그인 하시겠습니까?");
    if (result === true) {
      await router.push('/login');
    } else {
      location.reload();
    }
  } else {
    try {
      // Check if the product exists in the cart
      const isProductInCart = await isInCart();

      // If the product is not in the cart, proceed to add it
      if (!isProductInCart) {
        const response = await axios.post(`/api/cart/addToCart/${props.pId}`, {
          pId: pId,
          productQty: quantity,
          memberId: memberId.value
        });

        const result = confirm("상품이 장바구니에 추가되었습니다. 장바구니로 이동할까요?");
        if (result === true) {
          await router.push("/cart");
        } else {
          // Do nothing or show an alternative message if needed.
        }
      } else {
        // Product already in the cart, show confirmation to redirect
        const res = confirm("동일 상품이 장바구니에 존재합니다. 장바구니로 이동하시겠습니까?");
        if (res === true) {
          await router.push("/cart");
        } else {
          location.reload();
        }
      }
    } catch (error) {
      alert("상품을 장바구니에 추가하지 못했습니다.");
    }
  }
};
const goToCart = () => {
  router.push("/cart")
}

const goToProductDetail = (newPId) => {
  console.log(newPId)
  router.push(`/productDetail/${newPId}`);
}

const getFirstImageURL = (product) => {
  if (product.fileName && product.fileName.length > 0) {
    const fileName = encodeURIComponent(product.fileName[0]);
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${product.pid}/${fileName}`;
  }
  return ''; // Return empty string if no image available
};


const event = ref("이벤트")
const getEventProductList = () => {
  console.log(event.value);
  axios
      .post("/api/products/getEventProductList", {
        event: event.value
      })
      .then((response) => {
        // Filter out the current product from the eventProduct list
        eventProduct.value = response.data;
        console.log(response.data);
      })
      .catch((error) => {
        if (error) {
          alert("추천 상품 불러오기 실패입니다.");
        }
      });
};

const getProductYoutubeLinkList = () => {
  axios.post(`/api/products/getYoutubeLinkList/${props.pId}`)
      .then((response) => {
        youtubeLink.value = response.data;
        console.log(response.data);
      })
}

const getProductDetail = () => {
  axios
      .post(`/api/products/getProduct/${props.pId}`)
      .then((response) => {
        product.value = response.data;
        console.log(response.data);
        // 첫 번째 이미지를 선택된 이미지로 설정합니다
        selectedImage.value = getOriginalImageURL(product.value.fileName[0]);
      })
      .catch((error) => {
        // 에러 처리 로직
        console.error(error);
      });
};

const getOriginalImageURL = (fileName) => {
  return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${props.pId}/${fileName}`;
};

const getThumbnailImageURL = (fileName) => {
  return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${props.pId}/${fileName}`;
};

const selectImage = (fileName) => {
  selectedImage.value = getOriginalImageURL(fileName);
};

const scrollToTop = () => {
  window.scrollTo(0, 0);
};

onMounted(() => {
  getProductDetail();
  getProductYoutubeLinkList();
  getEventProductList();
  scrollToTop();
});

watch(() => props.pId, (newValue) => {
  getProductDetail(newValue);
});

</script>


<style scoped>
.productInfo {
  text-align: left;
}

.thumbnail-image {
  width: 80px; /* 작은 이미지의 가로 크기를 조정할 수 있습니다 */
  height: 80px; /* 작은 이미지의 세로 크기를 조정할 수 있습니다 */
  object-fit: cover; /* 이미지 비율 유지 및 이미지가 잘리지 않도록 설정합니다 */
  object-position: center; /* 이미지를 가운데로 정렬합니다 */
  cursor: pointer; /* 마우스 포인터를 손가락 형태로 변경하여 클릭 가능함을 나타냅니다 */
  margin-right: 10px; /* 작은 이미지 사이의 간격을 설정합니다 */
}

.card-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.card {
  margin: 10px;
  flex: 1 0 25%;
  max-width: 20%;
  padding: 10px;
  box-sizing: border-box;
}

/* Add a fixed height to the image wrapper */
.image-wrapper {
  height: 150px; /* Adjust this value as needed */
  overflow: hidden;
}

/* Make the image cover the entire image wrapper without distortion */
.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-container {
  display: flex;
  align-items: center;
}

.card-wrapper {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.arrow-btn {
  font-size: 20px;
  padding: 5px 10px;
  cursor: pointer;
  background-color: transparent;
  border: none;
  display: flex;
  align-items: center;
}

.left-arrow {
  margin-right: auto;
}

.right-arrow {
  margin-left: auto;
}

/* Center-align the visible cards when alignCenter is true */
.center-align .card {
  margin-left: auto;
  margin-right: auto;
}


</style>
