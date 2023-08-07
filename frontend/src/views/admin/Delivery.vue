<template>
  <h1>배송 관리 페이지</h1>

  <div class="container card" >
    <br>
    <div class="row" >
      <div class="col-1">
        <i class="fa-solid fa-magnifying-glass fa-2xl"></i>
      </div>
      <div class="col">
        <select class="form-select w-10 p-1 custom-select" v-model="selectedYear">
          <option selected value="">전체 보기</option>
          <option v-for="year in years" :value="year" :key="year">{{ year }}년</option>
        </select>
        <br>
        <div class="checkbox-container">
          <div v-for="statusObj in deliveryStatus" :key="statusObj.status" class="checkbox-wrapper">
            <input
                class="form-check-input"
                type="checkbox"
                :id="statusObj.status"
                :value="statusObj.status"
                :checked="statusObj.selected"
                @change="toggleStatus(statusObj)"
            />
            <label :for="statusObj.status" @click="toggleStatus(statusObj)">{{ statusObj.status }}</label>
          </div>
          <button class="btn btn-primary" @click="searchDeliveryStatus">검색</button>
        </div>
          <br>
      </div>
    </div>
  </div>
      <br>
  <!--    <div>-->
  <!--      <input class="form-text w-10 p-2" type="text" placeholder="찾으려는 회원 번호를 입력하세요">-->
  <!--      <button class="btn btn-primary">찾기</button>-->
  <!--    </div>-->
  <table class="table">
    <thead>
    <tr>
      <th>결제번호</th>
      <th>주문번호</th>
      <th>상품명</th>
      <th>총금액</th>
      <th>결제일자</th>
      <th>인보이스</th>
      <th>회원번호</th>
      <th>주문고객</th>
      <th>배송주소</th>
      <th>배송요청사항</th>
      <th>배송상태</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(info, index) in visibleRows" :key="index">
      <td>{{ info.paymentId }}</td>
      <td>{{ info.orderId }}</td>
      <td>{{ limitedText(info.productName) }}</td>
      <td>{{ formatPrice(info.amount) }}원</td>
      <td>{{ formatPaymentDate(info.paymentDate) }}</td>
      <td>{{ info.invoice }}</td>
      <td>{{ info.memberId }}</td>
      <td>{{ info.name }}</td>
      <td>{{ info.address }}</td>
      <td v-if="info.deliveryRequest === ''">문 앞</td>
      <td v-else>{{ info.deliveryRequest }}</td>
      <td>
        <select v-model="info.paymentStatus" class="fa-m">
          <option value="">-- 선택 --</option>
          <option v-for="status in deliveryStatus" :key="status.deliveryId" :value="status.status">
            {{ status.status }} <!-- Assuming deliveryName is the property that represents the status name -->
          </option>
        </select>
      </td>
      <td>
        <button class="btn btn-primary" @click="updateDeliveryStatus(info.paymentId, info.orderId, info.paymentStatus)">
          수정
        </button>
      </td>
    </tr>
    </tbody>
  </table>
  <div class="load-more-container">
    <button v-if="visibleRows.length >= 10" @click="loadMore" class="btn btn-primary btn-lg">
      더보기
    </button>
  </div>
</template>

<script setup>

import axios from "axios";
import {onMounted, ref, watch} from "vue";

const selectedYear = ref('');
const currentYear = new Date().getFullYear();
const lastYear = currentYear - 1;
const yearBeforeLast = currentYear - 2;
const years = [currentYear, lastYear, yearBeforeLast];
const paymentList = ref([]);
const deliveryStatus = ref([]);
const searchResults = ref([]);


const visibleRows = ref([]);

const limitedText = (text) => {
  const maxLength = 20;
  if (text.length > maxLength) {
    return text.slice(0, maxLength) + '...';
  } else {
    return text;
  }
};

// Function to handle the "Load More" button click
const loadMore = () => {
  const startIndex = visibleRows.value.length;
  const endIndex = Math.min(startIndex + 10, paymentList.value.length);
  visibleRows.value = [...visibleRows.value, ...paymentList.value.slice(startIndex, endIndex)];
};
;

