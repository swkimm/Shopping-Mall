<template>
  <h1> {{ pId }}번 상품 수정 페이지</h1>
  <div class="container-lg">
    <div class="row justify-content-center">
      <div class="col-12 col-md-10 col-lg-8">
        <h1>상품 수정</h1>

        <div class="mb-3">
          <label class="form-label">카테고리</label>
          <input v-model="product.categoryName" class="form-control" type="text" readonly/>
        </div>

        <div class="mb-3">
          <label class="form-label">상태</label>
          <select class="form-select" v-model="product.pstatus">
            <option value="기본">기본</option>
            <option value="품절">품절</option>
            <option value="이벤트">이벤트</option>
            <option value="할인">할인</option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label">상품명</label>
          <input v-model="product.pname" class="form-control" type="text"/>
        </div>
        <div class="mb-3">
          <label class="form-label">가격</label>
          <input v-model="product.pprice" class="form-control" type="text"/>
        </div>
        <div class="mb-3">
          <label class="form-label">재고</label>
          <input v-model="product.pstock" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">브랜드</label>
          <input v-model="product.pbrand" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">설명</label>
          <textarea v-model="product.pdesc" class="form-control" type="text"></textarea>
        </div>

        <div class="mb-3">
          <label class="form-label">사이즈</label>
          <input v-model="product.psize" class="form-control" type="text"/>
        </div>

        <div class="mb-3">
          <label class="form-label">색상</label>
          <input v-model="product.pcolor" class="form-control" type="text"/>
        </div>

        <!-- 유튜브 링크 -->
        <div class="mb-3">
          <label class="form-label">유튜브 링크</label>
          <div v-for="(link, index) in product.linkName" :key="index">
            <input class="col-lg-10" v-model="product.linkName[index]" type="text"/>
            <button @click="deleteLink(link)" class="fa-solid fa-trash" style="background-color: red"></button>
          </div>
          <div v-if="showNewLinkInput">
            <input v-model="newLinkName" type="text" class="col-lg-10"/>
            <button @click="addLink" class="fa-solid fa-check"></button>
            <br>
          </div>
          <button @click="newLink">링크추가</button>
        </div>

        <!-- 파일 등록  -->
        <!-- 체크 박스 눌린것들 상품 수정 버튼 누르면 삭제되도록해야됨, 그리고 이미지 추가된것들도 추가되도록       -->
        <div class="mb-3">
          <div v-for="(fileName, index) in product.fileName" :key="index">
            <label :for="'removeCheckBox' + index">이미지</label><br>
            <button @click="deleteImg(fileName)" class="fa-solid fa-trash" style="background-color: red"></button>
            <div class="mb-3">
              <img class="img-fluid img-thumbnail"
                   :src="`https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${pId}/${fileName}`"
                   alt=""/>
            </div>
          </div>
          <input class="form-control" type="file" multiple name="files" accept="image/*"
                 v-on="productImg" ref="productImg">

          <div class="form-test">
            업로드 최대 크기 10MB, 한 개의 파일 1MB를 초과할 수 없습니다.
          </div>
        </div>

        <div class="mb-3">
          <button class="btn btn-secondary" @click="updateProduct">상품 수정</button>
        </div>
      </div>
    </div>
  </div>


</template>

<script setup>

import axios from "axios";
import {onMounted, defineProps, ref, reactive, watch} from "vue";
import router from "@/router";

const props = defineProps({
  pId: {
    type: [Number, String],
    required: true
  },
});

const product = reactive({});
const productImg = ref([]);
const youtubeLink = ref([]);
const newLinkName = ref('');
const showNewLinkInput = ref(false);


watch(() => product.linkName, (newValue, oldValue, index) => {
  console.log('유튜브 링크 변경 감지:');
  console.log('이전 값:', oldValue);
  console.log('새 값:', newValue);
  console.log('변경된 값의 인덱스:', index);
});

const newLink = () => {
  showNewLinkInput.value = true
}

