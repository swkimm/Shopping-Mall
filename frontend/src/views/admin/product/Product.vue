<template>
  <h1>상품 관리 페이지</h1>
  <!-- 기본적으로 상품 리스트가 테이블 형식으로 표현되고 해당 제품의 버튼을 눌러서 수정, 삭제되도록 만들예정
   상단에는 카테고리 드랍박스와 제품 등록 버튼 있음 -->
  <div>
    <router-link to="/ProductAdd">
      <button>상품 등록</button>
    </router-link>
  </div>
  <br>
  <table class="table">
    <thead>
    <tr>
      <th>이미지</th>
      <th>카테고리</th>
      <th>상품번호</th>
      <th>브랜드</th>
      <th>상품명</th>
      <th>가격</th>
      <th>재고</th>
      <th>사이즈</th>
      <th>색상</th>
      <th>수정/삭제</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="product in productList" :key="product.id">
      <td>
        <img style="width: 3rem; margin-right: 0.5rem;"
             :src="getRepresentativeImageURL(product)"
             alt=""/>
      </td>
      <td>{{ product.categoryName }}</td>
      <td>{{ product.pid }}</td>
      <td>{{ product.pbrand }}</td>
      <td>{{ product.pname }}</td>
      <td>{{ product.pprice }}</td>
      <td>{{ product.pstock }}</td>
      <td>{{ product.psize }}</td>
      <td>{{ product.pcolor }}</td>
      <td>
        <button @click="updateProduct(product.pid)">수정</button>
        <button @click="deleteProduct(product.pid)">삭제</button>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script setup>
import {useRouter} from 'vue-router';
import {onMounted, ref} from "vue";
import axios from "axios";

const router = useRouter();
const productList = ref({});

const productAdd = () => {
  router.push('/productAdd');
};


const getRepresentativeImageURL = (product) => {
  if (product.fileName && product.fileName.length > 0) {
    const firstFileName = product.fileName[0];
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${product.pid}/${firstFileName}`;
  }
  return ''; // Return empty string if no image available
};

const deleteProduct = (pid) => {
  console.log("삭제", pid)
  axios.post('/api/products/deleteProduct', {
    pid: pid,
  })
      .then((response) => {
        alert("삭제되었습니다.")
        location.reload()
      })
      .catch((error) => {
        if (error.response) {
          alert("삭제 시 문제가 발생하였습니다.")
        }
      })
}

const updateProduct = (pid) => {
  router.push(`/productUpdate/${pid}`);
}

const getProductList = () => {
  axios.post('/api/products/getList')
      .then((response) => {
        productList.value = response.data;
        console.log(productList.value)
      })
      .catch((error) => {
        if (error.response) {
          alert("제품 리스트를 가져올 수 없습니다.")
        }
      })
}

onMounted(() => {
  getProductList();
})


</script>

<style scoped>

</style>
