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
        <div class="card" style="width: 30rem;" v-for="product in productList">
<!--          <img :src="helmetSample1" class="card-img-top" alt="...">-->
          <img :src="getFirstImageURL(product)" alt=""/>
          <div class="card-body">
            <div class="card-text">
              <!-- 기타 상품 정보 -->
              <h4>{{ product.pid }}</h4>
              <h3>{{ product.pbrand }} {{ product.pname }}</h3>
              <h3>평점</h3>
              <h3>{{ product.pcolor }}</h3>
              <h3>{{ product.pprice }}</h3>
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

const productList = ref({
  pid : '',
  cid : '',
  pname : '',
  pprice : '',
  pbrand : '',
  pstatus : '',
  psize : '',
  pcolor : '',
  fileName : ''
});

const getFirstImageURL = (product) => {
  if (product.fileName && product.fileName.length > 0) {
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${product.pid}/${product.fileName[0]}`;
  }
  return ''; // Return empty string if no image available
};

const getProductList = async () => {
  try {
    const response = await axios.post('/api/products/getList'); // API 엔드포인트에 맞게 경로 수정
    productList.value = response.data;
    console.log(productList.value)
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
  padding: 20px;
  border-right: 1px solid #000; /* 실선 색상 설정 */
}

.content {
  flex: 1; /* 남은 영역을 채우기 위해 설정 */
  margin-left: 15px;
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
  //justify-content: flex-start;
}

.card {
  margin: 10px;
  flex: 1 0 25%;
  max-width: 20%;
  padding: 10px;
  box-sizing: border-box;
}

</style>