<template>
  <h1>내 배송 현황</h1>
  <div v-if="authority === 1">
    <div class="page-container">
      <div class="sidebar">
        <h3 :class="{ 'underline': activeTab === '/profile' }" @click="goToProfile">개인정보수정</h3>
        <h3 :class="{ 'underline': activeTab === '/myDeliveryStatus'  }" @click="goToMyDeliveryStatus">배송 현황</h3>
        <h3 :class="{ 'underline': activeTab === '/myOrderList' }" @click="goToMyOrderList">주문 목록</h3>
      </div>
      <div class="content">
        <div v-if="deliveryInfo.length > 0 && deliveryInfo.paymentStatus !== '배송완료'">
          <div v-for="(info, index) in deliveryInfo.slice(0, visibleItemCount)" :key="index">
            <hr>
            <div>
              <StepProgress :steps="stepsData" :currentStep="getCurrentStep(info.paymentStatus)"/>
            </div>
            <br>
            <div class="row">
              <div class="col">
                <h5>주문 상품 : <strong>{{ info.productName }}</strong></h5>
                <h6>주문 번호 : <strong>{{ info.invoice }}</strong></h6>
              </div>
              <div class="col">
                <h6>받는 사람 : <strong>{{ info.name }}</strong></h6>
                <h6>받는 주소 : <strong>{{ info.address }}</strong></h6>
                <h6>요청 사항 : <strong>{{ info.deliveryRequest }}</strong></h6>
              </div>
            </div>
          </div>
          <hr>
          <div class="load-more-container" v-if="showLoadMoreButton && !isLastPage">
            <button class="btn btn-primary btn-lg" style="text-align: center" @click="loadMore">더보기</button>
          </div>
        </div>
        <div v-else>
          <h3>진행 중인 배송이 없습니다.</h3>
        </div>
      </div>
    </div>
  </div>
  <br>
  <br>

</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import StepProgress from "@/components/StepProgress.vue";
import {useStore} from 'vuex';
import {useRouter} from 'vue-router';

const router = useRouter();
const store = useStore();
const authority = computed(() => store.getters.getAuthority)
const memberId = computed(() => store.getters.getMemberId);
const orderId = ref('')
const invoice = ref('');
const stepsData = ref([]); // 단계별 이름 배열
const deliveryStatus = ref([]);
const deliveryInfo = ref([]);
const activeTab = ref('/myDeliveryStatus');

const itemsToShowInitially = 5;
const itemsToAddOnLoadMore = 5;
let visibleItemCount = ref(itemsToShowInitially);
const deliveryInfoLength = computed(() => deliveryInfo.value.length);

const showLoadMoreButton = () => {
  console.log("deliveryInfo.length",deliveryInfo.length)
  return visibleItemCount.value < deliveryInfoLength.value;
};

const isLastPage = computed(() => {
  return visibleItemCount.value >= deliveryInfoLength.value;
});

const loadMore = () => {
  visibleItemCount.value += itemsToAddOnLoadMore;
};

const getCurrentStep = (paymentStatus) => {
  if (paymentStatus === "결제완료") {
    return 0;
  } else if (paymentStatus === "상품준비중") {
    return 1;
  } else if (paymentStatus === "배송시작") {
    return 2;
  } else if (paymentStatus === "배송중") {
    return 3;
  } else if (paymentStatus === "배송완료") {
    return 4;
  }
  return -1; // 예외 상황 처리 (결제완료, 상품준비중, 배송시작 이외의 상태가 오면 -1을 반환)
};


if (authority === 2) {
  const params = new URLSearchParams(window.location.search);
  const tabParam = params.get('tab');
  if (tabParam === '/memberList') {
    activeTab.value = '/memberList';
  } else {
    activeTab.value = '/profile';
  }
} else {
  const params = new URLSearchParams(window.location.search);
  const tabParam = params.get('tab');
  if (tabParam === '/myDeliveryStatus') {
    activeTab.value = '/myDeliveryStatus';
  } else if (tabParam === '/myOrderList') {
    activeTab.value = '/myOrderList';
  }
}

//todo 결제완료이고 배송완료가 아닌것들이 보여야됨


const getDeliveryStatusList = () => {
  axios.post("/api/delivery/getDeliveryStatusList")
      .then((response) => {
        deliveryStatus.value = response.data;
        console.log(response.data)
        stepsData.value = deliveryStatus.value.map((delivery) => delivery.status);
        // currentStepData.value = deliveryStatus.value.map((delivery) => delivery.deliveryId);
      })
      .catch((error) => {
        if (error) {
          alert("배송 상태 불러오기 오류")
        }
      })
}

const getOrderId = () => {
  axios.post("/api/order/getOrderId", {
    memberId: memberId.value
  })
      .then((response) => {
        orderId.value = response.data
        getPaymentInfo()
      })
      .catch((error) => {
        if (error) {
          alert("주문번호 가져오기 오류")
        }
      })
}

const payInfo = ref({});

const getPaymentInfo = () => {
  console.log("orderId", orderId.value)
  axios.post("/api/payment/getPaymentInfo", {
    memberId: memberId.value,
    orderId: orderId.value
  })
      .then((response) => {
        payInfo.value = response.data;
        console.log(response.data)
        getDeliveryInfoInPayment()
      })
      .catch((error) => {
        if (error) {
          alert("오류")
        }
      })
}


const getDeliveryInfoInPayment = () => {
  axios.post("/api/payment/getDeliveryInfoInPayment", {
    memberId: memberId.value,
    invoice: payInfo.value.invoice
  })
      .then((response) => {
        deliveryInfo.value = response.data;
        console.log(response.data);
      })
      .catch((error) => {
        if (error) {
          alert("배송 정보 가져오기 오류 입니다.");
        }
      });
};

const goToProfile = () => {
  activeTab.value = '/profile'
  window.location.href = '/profile'
}

const goToMyDeliveryStatus = () => {
  activeTab.value = '/myDeliveryStatus'
  window.location.href = '/myDeliveryStatus'
}

const goToMyOrderList = () => {
  activeTab.value = '/myOrderList'
  window.location.href = '/myOrderList'
}

onMounted(() => {
  getDeliveryStatusList()
  getOrderId();
  getPaymentInfo()
  visibleItemCount.value = itemsToShowInitially;

  if (authority.value === 2) {
    router.push("/");
  }
})

</script>

<style scoped>
.underline {
  text-decoration: underline;
}

.page-container {
  display: flex;
  justify-content: center; /* Horizontally center aligns the child elements */
}

.sidebar {
  width: 20%;
  background-color: white;
  padding: 20px;
  border-right: .5px solid #000;
}

.content {
  flex: 1;
  margin: 0 auto; /* Centers the content horizontally within the remaining space */
  max-width: 800px; /* Optional: Limits the maximum width of the content */
  align-items: center;
}
.load-more-container {
  display: flex;
  justify-content: center;
  margin-top: 20px; /* Adjust the margin as needed */
}
</style>