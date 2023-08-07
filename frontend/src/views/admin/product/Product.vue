<template>
  <br>
  <div class="container">
    <h1>상품 관리 페이지</h1>
    <div class="row">
      <div class="col">
        <select class="form-select w-25 p-1" v-model="selectedCategoryId">
          <option selected value="">전체 보기</option>
          <option v-for="category in categoryList" :key="category.categoryId" :value="category.categoryId">
            {{ category.categoryName }}
          </option>
        </select>
      </div>
      <div class="col">
        <div style="text-align: right; margin-right: 40px">
          <router-link to="/ProductAdd">
            <button class="btn btn-primary btn-lg">상품 등록</button>
          </router-link>
        </div>

      </div>
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
        <td>{{ formatPrice(product?.pprice) }}</td>
        <td>{{ product.pstock }}</td>
        <td>{{ product.psize }}</td>
        <td>{{ product.pcolor }}</td>
        <td>
          <button class="btn btn-secondary btn-m" @click="updateProduct(product.pid)">수정</button>
          <button class="btn btn-danger btn-m" @click="deleteProduct(product.pid)">삭제</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router';
import {onMounted, ref, watch} from "vue";
import axios from "axios";

const router = useRouter();
const productList = ref({});
const selectedCategoryId = ref('');
const categoryList = ref([])


const getCategoryList = () => {
  axios.post("/api/categories/getCategoryList")
      .then((response) => {
        categoryList.value = response.data
        console.log("getCategory", response.data)
      })
      .catch((error) => {
        if (error) {
          alert("카테고리 아이디 불러오기 오류 입니다.")
        }
      })
}

// 1000 단위 콤마(,)를 추가하는 함수
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
  const result = confirm("삭제 하시겠습니까?")
  if (result === true) {
    axios.post('/api/products/deleteProduct', {
      pid: pid,
    })
        .then((response) => {
          location.reload()
        })
        .catch((error) => {
          if (error.response) {
            alert("삭제 시 문제가 발생하였습니다.")
          }
        })
  } else {

  }
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

const getListWithCategoryId = (categoryId) => {
  console.log(categoryId)
  axios.post("/api/products/getListWithCategoryId", {
    categoryId : categoryId
  })
      .then((response) => {
        productList.value = response.data;
        console.log(response.data)
      })
      .catch((error) => {
        if(error) {
          alert("상품 가져오기 오류 입니다.")
        }
      })

}

watch(selectedCategoryId, (newCategoryId, oldCategoryId) => {
  // Do something with the newCategoryId, for example, filter products based on the selected category
  console.log('Selected category changed:', newCategoryId);
  if (newCategoryId === '') {
    getProductList()
  } else {
    getListWithCategoryId(newCategoryId)
  }
});

onMounted(() => {
  selectedCategoryId.value = '';
  getProductList(selectedCategoryId);
  getCategoryList()
})


</script>

<style scoped>

</style>
