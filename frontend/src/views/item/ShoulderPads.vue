<template>
    <img class="shoulderPadTop" :src="shoulderTopImg" alt=""/>
  <hr>
  <div class="page-container">
    <div class="sidebar">
      <h4>브랜드</h4>
      <ul><a href="#" @click="getProductList">전체보기</a></ul>
      <div v-for="brand in brandList">
        <ul><a href="#" @click="searchByBrand(brand.pbrand)">{{ brand.pbrand }} ({{ brand.brandCount }})</a></ul>
      </div>
      <br>
      <h4>색상</h4>
      <ul><a href="#" @click="getProductList">전체보기</a></ul>
      <div v-for="color in colorList">
        <ul><a href="#" @click="searchByColor(color.pcolor)">{{ color.pcolor }} ({{ color.colorCount }})</a></ul>
      </div>

    </div>
    <div class="content">
      <div class="card-wrapper">
        <div class="card" v-for="product in filteredProductList" :key="product.pid"
             @click="goToProductDetail(product.pid)">
          <div class="image-wrapper">
            <img :src="getFirstImageURL(product)" alt=""/>
            <i v-if="product.liked" :class="getHeartIconClass(product)"
               @click="toggleHeart(product); stopPropagation($event)"></i>
            <i v-else :class="getHeartIconClass(product)" @click="toggleHeart(product); stopPropagation($event)"></i>
          </div>
          <div class="card-body">
            <div class="card-text">
              <!-- 기타 상품 정보 -->
              <p>{{ product.pbrand }}</p>
              <p>{{ product.pname }}</p>
              <p>{{ product.pcolor }}</p>
              <h5><strong>{{ formatPrice(product?.pprice) }}원</strong></h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import shoulderTopImg from "@/assets/shoulderTopImg.png";

import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from 'vue-router';
import store from "@/store/store";

const router = useRouter();
const authority = computed(() => store.getters.getAuthority);
const memberId = computed(() => store.getters.getMemberId);
const categoryId = 2;
const brandList = ref([])
const colorList = ref([])

const stopPropagation = (event) => {
  event.stopPropagation();
}

const getHeartIconClass = (product) => {
  return product.liked
      ? 'fa-solid fa-heart fa-2xl'
      : 'fa-regular fa-heart fa-2xl';
};

const toggleHeart = async (product) => {
  console.log("toggleHeart")
  const liked = !product.liked;

  try {
    const response = await axios.post('/api/products/insertLike', {
      memberId: memberId.value,
      productId: product.pid,
      liked: liked,
    });

    if (response.status === 200) {
      console.log(productList.value)
      const foundProduct = productList.value.find(p => p.pid === product.pid);
      console.log("foundProduct", foundProduct)
      if (foundProduct) {
        foundProduct.liked = true; // 상품 객체의 liked 프로퍼티 업데이트
      }
    } else {
      alert('좋아요 토글 실패');
    }
  } catch (error) {
    if (memberId.value === null) {
      const result = confirm("로그인 하시겠습니까?")
      if (result === true) {
        await router.push("/login")
      } else {
        location.reload()
      }
    } else {
      alert("좋아요 기능 오류 입니다.")
    }
  }
};

const getColorList = () => {
  const categoryId = 2;
  axios.post("/api/products/getColorList", {
    categoryId: categoryId,
  })
      .then((response) => {
        colorList.value = response.data
      })
      .catch((error) => {
        if(error) {
          alert("색상 불러오기 오류입니다.")
        }
      })
}

const getBrandList = () => {
  const categoryId = 2;
  axios.post("/api/products/getBrandList", {
    categoryId: categoryId,
  })
      .then((response) => {
        brandList.value = response.data
        console.log(response.data)
      })
      .catch((error) => {
        if (error) {
          alert(error)
        }
      })
}

const goToProductDetail = (pId) => {
  router.push(`/productDetail/${pId}`);
};

const filteredProductList = computed(() => {
  return productList.value.filter(product => product.categoryId === 2);
});

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


const productList = ref([]);

