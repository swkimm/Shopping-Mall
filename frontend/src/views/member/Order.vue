<template>
  <h1>주문/결제</h1>
  <div class="container">
    <h3><strong>구매자 정보</strong></h3>
    <hr>
    <table class="table">
      <tr>
        <th scope="row" style="width: 142px;">이름</th>
        <td>{{ member.name }}</td>
      </tr>
      <tr>
        <th scope="row" style="width: 142px;">이메일</th>
        <td>{{ member.email }}</td>
      </tr>
      <tr>
        <th scope="row" style="width: 142px;">휴대폰 번호</th>
        <td>{{ member.phone }}</td>
      </tr>
    </table>
    <br><br>

    <div>
      <h3><strong>받는 사람 정보</strong></h3>
      <!--      <button class="changeAddressBtn" @click="changeAddress()">배송지 변경</button></h3>-->
    </div>
    <hr>
    <table class="table">
      <tr>
        <th scope="row">이름</th>
        <td>{{ member.name }}</td>
      </tr>
      <tr>
        <th scope="row">배송주소</th>
        <td>{{ member.address }}</td>
      </tr>
      <tr>
        <th scope="row">연락처</th>
        <td>{{ member.phone }}</td>
      </tr>
      <tr>
        <th scope="row">배송 요청 사항</th>
        <td style="max-width: 100px" v-if="newDeliveryRequest === ''">(기본) 문 앞</td>
        <td style="max-width: 100px" else>{{ newDeliveryRequest }}</td>
        <button @click="openModal">변경</button>

      </tr>
    </table>
    <br><br>

    <h3><strong>배송 정보</strong></h3>
    <hr>
    <table class="table">
      <thead>
      <tr>
        <th>{{ getOrderDateWithDeliveryEstimate() }}</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td>{{ orderInfo(order) }}</td>
      </tr>
      </tbody>
    </table>
    <br><br>


    <h3><strong>결제 정보</strong></h3>
    <hr>
    <table class="table">
      <tr>
        <th scope="row" style="width: 142px;">총 상품 가격</th>
        <td>{{ formatPrice(order.totalPrice) }} 원</td>
      </tr>
      <tr>
        <th scope="row" style="width: 142px;">배송비</th>
        <td>{{ formatPrice(deliveryFee) }} 원</td>
      </tr>
      <tr>
        <th scope="row" style="width: 142px;">총 결제 금액</th>
        <td>{{ formatPrice(totalPayment(order, deliveryFee)) }} 원</td>
      </tr>
    </table>
    <br><br>

    <div class="paymentBtn">
      <button type="button" class="btn btn-secondary btn-lg" @click="testPayment">데모 결제하기</button>
      <button type="button" class="btn btn-primary btn-lg" @click="cardPayment">카드 결제하기</button>
    </div>
  </div>

  <div v-if="showModal" class="modal">
    <!-- 모달 내용 -->
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Modal title</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" @click="closeModal"
                  aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <h5>변경할 요청 사항을 입력하세요</h5>
          <input v-model="newDeliveryRequestModal" type="text">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="closeModal">Close</button>
          <button type="button" class="btn btn-primary" @click="saveModal">Save changes</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import store from "@/store/store";
import axios from "axios";
import router from "@/router";

const memberId = computed(() => store.getters.getMemberId);
const authority = computed(() => store.getters.getAuthority)


const member = ref({})
const cart = ref({})
const order = ref({})
const orderId = ref('');

const deliveryFee = ref(3000)

const showModal = ref(false);
const newDeliveryRequest = ref('');
const newDeliveryRequestModal = ref('');
const currentTime = ref(new Date().toISOString());

const saveModal = () => {
  // Update newDeliveryRequest with the content entered in the input field
  newDeliveryRequest.value = newDeliveryRequestModal.value;
  closeModal();
};

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
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

const totalPayment = (order, deliveryFee) => {
  return order.totalPrice + deliveryFee;
};

const getOrderDateWithDeliveryEstimate = () => {
  const estimatedDeliveryDate = new Date();
  estimatedDeliveryDate.setDate(estimatedDeliveryDate.getDate() + 7);
  // 날짜를 원하는 형식으로 포맷팅 (예: "2023-07-31")
  const formattedDate = estimatedDeliveryDate.toISOString().split('T')[0];
  return `${formattedDate} 내 도착 예정`;
};
const orderInfo = (order) => {
  const {brand, name, color, size, countProduct} = order;
  if (order.length > 1) {
    return `${brand} ${name} ${color} ${size} 외 ${countProduct - 1}`;
  } else {
    return `${brand} ${name} ${color} ${size} `;
  }
};

const getOrderMemberInfo = () => {
  axios.post("/api/order/getOrderMemberInfo", {
    memberId: memberId.value,
  })
      .then((response) => {
        member.value = response.data
        console.log("getOrderMemberInfo", response.data)
      })
      .catch((error) => {
        if (error) {
          alert("주문 고객 정보 불러오기 중 오류가 발생했습니다.")
        }
      })
}

const getOrderId = () => {
  axios.post("/api/order/getOrderId", {
    memberId: memberId.value,
  })
      .then((response) => {
        orderId.value = response.data;
        console.log("getOrderId", response.data);
        getOrderInfo()

      })
      .catch((error) => {
        if (error) {
          const msg = "주문 번호 가져오기 오류입니다.";
          alert(msg);
        }
      });
};

const getOrderInfo = () => {
  console.log(orderId.value);
  console.log(memberId.value);
  axios.post("/api/order/getOrderInfo", {
    memberId: memberId.value,
    orderId: orderId.value,
  })
      .then((response) => {
        console.log("getOrderInfo", response.data);
        order.value = response.data;
      })
      .catch((error) => {
        if (error) {
          const msg = "주문 정보 불러오기 중 오류가 발생했습니다.";
          alert(msg);
        }
      });
};

