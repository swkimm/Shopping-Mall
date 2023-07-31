<template>
  <br>
    <div class="container">
      <h1>장바구니</h1>
      <hr>
      <div class="row">
        <div class="col-sm-3">
          <div class="row">
            <div class="col oneContent">
              <div v-for="cart in cartList" :key="cart.id" class="image-container">
                <img style="width: 10rem;" :src="getRepresentativeImageURL(cart)" alt=""/>
              </div>
            </div>
          </div>
        </div>
        <div class="col oneContent" v-if="cartList.length > 0">
          <div v-for="cart in cartList" :key="cart.id" class="product-info">
            <h4>{{ cart.pbrand }} {{ cart.pname }} {{ cart.pcolor }}, {{ cart.psize }}</h4>
            <h6>사이즈 : {{ cart.psize }}</h6>
            <h6>색상 : {{ cart.pcolor }}</h6>
            <h5><strong>가격: {{ formatPrice(cart?.pprice) }}원</strong></h5>
            <select v-model="cart.productQty" class="fa-m">
              <option v-for="n in 10" :key="n" :value="n">{{ n }}개</option>
            </select>
            <a style="margin-left: 15px" href="#"
               @click="updateQtyInCart(cart.productId, cart.cartId, cart.productQty)">수정</a>
            <a style="margin-left: 15px" href="#"
               @click="deleteInCart(cart.productId, cart.cartId)">삭제</a>
          </div>
        </div>
        <div v-else>
          <h5>장바구니가 비었습니다</h5>
        </div>
      </div>
      <hr>
      <div v-if="cartList.length > 0">
        <h3 style="text-align: right;">총액 : {{ formatPrice(totalPriceValue) }} 원</h3>
      </div>
      <div v-else>
        <h3 style="text-align: right;">총액 : 0 원</h3>

      </div>
    </div>
    <div>
      <div class="orderBtn">
        <!-- 버튼의 disabled 속성에 cartList의 길이가 0이면 true, 그렇지 않으면 false를 설정합니다. -->
        <button @click="goToOrder" type="button" class="btn btn-primary btn-lg" :disabled="cartList.length === 0">주문하기
        </button>
      </div>
    </div>

</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import axios from "axios";
import store from "@/store/store";
import router from "@/router";

const memberId = computed(() => store.getters.getMemberId);
const authority = computed(() => store.getters.getAuthority);
const cartList = ref([]);
const totalPriceValue = ref();

const orderId = ref('');

const id = ref('');

const getOrderId = () => {
  axios.post("/api/order/getOrderId", {
    memberId: memberId.value
  })
      .then((response) => {
        orderId.value = response.data;
        // 주문 상세 정보를 저장하는 함수를 호출합니다.
        saveOrderDetails()
      })
      .catch((error) => {
        if (error) {
          alert("주문 번호 가져오기 중 오류가 발생했습니다.");
        }
      });
};

const goToOrder = () => {
  console.log("goToOrder", orderId.value)
  axios
      .post("/api/order/saveOrder", {
        memberId: memberId.value,
      })
      .then((response) => {
        orderId.value = response.data;
        saveOrderDetails()
        router.push("/order")
      })
      .catch((error) => {
        if (error) {
          alert("주문하기 실패입니다.");
        }
      });
};
const saveOrderDetails = () => {
  // Step 2: Save order details (items in the cart) to the backend
  const orderDetails = cartList.value.map((item) => ({
    orderId: orderId.value,
    memberId: memberId.value,
    productId: item.productId,
    productQty: item.productQty,
    productPrice: item.pprice,
  }));
  axios
      .post("/api/order/saveOrderDetails", {
        orderDetails: orderDetails,
      })
      .then((response) => {
        console.log("orderDetails 저장 완료");
      })
      .catch((error) => {
        if (error) {
          alert("주문하기 실패입니다.");
        }
      });
};


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

const totalPrice = () => {
  axios.post("/api/cart/totalPrice", {
    memberId: memberId.value
  })
      .then((response) => {
        totalPriceValue.value = response.data
      })
      .catch((error) => {
        if (error) {
          alert("총액 불러오기 오류입니다.")
        }
      })
}
const updateQtyInCart = (productId, cartId, productQty) => {
  console.log("updateQtyInCart", productId, cartId, productQty)
  axios.post("/api/cart/updateQtyInCart", {
    memberId: memberId.value,
    productId: productId,
    cartId: cartId,
    productQty: productQty
  })
      .then((response) => {
        console.log(response.data)
        alert("수량이 수정되었습니다.")
        location.reload()
      })
      .catch((error) => {
        if (error) {
          alert("장바구니 상품 수량 변경 오류입니다.")
        }
      })
}
const deleteInCart = (productId, cartId) => {
  // Show the confirm dialog and store the result
  const shouldDelete = confirm("상품을 삭제하시겠습니까?");

  if (shouldDelete) {
    axios.post("/api/cart/deleteInCart", {
      memberId: memberId.value,
      productId: productId,
      cartId: cartId
    })
        .then((response) => {
          console.log(response.data);
          alert("상품이 삭제되었습니다.");
          location.reload();
        })
        .catch((error) => {
          if (error) {
            alert("상품 삭제 시 오류가 발생했습니다.");
          }
        });
  }
};

const getRepresentativeImageURL = (cartList) => {
  if (cartList.fileName && cartList.fileName.length > 0) {
    const firstFileName = cartList.fileName[0];
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${cartList.pid}/${firstFileName}`;
  }
  return ''; // Return empty string if no image available
};

const getCartList = () => {
  // console.log(memberId.value)
  axios.post("/api/cart/getCartList", {
    memberId: memberId.value
  })
      .then((response) => {
        cartList.value = response.data;
      })
      .catch((error) => {
        if (error) {
          alert("장바구니 불러오기 오류입니다.")
        }
      })
}

onMounted(() => {
  getCartList()
  totalPrice()
  getOrderId()
  if (authority.value === 2) {
    router.push("/");
  }

})

</script>

<style scoped>
.image-container {
  overflow: hidden;
  margin-left: auto;
  margin-right: auto;
  width: 10rem; /* Set the width to match the desired size */
  height: 10rem; /* Set the height to match the desired size */
  margin-bottom: 10px;
}

.orderBtn {
  text-align: center;
}

/* 이미지 스타일 조정 */
.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 텍스트 스타일 조정 */
.product-info {
  padding-left: 1rem;
  height: 10rem;
  margin-bottom: 10px;

}


</style>