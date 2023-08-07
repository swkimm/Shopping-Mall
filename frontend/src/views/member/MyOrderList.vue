<template>
  <h1>내 주문목록</h1>
  <div v-if="authority === 1">
    <div class="page-container">
      <div class="sidebar">
        <h3 :class="{ 'underline': activeTab === '/profile' }" @click="goToProfile">개인정보수정</h3>
        <h3 :class="{ 'underline': activeTab === '/myDeliveryStatus'  }" @click="goToMyDeliveryStatus">배송 현황</h3>
        <h3 :class="{ 'underline': activeTab === '/myOrderList' }" @click="goToMyOrderList">주문 목록</h3>
      </div>
      <div class="content">
        <div class="mb-2">
          <button
              type="button"
              class="btn btn-outline-primary"
              :class="{ 'active-button': selectedDateRange === 'last6Months' }"
              style="margin-left: 5px"
              @click="getOrdersByLast6Months"
          >
            최근 6개월
          </button>
          <button
              type="button"
              class="btn btn-outline-primary"
              :class="{ 'active-button': selectedDateRange === getCurrentYear().toString() }"
              style="margin-left: 5px"
              @click="getOrdersByYear(getCurrentYear())"
          >
            {{ getCurrentYear() }}
          </button>
          <button
              type="button"
              class="btn btn-outline-primary"
              :class="{ 'active-button': selectedDateRange === (getCurrentYear() - 1).toString() }"
              style="margin-left: 5px"
              @click="getOrdersByYear(getCurrentYear() - 1)"
          >
            {{ getCurrentYear() - 1 }}
          </button>
        </div>
        <br>
        <div v-if="orderList.length === 0">
          <h1>주문 내역이 없습니다.</h1>
        </div>
        <div v-else>
          <div v-for="order in orderList">
            <div class="card container">
              <div style="margin-top: 10px">
                <h4><strong>{{ formatPaymentDate(order.paymentDate) }}</strong> 주문</h4>

              </div>
              <div class="card container" style="margin-bottom: 10px">
                <div class="row" style="margin-top: 10px">
                  <div class="col">
                    <h4>{{ order.paymentStatus }}</h4>
                    <img class="product-image"
                         :src="getRepresentativeImageURL(order)"
                         alt=""/>
                  </div>
                  <div class="col-6" style="margin-top: 30px">
                    <h4><a href="#" @click="goToProductDetail(order.productId)">{{ order.productName }}</a></h4>
                    <h5>{{ formatPrice(order.productPrice) }}원, {{ order.productQty }}개</h5>
                  </div>
                  <div class="col d-flex justify-content-center align-items-end">
                    <button @click="addToCart(order.productId, order.productQty)" type="button"
                            class="btn btn-primary btn-lg">장바구니 추가
                    </button>
                  </div>
                </div>
                <br>
              </div>
            </div>
            <br>
          </div>
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

const authority = computed(() => store.getters.getAuthority)
const memberId = computed(() => store.getters.getMemberId);
const activeTab = ref('/myOrderList');

const orderList = ref([]);
const isInResult = ref(0);

const selectedDateRange = ref("last6Months"); // Initialize the selectedDateRange with "last6Months"

// 가격을 1000 단위 콤마(,)가 포함된 형식으로 변환하는 메소드
function formatPrice(price) {
  if (price) {
    return addCommasToNumber(price);
  } else {
    return ''; // or any default value you prefer when the price is not available
  }
}

