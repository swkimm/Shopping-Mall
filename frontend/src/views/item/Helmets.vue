<template>
  <h1>여기는 헬멧 페이지</h1>
  <div>
    <img class="helmetTop" :src="helmetTopImg1"/>
  </div>

  <hr>
  <div class="page-container">
    <div class="sidebar">
      <h1>사이드바</h1>

    </div>
    <div class="content">
      <h1>상품 리스트</h1>

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
              <p>{{ product.pprice }}원</p>
              <button>장바구니</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


</template>

<script setup>
import helmetTopImg1 from "@/assets/helmetTopImg1.jpeg";
import helmetSample1 from "@/assets/helmetSample1.png";
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from 'vue-router';

const router = useRouter();

const goToProductDetail = (pId) => {
  router.push(`/product/detail/${pId}`);
};

const filteredProductList = computed(() => {
  return productList.value.filter(product => product.categoryId === 1);
});

const productList = ref([]);

const getFirstImageURL = (product) => {
  if (product.fileName && product.fileName.length > 0) {
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${product.pid}/${product.fileName[0]}`;
  }
  return ''; // Return empty string if no image available
};

const helmetCategoryId = 1;

const getProductList = async () => {
  try {
    const response = await axios.post('/api/products/helmet/getList', {helmetCategoryId: helmetCategoryId}, {
      headers: {"Content-Type": "application/json"} // 수정: 객체 형태로 전달되어야 함
    });
    productList.value = response.data;
    console.log(productList.value);
    console.log(helmetCategoryId);
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  getProductList()
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
  max-width: 80%;
  max-height: 800px;
}

.card-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.card {
  margin: 10px;
  flex: 1 0 25%;
  max-width: 23%;
  padding: 6px;
  box-sizing: border-box;
}

/* Add a fixed height to the image wrapper */
.image-wrapper {
  height: 250px; /* Adjust this value as needed */
  overflow: hidden;
}

/* Make the image cover the entire image wrapper without distortion */
.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

</style>