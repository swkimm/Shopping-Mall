<template>
  <h1>프로필</h1>
  <div v-if="authority === 1">
    <div class="page-container">
      <div class="sidebar">
        <h3 :class="{ 'underline': activeTab === '/profile' }">개인정보수정</h3>
        <h3></h3>
        <h3 :class="{ 'underline': activeTab === '/myDeliveryStatus' }" @click = "goToMyDeliveryStatus">배송 현황</h3>
        <h3 :class="{ 'underline': activeTab === '/myOrderList' }" @click = "goToMyOrderList">주문 목록</h3>
      </div>
      <div class="content">
        <div class="col-lg-10">
          <div class="mb-3">
            <label class="form-label">가입일자</label>
            <div class="input-group">
              <input readonly class="form-control" v-model="formattedRegDate">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">이름</label>
            <div class="input-group">
              <input v-model="memberInfo.name" class="form-control" type="text">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">전화번호</label>
            <div class="input-group">
              <input v-model="formattedPhone" type="text" class="form-control" maxlength="13"/>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">닉네임</label>
            <div class="input-group">
              <input v-model="memberInfo.nickName" class="form-control" type="text">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">이메일</label>
            <div class="input-group">
              <input v-model="memberInfo.email" class="form-control" type="email">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">기존 주소</label>
            <div class="input-group">
              <input readonly v-model="memberInfo.address" class="form-control" type="text">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">변경할 주소</label>
            <br>
            <div class="input-group">
              <input class="form-control" id="postcode" v-model="postcode" placeholder="우편번호">
              <input class="btn btn-outline-secondary" @click="search()" value="우편번호 찾기">
            </div>
            <input v-model="roadAddress" readonly class="form-control" type="text" id="roadAddress"
                   placeholder="도로명주소">
            <span id="guide" style="color:#000;display:none"></span>
            <input v-model="extraAddress" readonly class="form-control" id="extraAddress" placeholder="기본주소">
            <input v-model="detailAddress" class="form-control" id="detailAddress" placeholder="상세주소">
          </div>
        </div>
        <button @click="updateProfile(authority)">수정</button>
      </div>
    </div>
  </div>
  <div v-else>
    <div class="page-container">
      <div class="sidebar">
        <h3 :class="{ 'underline': activeTab === '/profile'}" @click="goToProfile">개인정보수정</h3>
        <h3 :class="{ 'underline': activeTab === '/memberList'}" @click="goToMemberList">회원 목록</h3>
      </div>
      <div class="content">
        <div class="col-lg-10">
          <div class="mb-3">
            <label class="form-label">이름</label>
            <div class="input-group">
              <input v-model="memberInfo.name" class="form-control" type="text">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">닉네임</label>
            <div class="input-group">
              <input v-model="memberInfo.nickName" class="form-control" type="text">
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">이메일</label>
            <div class="input-group">
              <input v-model="memberInfo.email" class="form-control" type="email">
            </div>
          </div>
        </div>
        <button @click="updateProfile(authority)">수정</button>
      </div>
    </div>
  </div>

</template>

<script setup>

import {computed, onMounted, ref, watch, watchEffect} from "vue";
import axios from "axios";
import store from "@/store/store";
import router from "@/router";


const postcode = ref("")
const roadAddress = ref("");
const extraAddress = ref("");
const detailAddress = ref("");
const newAddress = ref();

const nickName = computed(() => store.getters.getNickName)
const email = computed(() => store.getters.getEmail)
const authority = computed(() => store.getters.getAuthority)

const memberInfo = ref({
  name: '',
  phone: '',
  nickName: '',
  email: '',
  address: '',
  regDate: []
});

const activeTab = ref('/profile');

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

const goToMyDeliveryStatus = () => {
  activeTab.value = '/myDeliveryStatus'
  window.location.href = '/myDeliveryStatus'
}

const goToMyOrderList = () => {
  activeTab.value = '/myOrderList'
  window.location.href = '/myOrderList'
}

const goToMemberList = () => {
  activeTab.value = '/memberList';
  // Update the URL to navigate to /memberList
  window.location.href = '/memberList';
}