const getFirstImageURL = (product) => {
  if (product.fileName && product.fileName.length > 0) {
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${product.pid}/${product.fileName[0]}`;
  }
  return ''; // Return empty string if no image available
};


const getProductList = async () => {
  try {
    const likedProductsResponse = await axios.post('/api/products/likedProducts', {
          memberId: memberId.value || null
        }
    );

    const likedProducts = likedProductsResponse.data;

    const response = await axios.post('/api/products/item/getList', {
      categoryId: categoryId
    }, {
      headers: {"Content-Type": "application/json"}
    });
    const productListData = response.data;

    // 좋아요를 누른 상품들의 productId 목록과 productListData를 비교하여 liked 값을 설정
    productList.value = productListData.map(product => ({
      ...product,
      liked: likedProducts.includes(product.pid)
    }));
    console.log(productList.value);
  } catch
      (error) {
    console.error(error);
  }
};

const searchByBrand = (brand) => {
  console.log(brand);
  axios.post('/api/products/item/searchByBrand', {
    categoryId: categoryId,
    brand: brand,
    memberId: memberId.value || null // memberId가 없는 경우 null로 설정
  })
      .then(async (response) => {
        const likedProductsResponse = await axios.post('/api/products/likedProducts', {
          memberId: memberId.value || null
        });

        const likedProducts = likedProductsResponse.data;

        const productsWithLikedStatus = response.data.map(product => ({
          ...product,
          liked: likedProducts.includes(product.pid)
        }));

        productList.value = productsWithLikedStatus;
        console.log("searchByBrand", productsWithLikedStatus);
      })
      .catch((error) => {
        if (error) {
          alert("브랜드별 상품 가져오기 오류입니다.");
        }
      });
};

const searchByColor = (color) => {
  console.log(color);
  axios.post('/api/products/item/searchByColor', {
    categoryId: categoryId,
    color: color,
    memberId: memberId.value || null // memberId가 없는 경우 null로 설정
  })
      .then(async (response) => {
        const likedProductsResponse = await axios.post('/api/products/likedProducts', {
          memberId: memberId.value || null
        });

        const likedProducts = likedProductsResponse.data;

        const productsWithLikedStatus = response.data.map(product => ({
          ...product,
          liked: likedProducts.includes(product.pid)
        }));

        productList.value = productsWithLikedStatus;
        console.log("searchByColor", productsWithLikedStatus);
      })
      .catch((error) => {
        if (error) {
          alert("색상별 상품 가져오기 오류입니다.");
        }
      });
};

onMounted(() => {
  getProductList()
  getColorList()
  getBrandList()
  if (authority.value === 2) {
    router.push("/");
  }

});

</script>

<style scoped>

.fa-solid {
  color: #db2424;
}

.shoulderPadTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  width: 60%;
  max-height: 800px;
}

.page-container {
  display: flex;
}

.sidebar {
  width: 20%; /* 좌측 사이드바 너비 조정 */
  background-color: white; /* 사이드바 배경색 */
  margin-left: 15px;
  border-right: .5px solid #000; /* 실선 색상 설정 */
}

.content {
  flex: 1; /* 남은 영역을 채우기 위해 설정 */
  margin-left: 10px;
}

.shoulderPadTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  max-width: 80%;
  max-height: 800px;
}

.card-wrapper {
  display: flex;
  flex-wrap: wrap;
  cursor: pointer;

}

.card {
  margin: 5px;
  flex: 1 0 25%;
  max-width: 24%;
  padding: 6px;
  box-sizing: border-box;
}

/* Add a fixed height to the image wrapper */
.image-wrapper {
  height: 300px; /* Adjust this value as needed */
  overflow: hidden;
  display: flex; /* Add display: flex; to create a flex container */
  align-items: center; /* Center the content vertically */
  justify-content: center;
  position: relative;
}
.fa-heart {
  position: absolute;
  top: 20px; /* Adjust the value to move the heart down */
  right: 10px; /* Adjust the value to move the heart to the left */
}

/* Make the image cover the entire image wrapper without distortion */
.image-wrapper img {
  width: 80%;
  height: 80%;
  object-fit: cover;
}
</style>