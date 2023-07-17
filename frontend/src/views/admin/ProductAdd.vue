<template>
  <div class="container-lg">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <h1>상품 등록</h1>
        <div class="mb-3">
          <p>카테고리</p>
          <select class="form-select" v-model="categoryName">
            <option v-for="category in categories" :key="category.id" :value="category.categoryId">{{
                category.categoryName
              }}
            </option>
          </select>
        </div>

        <div class="mb-3">
          <label class="form-label">상품명</label>
          <input v-model="productName" class="form-control" type="text"/>
        </div>
        <div class="mb-3">
          <label class="form-label">가격</label>
          <input v-model="productPrice" class="form-control" type="text"/>
        </div>
        <div class="mb-3">
          <label class="form-label">재고</label>
          <input v-model="productStock" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">브랜드</label>
          <input v-model="productBrand" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">설명</label>
          <textarea v-model="productDesc" class="form-control" type="text"></textarea>
        </div>

        <div class="mb-3">
          <label class="form-label">사이즈</label>
          <input v-model="productSize" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">색상</label>
          <input v-model="productColor" class="form-control" type="text"/>
        </div>

        <!-- 파일 등록  -->
        <div class="mb-3">
          <label for="productImg" class="form-label">상품 이미지</label>
          <input class="form-control" type="file" id="productImg" multiple name="files" accept="image/*"
                 ref="productImg">

          <div class="form-test">
            업로드 최대 크기 10MB, 한 개의 파일 1MB를 초과할 수 없습니다.
          </div>
        </div>

        <div class="mb-3">
          <button @click="saveProduct">상품 등록</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

// 카테고리 불러오기
const categories = ref({
  categoryId: '',
  categoryName: ''
});

// 상품 저장
const categoryName = ref('');
const productName = ref('');
const productPrice = ref('');
const productStock = ref('');
const productBrand = ref('');
const productDesc = ref('');
const productSize = ref('');
const productColor = ref('');
const productImg = ref(null);

const saveProduct = async () => {
  console.log(categoryName.value)
  const registerProduct = new FormData();

  registerProduct.append("cId", categoryName.value);
  registerProduct.append("pName", productName.value);
  registerProduct.append("pPrice", productPrice.value);
  registerProduct.append("pStock", productStock.value);
  registerProduct.append("pBrand", productBrand.value);
  registerProduct.append("pDesc", productDesc.value);
  registerProduct.append("pSize", productSize.value);
  registerProduct.append("pColor", productColor.value);

  if (productImg.value && productImg.value.files.length > 0) {
    for (let i = 0; i < productImg.value.files.length; i++) {
      registerProduct.append('files', productImg.value.files[i]);
    }
  }

  try {
    const response = await axios.post("/api/products/saveProduct", registerProduct, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
    console.log(response.data);
    alert("상품이 등록되었습니다.")
    router.push('/product')
  } catch (error) {
    if (error.response) {
      alert(error.response.data.message);
    }
  }
};

const getCategoryList = async () => {
  try {
    const response = await axios.post("/api/categories/getCategoryList");
    categories.value = response.data;
  } catch (error) {
    if (error.response) {
      alert(error.response.data.message);
    }
  }
};

onMounted(() => {
  getCategoryList();
});
</script>

<style scoped>

</style>