// Compute the formattedRegDate string
const formattedRegDate = computed({
  get() {
    if (memberInfo.value.regDate && memberInfo.value.regDate.length >= 3) {
      return `${memberInfo.value.regDate[0]}년 ${memberInfo.value.regDate[1]}월 ${memberInfo.value.regDate[2]}일`;
    } else {
      return '';
    }
  },
  set(value) {
    // Handle input changes here if needed
    // Split the formatted value to extract year, month, and day
    const [year, month, day] = value.split(/년|월|일/);
    // Update memberInfo.regDate with the parsed values
    memberInfo.value.regDate = [parseInt(year), parseInt(month), parseInt(day)];
  }
});

const formattedPhone = computed({
  get: () => {
    return memberInfo.value.phone;
  },
  set: (value) => {
    if (value) {
      const numericValue = value.replace(/[^0-9]/g, '');
      let formattedValue = '';

      if (numericValue.length <= 3) {
        formattedValue = numericValue;
      } else if (numericValue.length <= 7) {
        formattedValue = numericValue.substring(0, 3) + '-' + numericValue.substring(3);
      } else if (numericValue.length <= 11) {
        formattedValue = numericValue.substring(0, 3) + '-' + numericValue.substring(3, 7) + '-' + numericValue.substring(7);
      } else {
        formattedValue = numericValue.substring(0, 3) + '-' + numericValue.substring(3, 7) + '-' + numericValue.substring(7, 11);
        if (numericValue.length > 11) {
          formattedValue += '-' + numericValue.substring(11);
        }
      }
      memberInfo.value.phone = formattedValue;
    } else {
      memberInfo.value.phone = '';
    }
  }
});

watchEffect(() => {
  formattedPhone.value = memberInfo.value.phone;
});

const updateProfile = (authority) => {

  if (authority === 1) {
    const newAddressValue = postcode.value + " " + roadAddress.value + extraAddress.value + " " + detailAddress.value;

    let updateValue;

    if (newAddressValue === "") {
      updateValue = {
        name: memberInfo.value.name,
        phone: memberInfo.value.phone,
        nickName: memberInfo.value.nickName,
        email: memberInfo.value.email,
        address: memberInfo.value.address
      };
    } else {
      updateValue = {
        name: memberInfo.value.name,
        phone: memberInfo.value.phone,
        nickName: memberInfo.value.nickName,
        email: memberInfo.value.email,
        address: newAddressValue
      };
    }

    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    axios.post("/api/member/updateProfile", {updateValue}, config)
        .then((response) => {
          console.log(response.data);
          alert("정보가 수정되었습니다.");

          // Retrieve the existing user information from the local storage
          const user = JSON.parse(localStorage.getItem('user'));

          // Update the loginUser object with the modified fields
          user.address = updateValue.address;
          user.name = updateValue.name;
          user.phone = updateValue.phone;
          user.nickName = updateValue.nickName;
          user.email = updateValue.email;

          // Save the updated user information back to local storage
          localStorage.setItem('user', JSON.stringify(user));


          // router.push('/');
          location.reload();

        })
        .catch((error) => {
          if (error) {
            alert(error);
          }
        });
  }
  // authority == 2인 admin 개인정보 수정
  else {

  }
};


const getMember = () => {
  axios.post("/api/member/getMemberProfile", {
    email: email.value
  })
      .then((response) => {
        memberInfo.value = response.data;
        console.log(response.data)
      })
      .catch((error) => {
        if (error) {
          alert("회원 정보 불러오기 중 오류가 발생했습니다.")
        }
      })
}

const search = () => {
  new window.daum.Postcode({
    oncomplete: (data) => {
      var roadAddr = data.roadAddress;
      var extraRoadAddr = "";

      if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
        extraRoadAddr += data.bname;
      }

      if (data.buildingName !== "" && data.apartment === "Y") {
        extraRoadAddr += extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName;
      }

      if (extraRoadAddr !== "") {
        extraRoadAddr = " (" + extraRoadAddr + ")";
      }

      postcode.value = data.zonecode;
      roadAddress.value = roadAddr;
      extraAddress.value = extraRoadAddr;

      var guideTextBox = document.getElementById("guide");

      if (data.autoRoadAddress) {
        var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
        guideTextBox.innerHTML = "(예상 도로명 주소 : " + expRoadAddr + ")";
        guideTextBox.style.display = "block";
      } else {
        guideTextBox.innerHTML = "";
        guideTextBox.style.display = "none";
      }
    },
  }).open();
};

onMounted(() => {
  getMember()

});

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

.sidebar h3 {
  cursor: pointer;
}

</style>