// const product = reactive({
//   pName: '',
//   pPrice: '',
//   pStock: '',
//   pBrand: '',
//   pDesc: '',
//   pSize: '',
//   pColor: '',
//   fileName: []
// });

const addLink = () => {
  console.log(newLinkName.value)
  const youtubeReg = "https://www.youtube.com/embed/";
  if (newLinkName.value.indexOf(youtubeReg) === 0) {
    axios.post(`/api/products/addLink/${props.pId}`, {
      pId : props.pId,
      linkName : newLinkName.value
    })
        .then((response) => {
          console.log(response.data)
          location.reload()
        })
  } else {
    alert("정상적인 유튜브 링크를 입력하세요");
  }
}

const deleteLink = (linkName) => {
  console.log(linkName)
  axios.post(`/api/products/deleteLink/${props.pId}`, {
    pId: props.pId,
    linkName: linkName
  })
      .then((response) => {
        alert("링크가 삭제되었습니다.")
        location.reload()
      })
      .catch((error) => {
        if (error) {
          alert("링크 삭제 시 오류가 발생하였습니다.")
        }
      })
}

const deleteImg = (fileName) => {
  console.log(fileName)
  axios.post(`/api/products/deleteImg/${props.pId}`, {
    pId: props.pId,
    fileName: fileName
  })
      .then((response) => {
        alert("이미지가 삭제되었습니다.")
        location.reload()
      })
      .catch((error) => {
        if (error) {
          alert("이미지 삭제 시 오류가 발생하였습니다.")
        }
      })
}

const getProduct = () => {
  axios
      .post(`/api/products/getProduct/${props.pId}`, {
        pId: props.pId,
      })
      .then((response) => {
        if (response && response.data) {
          Object.assign(product, response.data);
          console.log(product);
        } else {
          alert("상품 데이터를 가져오지 못했습니다.");
        }
      })
      .catch((error) => {
        console.error(error);
        alert("상품 불러오기 오류입니다.");
      });
};

// const updateProduct = async () => {
//   const requestData = {
//     pName: product.pname,
//     pPrice: product.pprice,
//     pStock: product.pstock,
//     pBrand: product.pbrand,
//     pDesc: product.pdesc,
//     pSize: product.psize,
//     pColor: product.pcolor,
//   };
//
//   try {
//     const response = await axios.post(
//         `/api/products/updateProduct/${props.pId}`,
//         requestData,
//         {
//
//         }
//     );
//
//     console.log(response.data);
//     console.log(product);
//
//     alert("상품이 수정되었습니다.");
//   } catch (error) {
//     if (error) {
//       console.log(error.response.data.message);
//       alert("상품 수정 실패입니다.");
//     }
//   }
// };


const updateProduct = async () => {
  const updateData = new FormData();
  updateData.append("pName", product.pname)
  updateData.append("pStatus", product.pstatus)
  updateData.append("pPrice", product.pprice)
  updateData.append("pStock", product.pstock)
  updateData.append("pBrand", product.pbrand)
  updateData.append("pDesc", product.pdesc)
  updateData.append("pSize", product.psize)
  updateData.append("pColor", product.pcolor)

  if (youtubeLink.value.length > 0) {
    for (let i = 0; i < youtubeLink.value.length; i++) {
      updateData.append('linkName', youtubeLink.value[i]);
    }
    ;
  }

  if (productImg.value && productImg.value.files && productImg.value.files.length > 0) {
    for (let i = 0; i < productImg.value.files.length; i++) {
      updateData.append('files', productImg.value.files[i]);
    }
  }

  try {
    const response = await axios.post(
        `/api/products/updateProduct/${props.pId}`, updateData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        });
    console.log(response.data);
    console.log(product);

    alert('상품이 수정되었습니다.');
    location.reload();

  } catch (error) {
    if (error.response && error.response.data && error.response.data.message) {
      console.log(error.response.data.message);
    } else {
      console.log(error);
    }
    alert('상품 수정 실패입니다.');
  }
};


onMounted(() => {
  getProduct();
})

</script>


<style scoped>

</style>