function addCommasToNumber(number) {
  return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

const getOrdersByLast6Months = () => {
  const endDate = new Date(); // Current date
  const startDate = new Date();
  startDate.setMonth(startDate.getMonth() - 6); // 6 months ago from the current date
  const formattedStartDate = startDate.toISOString();
  const formattedEndDate = endDate.toISOString();
  selectedDateRange.value = "last6Months"; // Set the selected date range to "last6Months"
  getOrdersByDateRange(formattedStartDate, formattedEndDate);
};

const getOrdersByYear = (selectedYear) => {
  const startDate = new Date(selectedYear, 0, 1); // First day of the selected year
  const endDate = new Date(selectedYear, 11, 31); // Last day of the selected year
  const formattedStartDate = startDate.toISOString();
  const formattedEndDate = endDate.toISOString();
  selectedDateRange.value = selectedYear.toString(); // Set the selected date range to the selected year
  getOrdersByDateRange(formattedStartDate, formattedEndDate);
};


const getOrdersByDateRange = (startDate, endDate) => {
  axios
      .post("/api/order/getOrdersByDateRange", {
        memberId: memberId.value,
        startDate: startDate,
        endDate: endDate,
      })
      .then((response) => {
        orderList.value = response.data;
        console.log("getOrdersByDateRange", response.data);
      })
      .catch((error) => {
        if (error) {
          alert("주문 목록 불러오기 중 오류가 발생했습니다.");
        }
      });
};


const getCurrentYear = () => {
  return new Date().getFullYear();
};
const isInCart = async (productId) => {
  console.log(productId)
  try {
    const response = await axios.post(`/api/cart/isInCart/${productId}`, {
      memberId: memberId.value
    });
    isInResult.value = response.data;
    console.log("isInCart", response.data);
    return isInResult.value === 1; // Return true if the product is in the cart
  } catch (error) {
    alert("장바구니에 동일한 상품이 있는지 체크 오류입니다.");
    return false;
  }
}

const addToCart = async (productId, productQty) => {
  console.log(productId)
  console.log(productQty)
  try {
    // Check if the product exists in the cart
    const isProductInCart = await isInCart(productId);

    // If the product is not in the cart, proceed to add it
    if (!isProductInCart) {
      const response = await axios.post(`/api/cart/addToCart/${productId}`, {
        pId: productId,
        productQty: productQty,
        memberId: memberId.value
      });

      const result = confirm("상품이 장바구니에 추가되었습니다. 장바구니로 이동할까요?");
      if (result === true) {
        await router.push("/cart");
      } else {
        // Do nothing or show an alternative message if needed.
      }
    } else {
      // Product already in the cart, show confirmation to redirect
      const res = confirm("동일 상품이 장바구니에 존재합니다. 장바구니로 이동하시겠습니까?");
      if (res === true) {
        await router.push("/cart");
      } else {
        location.reload();
      }
    }
  } catch (error) {
    alert("상품을 장바구니에 추가하지 못했습니다.");
  }
}

// const getCompleteOrderList = () => {
//   axios.post("/api/order/getCompleteOrderList", {
//     memberId: memberId.value
//   })
//       .then((response) => {
//         orderList.value = response.data;
//         console.log(response.data)
//       })
//       .catch((error) => {
//         if (error) {
//           alert("주문 목록 불러오기 중 오류가 발생했습니다.")
//         }
//       })
// }

const goToProductDetail = (productId) => {
  router.push(`/productDetail/${productId}`)
}

const getRepresentativeImageURL = (order) => {
  if (order.fileName && order.fileName.length > 0) {
    const firstFileName = order.fileName[0];
    return `https://bucket230503-zvdwq133sdjmh.s3.ap-northeast-2.amazonaws.com/shop/${order.productId}/${firstFileName}`;
  }
  return ''; // Return empty string if no image available
};
const formatPaymentDate = (dateArray) => {
  const [year, month, day] = dateArray;
  const formattedDate = `${year}년 ${month}월 ${day}일`;
  return formattedDate;
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
  getOrdersByLast6Months()
})

</script>

<style scoped>
.active-button {
  background-color: #007bff; /* Blue color - you can change it to your preferred color */
  color: white;
}

.product-image {
  width: 8rem;
  height: 8rem; /* Set the desired height for the image */
  object-fit: cover; /* Use 'cover' to ensure the entire container is filled, and the image is cropped if necessary */
  margin-right: 0.5rem;
}

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

.sidebar h3 {
  cursor: pointer;
}

</style>