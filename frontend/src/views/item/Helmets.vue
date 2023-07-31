<template>

  <div>
    <img class="helmetTop" :src="helmetTopImg"/>
  </div>

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
        <div class="card" v-for="product in filteredProductList" @click="goToProductDetail(product.pid)">
          <div class="image-wrapper">
            <img :src="getFirstImageURL(product)" alt=""/>
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
import helmetTopImg from "@/assets/helmetTopImg.png";
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from 'vue-router';
import store from "@/store/store";

const router = useRouter();
const authority = computed(() => store.getters.getAuthority);
const categoryId = 1;
const brandList = ref([])
const colorList = ref([])

const getColorList = () => {
  const categoryId = 1;
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
  const categoryId = 1;
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
  return productList.value.filter(product => product.categoryId === 1);
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
    const response = await axios.post('/api/products/item/getList', {categoryId: categoryId}, {
      headers: {"Content-Type": "application/json"} // 수정: 객체 형태로 전달되어야 함
    });
    productList.value = response.data;
    console.log(productList.value);
  } catch (error) {
    console.error(error);
  }
};

const searchByBrand = (brand) => {
  console.log(brand)
  axios.post('/api/products/item/searchByBrand', {
    categoryId: categoryId,
    brand: brand,
  })
      .then((response) => {
        productList.value = response.data;
        console.log("searchByBrand", response.data)
      })
      .catch((error) => {
        if (error) {
          alert("브랜드별 상품 가져오기 오류입니다.")
        }
      })
}

const searchByColor = (color) => {
  console.log(color)
  axios.post('/api/products/item/searchByColor', {
    categoryId: categoryId,
    color : color,
  })
      .then((response) => {
        productList.value = response.data;
        console.log("searchByColor", response.data)
      })
      .catch((error) => {
        if (error) {
          alert("색상별 상품 가져오기 오류입니다.")
        }
      })


}

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

.helmetTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  max-width: 60%;
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

.image-wrapper {
  height: 300px; /* Adjust this value as needed */
  overflow: hidden;
  display: flex; /* Add display: flex; to create a flex container */
  align-items: center; /* Center the content vertically */
  justify-content: center;
}

/* Make the image cover the entire image wrapper without distortion */
.image-wrapper img {
  width: 80%;
  height: 80%;
  object-fit: cover;
}

</style>