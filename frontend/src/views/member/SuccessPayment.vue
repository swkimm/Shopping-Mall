<template>

  <div class="payment-container" >
    <div class="payment-confirmation">
      <h2>결제가 완료되었습니다!</h2>
      <p>주문번호: <a href="#" @click="goToDeliveryStatus()">{{ paymentInfo.invoice }}</a></p>
        <p>결제 금액: {{ formatPrice(paymentInfo.amount) }}원</p>
    </div>
  </div>


</template>

<script setup>

import axios from "axios";
import {computed, onMounted, ref} from "vue";
import store from "@/store/store";
import router from "@/router";

const memberId = computed(() => store.getters.getMemberId);
const authority = computed(() => store.getters.getAuthority)

const paymentInfo = ref({
  invoice : '',
  amount : '',
  orderId : ''
})

const goToDeliveryStatus = () => {
  router.push("/myDeliveryStatus")
}

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

const orderId = ref('')
const getOrderId = () => {
  axios.post("/api/order/getOrderId", {
    memberId : memberId.value
  })
      .then((response) => {
        orderId.value = response.data
        getPaymentInfo()
      })
}

const getPaymentInfo = () => {
  console.log("orderId", orderId.value)
  axios.post("/api/payment/getPaymentInfo", {
    memberId : memberId.value,
    orderId : orderId.value
  })
      .then((response) => {
        paymentInfo.value = response.data
        console.log(response.data)
        deletePurchasedItemCart();
      })
      .catch((error) => {
        if(error) {
          alert("주문 번호 불러오기 오류입니다.")
        }
      })
}

const deletePurchasedItemCart = () => {
  console.log(orderId.value);
  axios
      .post("/api/cart/deletePurchasedItemCart", {
        memberId: memberId.value,
        orderId: orderId.value,
      })
      .then((response) => {
        console.log("결제 완료된 상품이 장바구니 삭제되었습니다.")
      })
      .catch((error) => {
        if (error) {
          alert("장바구니 삭제 중 오류 발생");
        }
      });
};

onMounted(() => {
  getOrderId()
  if (authority.value === 2) {
    router.push("/");
  }
})

</script>


<style scoped>
.payment-container {
  display: flex;
  justify-content: center; /* 변경된 부분: 가로 방향 가운데로 위치하도록 center로 변경 */
  align-items: center;
  height: 60vh;
  background-color: #f5f5f5;
}

.payment-confirmation {
  text-align: center;
  padding: 20px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  max-width: 400px;
  width: 100%;
}

h2 {
  color: #42b983;
  font-size: 24px;
  margin-bottom: 10px;
}

p {
  font-size: 16px;
  margin-bottom: 5px;
}

/* 버튼 스타일은 필요에 따라 변경 가능합니다. */
button {
  background-color: #42b983;
  color: #fff;
  font-size: 16px;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #37966f;
}

button:focus {
  outline: none;
}

</style>