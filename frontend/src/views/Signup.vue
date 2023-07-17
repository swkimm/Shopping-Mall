<template>
  <h1>회원가입</h1>

  <div class="container-lg">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="mb-3">
          <label class="form-label">이름</label>
          <input v-model="memberName" class="form-control" type="text">
        </div>
        <div class="mb-3">
          <label class="form-label">전화번호</label>
          <input v-model="phone" class="form-control" type="text">
        </div>
        <div class="mb-3">
          <label class="form-label">닉네임</label>
          <div class="input-group">
            <input v-model="nickName" class="form-control" type="email">
            <input @click="duplCheckNickname(nickName)" class="btn btn-outline-secondary" type="button" value="중복 확인">
          </div>
          <div v-if="checkNickname === 1" class="form-text text-danger">
            <i class="fa-solid fa-triangle-exclamation"></i>
            사용 불가능한 별명입니다.
          </div>
          <div v-else-if="checkNickname !== ''" class="form-text text-primary">
            <i class="fa-solid fa-check"></i>
            사용 가능한 별명입니다.
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label">이메일</label>
          <div class="input-group">
            <input v-model="email" class="form-control" type="email">
            <input @click="duplCheckEmail(email)" class="btn btn-outline-secondary" type="button" value="중복 확인">
          </div>
          <div v-if="checkEmail === 1 " class="form-text text-danger">
            <i class="fa-solid fa-triangle-exclamation"></i>
            사용 가능한 이메일입니다.
          </div>
          <div v-else-if="checkEmail !== '' && emailRegexCheck(email)" class="form-text text-primary">
            <i class="fa-solid fa-check"></i>
            이메일 형식이 아닙니다.
          </div>
        </div>
        <div class="mb-3">
          <label class="form-label">비밀번호</label>
          <input v-model="password" class="form-control" type="password">
        </div>
        <div class="mb-3">
          <label class="form-label">비밀번호 확인</label>
          <input v-model="passwordAgain" class="form-control" type="password">
        </div>
        <div v-if="password !== '' && passwordAgain !== ''">
          <div v-if="isPasswordMatch" class="form-text text-primary">
            <i class="fa-solid fa-check"></i>
            패스워드가 일치합니다.
          </div>
          <div v-else class="form-text text-danger">
            <i class="fa-solid fa-triangle-exclamation"></i>
            패스워드가 일치하지 않습니다.
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label">주소</label>
          <br>
          <!--          <input v-model-="address" class="form-control" type="text">-->
          <div class="input-group">
            <input class="form-control" id="postcode" placeholder="우편번호">
            <input class="btn btn-outline-secondary" @click="search()" value="우편번호 찾기">
          </div>
          <input v-model="roadAddress" readonly class="form-control" type="text" id="roadAddress" placeholder="도로명주소">
          <!--          <input v-model="jibunAddress" readonly class="form-control" id="jibunAddress" placeholder="지번주소">-->
          <span id="guide" style="color:#000;display:none"></span>
          <input v-model="extraAddress" readonly class="form-control" id="extraAddress" placeholder="참고항목">
          <input v-model="detailAddress" class="form-control" id="detailAddress" placeholder="상세주소">

        </div>
        <div class="mb-3">
          <button @click="signup" :disabled="!isSignupEnabled">회원가입</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>

import {computed, onMounted, ref, watchEffect} from "vue";
import axios from "axios";

// const signupMember = ref({
//   memberName: '',
//   nickName: '',
//   phone: '',
//   email: '',
//   password: '',
//   checkPassword: '',
//   address: ''
// })

const memberName = ref('');
const phone = ref('');
const nickName = ref('');
const email = ref('');
const password = ref('');
const address = ref('');
const passwordAgain = ref('');
const check = ref(false);
const isNull = ref();

const roadAddress = ref('');
const jibunAddress = ref('');
const extraAddress = ref('');
const detailAddress = ref('');

const checkNickname = ref('');
const checkEmail = ref('');
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const isEmailEmpty = computed(() => email.value === "");
const isPasswordMatch = computed(() => password.value === passwordAgain.value);


const apiUrl = ref('');

const search = () => {
  new window.daum.Postcode({
    oncomplete: (data) => {
      var roadAddr = data.roadAddress;
      var extraRoadAddr = '';

      if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
        extraRoadAddr += data.bname;
      }

      if (data.buildingName !== '' && data.apartment === 'Y') {
        extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
      }

      if (extraRoadAddr !== '') {
        extraRoadAddr = ' (' + extraRoadAddr + ')';
      }

      document.getElementById('postcode').value = data.zonecode;
      document.getElementById('roadAddress').value = roadAddr;
      // document.getElementById('jibunAddress').value = data.jibunAddress;

      if (roadAddr !== '') {
        document.getElementById('extraAddress').value = extraRoadAddr;
      } else {
        document.getElementById('extraAddress').value = '';
      }

      var guideTextBox = document.getElementById('guide');

      if (data.autoRoadAddress) {
        var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
        guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
        guideTextBox.style.display = 'block';
        // } else if (data.autoJibunAddress) {
        //   var expJibunAddr = data.autoJibunAddress;
        //   guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
        //   guideTextBox.style.display = 'block';
      } else {
        guideTextBox.innerHTML = '';
        guideTextBox.style.display = 'none';
      }
    },
  }).open();
};
const emailRegexCheck = (email) => {
  if (emailRegex.test(email)) {
    checkEmail.value = true;
  } else {
    checkEmail.value = false;
  }
};

const duplCheckEmail = (email) => {
  emailRegexCheck(email);
  console.log(email)
  if (!isEmailEmpty.value) {
    axios
        .post("/api/member/duplCheckEmail", {email: email})
        .then((response) => {
          checkEmail.value = response.data;
          console.log(response.data)
          if (checkEmail.value === 1) {
            alert("이메일 중복입니다.")
          } else {
            alert("사용 가능한 이메일 입니다.")
          }
        })
        .catch((error) => {
          console.error("중복 체크 요청에 실패했습니다.", error);
        });
  }
};

const duplCheckNickname = (nickName) => {
  console.log(nickName)
  axios.post("/api/member/duplCheckNickname", {nickName: nickName}, {})
      .then((response) => {
        checkNickname.value = response.data;
        console.log(response.data);
        if (checkNickname.value === 1) {
          alert("닉네임 중복입니다.")
        } else {
          alert("사용할 수 있는 닉네임입니다.")
        }

      })
      .catch((error) => {
        console.error(error)
        console.error("중복 체크 요청에 실패했습니다.", error);
      });
};

let isSignupEnabled = false;

watchEffect(() => {
  isSignupEnabled =
      memberName.value !== '' &&
      phone.value !== '' &&
      nickName.value !== 1 &&
      email.value !== '' &&
      password.value !== '' &&
      checkPassword.value !== '' &&
      address.value !== '';
});

const signup = () => {

  axios
      .post("/api/members/signup", {})
      .then((response) => {
        // Handle successful signup
        console.log("ㄴㅇㄹㅁㄴㅇㄹㅈㄷㄹ")
      })
      .catch((error) => {
        alert("회원가입 중 오류가 발생했습니다.");
        console.error(error);
      });
};

onMounted(() => {
})


</script>


<style scoped>

.postal {
  max-width: 300px;
}

</style>