const isInPayment = () => {
  axios.post("api/payment/isInPayment", {
    orderId: orderId.value,
  })
      .then((response) => {
        console.log("isInPayment", response.data);
        if (response.data === 1) {
          alert("결제 완료되었습니다.");
          router.push('/successPayment');
        }
      })
      .catch((error) => {
        alert("Error checking payment status.");
      });
};

const testPayment = () => {
  const paymentInfo = {
    orderId: order.value.orderId,
    productName: orderInfo(order.value),
    amount: order.value.totalPrice + deliveryFee.value,
    paymentDate: currentTime.value,
    memberId: member.value.memberId,
    email: member.value.email,
    name: member.value.name,
    phone: member.value.phone,
    address: member.value.address,
    deliveryRequest: newDeliveryRequest.value
  }


  console.log("testPayment", paymentInfo)
  // 결제 성공 시 로직
  axios.post("/api/payment/savePayInfo", paymentInfo)
      .then((response) => {
        console.log(response.data)
        isInPayment()
        router.push('/paymentSuccess');
      })
      .catch((error) => {
        if (error) {
          const msg = "결제 정보를 저장하지 못했습니다."
          alert(msg);
        }
      });
}

const paymentResult = ref(null);
const orderCount = ref();

const getMaxOrderId = () => {
  axios.post("/api/order/getMaxOrderId")
      .then((response) => {
        orderCount.value = response.data
        generateMerchantUid()
        console.log("getMaxOrderId", response.data)
      })
      .catch((error) => {
        if (error) {
          alert("MaxOrderId 불러오기 오류 입니다.")
        }
      })
}

const generateMerchantUid = () => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  const serialNumber = String(orderCount.value).padStart(6, '0');

  return `${year}${month}${day}-${serialNumber}`;
};


const cardPayment = () => {
  const IMP = window.IMP; // 생략 가능
  IMP.init("imp86058403"); // 예: imp00000000a
  const merchant_uid = generateMerchantUid();
  console.log("merchant_uid", merchant_uid)
  IMP.request_pay(
      {
        pg: "kcp.{INIBillTst}",
        pay_method: "card",
        merchant_uid: generateMerchantUid(),
        name: orderInfo(order.value),
        amount: order.value.totalPrice + deliveryFee.value,
        buyer_email: member.value.email,
        buyer_name: member.value.name,
        buyer_tel: member.value.phone,
        buyer_addr: member.value.address,
        buyer_postcode: '',
        m_redirect_url: 'http://localhost:5173/orderComplete',
      },
      rsp => {
        if (rsp.success) {
          // 결제 성공 시 로직
          paymentResult.value = rsp;
          verifyPayment(rsp.merchant_uid, rsp.amount);
          savePayInfo();
        } else {
          // 결제 실패 시 로직
          alert("카드 결제 실패입니다.");
        }
      }
  );
};

// Function to verify payment
const verifyPayment = (merchant_uid, amount) => {
  // 결제검증
  axios.post(`/api/verifyIamport/${merchant_uid}`, {
    merchant_uid : merchant_uid,
    amount : amount
  })
      .then(response => {
        const data = response.data;
        console.log(data);

        // Compare rsp.paid_amount with data.response.amount to perform verification
        if (amount === data.response.amount) {
          alert("결제 및 결제검증완료");
        } else {
          alert("결제 실패");
        }
      })
      .catch(error => {
        console.error('Error verifying payment:', error);
      });
};

const savePayInfo = () => {
  const paymentInfo = {
    orderId: order.value.orderId,
    productName: orderInfo(order.value),
    amount: order.value.totalPrice + deliveryFee.value,
    paymentDate: currentTime.value,
    memberId: member.value.memberId,
    email: member.value.email,
    name: member.value.name,
    phone: member.value.phone,
    address: member.value.address,
    deliveryRequest: newDeliveryRequest.value
  }
  console.log("requestPay", paymentInfo)

  axios.post("/api/payment/savePayInfo", paymentInfo)
      .then((response) => {
        console.log(response.data)
        // location.reload()
        isInPayment()
        router.push('/paymentSuccess');
      })
      .catch((error) => {
        if (error) {
          alert("결제 중 오류가 발생하였습니다.")
        }
      });
}


// const cardPayment = () => {
//   const IMP = window.IMP; // 생략 가능
//   IMP.init("imp86058403");
//   IMP.request_pay(
//       {
//         pg: "kcp.{TC0ONETIME}",
//         pay_method: "card",
//         merchant_uid: "ORD20180131-0000012",
//         name: "노르웨이 회전 의자",
//         amount: 64900,
//         buyer_email: "gildong@gmail.com",
//         buyer_name: "홍길동",
//         buyer_tel: "010-4242-4242",
//         buyer_addr: "서울특별시 강남구 신사동",
//         buyer_postcode: "01181"
//       },
//       (rsp) => {
//         // Callback function
//         // rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
//         // You can handle the response here.
//         if (rsp.success) {
//
//         } else {
//
//         }
//       }
//   );
// };

onMounted(async () => {
  await getMaxOrderId();
  await getOrderMemberInfo();
  await getOrderId();
  if (authority.value === 2) {
    await router.push("/");
  }
})


</script>

<style scoped>
.changeAddressBtn {
  margin-left: 10px;
  font-size: 15px;
  padding: 10px;
}

/* 버튼을 중앙으로 정렬 */
.paymentBtn {
  text-align: center;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
}

.close {
  float: right;
  cursor: pointer;
}

</style>