const toggleStatus = (statusObj) => {
  statusObj.selected = !statusObj.selected;
};

// watch(searchResults, (newResults) => {
//   // The watch callback is triggered whenever searchResults is updated
//   console.log('검색 결과 업데이트:', newResults);
//   if (paymentList === '') {
//     getAllPaymentList()
//   } else {
//     searchDeliveryStatus()
//   }
// });

const searchDeliveryStatus = () => {
  const selectedStatuses = deliveryStatus.value
      .filter((statusObj) => statusObj.selected)
      .map((statusObj) => statusObj.status);

  console.log('선택된 배송 상태:', selectedStatuses);

  // If no statuses are selected, show all payment data without filtering
  if (selectedStatuses.length === 0) {
    visibleRows.value = paymentList.value.slice(0, 10);
    return;
  }

  // Filter the paymentList based on selectedStatuses
  const filteredList = paymentList.value.filter((info) => {
    return selectedStatuses.includes(info.paymentStatus);
  });

  // Show the first 10 rows of the filtered list
  visibleRows.value = filteredList.slice(0, 10);
};

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

const formatPaymentDate = (dateArray) => {
  const [year, month, day, hour, minute] = dateArray;
  const formattedDate = `${year}.${month}.${day} ${hour}시 ${minute}분`;
  return formattedDate;
};

const updateDeliveryStatus = (paymentId, orderId, paymentStatus) => {
  axios.post("/api/payment/updateDeliveryStatus", {
    paymentId: paymentId,
    orderId: orderId,
    paymentStatus: paymentStatus
  })
      .then((response) => {
        // console.log(response.data)
        alert("수정되었습니다.")
        location.reload()
      })
      .catch((error) => {
        if (error) {
          alert("배송상태 수정 중 오류가 발생했습니다.")
        }
      })
}

watch(selectedYear, (newValue, oldValue) => {
  // Code to execute when the selectedYear changes
  console.log('New selected year:', newValue);
  console.log('Old selected year:', oldValue);
  if (newValue === '') {
    getAllPaymentList()
  } else {
    getSelectedYearPaymentList(newValue)
  }
  // You can perform any actions or logic here based on the new value
});


const getSelectedYearPaymentList = (selectedYear) => {
  console.log(selectedYear);
  axios
      .post("/api/payment/getSelectedYearPaymentList", {
        year: selectedYear,
      })
      .then((response) => {
        paymentList.value = response.data;
        // Initially show the first 10 rows
        visibleRows.value = paymentList.value.slice(0, 10);
        console.log(response.data);
      })
      .catch((error) => {
        if (error) {
          alert("년도별 배송 정보 불러오기 오류입니다.");
        }
      });
};

// Function to fetch all payment list
const getAllPaymentList = () => {
  axios
      .post("/api/payment/getAllPaymentList")
      .then((response) => {
        paymentList.value = response.data;
        // Initially show the first 10 rows
        visibleRows.value = paymentList.value.slice(0, 10);
        console.log(response.data);
      })
      .catch((error) => {
        if (error) {
          alert("결제 정보 가져오기 오류");
        }
      });
};


const getDeliveryStatusList = () => {
  axios.post("/api/delivery/getDeliveryStatusList")
      .then((response) => {
        deliveryStatus.value = response.data;
        console.log(response.data)
        // currentStepData.value = deliveryStatus.value.map((delivery) => delivery.deliveryId);
      })
      .catch((error) => {
        if (error) {
          alert("배송 상태 불러오기 오류")
        }
      })
}


onMounted(() => {
  selectedYear.value = ''
  getAllPaymentList(selectedYear)
  getDeliveryStatusList()
})
</script>


<style scoped>
.custom-select {
  width: 6rem; /* Adjust the width as needed */
}

.checkbox-container {
  display: flex;
  flex-wrap: wrap;
}

.checkbox-wrapper {
  display: flex;
  align-items: center;
  margin-right: 20px;
}

/* Adjustments for checkbox and label alignment */
.form-check-input {
  margin-right: 5px;
  /* You can adjust this value to match your preference */
}

.load-more-container {
  display: flex;
  justify-content: center;
  margin-top: 20px; /* Adjust the margin as needed */
}

</style>