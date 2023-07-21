<template>
  <h1>회원가입</h1>

  <div class="container-lg">
    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="mb-3">
          <label class="form-label">이름</label>
          <input v-model="name" class="form-control" type="text">
        </div>
        <div class="mb-3">
          <label class="form-label">전화번호</label>
          <input type="text" v-model="phone" class="form-control" maxlength="11"/>
          <i class="fa-solid fa-circle-info"></i>
          숫자를 입력해주세요.
        </div>
        <div class="mb-3">
          <label class="form-label">닉네임</label>
          <div class="input-group">
            <input v-model="nickName" class="form-control" type="text">
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
          <div v-if="email !== '' && emailIconVisible !== null">
            <div v-if="!emailRegexCheck(email) || (emailRegexCheck(email) && checkEmail === 1)" class="form-text text-danger">
              <i class="fa-solid fa-triangle-exclamation"></i>
              사용 불가능한 이메일입니다.
            </div>
            <div v-else-if="emailRegexCheck(email) && checkEmail === 0" class="form-text text-primary">
              <i class="fa-solid fa-check"></i>
              사용 가능한 이메일입니다.
            </div>
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
          <div class="input-group">
            <input class="form-control" id="postcode" placeholder="우편번호">
            <input class="btn btn-outline-secondary" @click="search()" value="우편번호 찾기">
          </div>
          <input v-model="roadAddress" readonly class="form-control" type="text" id="roadAddress"
                 placeholder="도로명주소">
          <span id="guide" style="color:#000;display:none"></span>
          <input v-model="extraAddress" readonly class="form-control" id="extraAddress" placeholder="기본주소">
          <input v-model="detailAddress" class="form-control" id="detailAddress" placeholder="상세주소">

        </div>
        <button @click="signup" :disabled="isSignupDisabled"
                :class="{'disabled-button': isSignupDisabled, 'active-button': !isSignupDisabled}">
          회원가입
        </button>
      </div>
    </div>
  </div>

</template>

<script setup>
import {computed, onMounted, ref, watchEffect} from "vue";
import axios from "axios";
import router from "@/router";

const roadAddress = ref("");
const jibunAddress = ref("");
const extraAddress = ref("");
const detailAddress = ref("");

const name = ref("");
const phone = ref("");
const nickName = ref("");
const email = ref("");
const password = ref("");
const address = ref('');
const passwordAgain = ref("");
const check = ref(false);
const isNull = ref();

const checkNickname = ref("");
const checkEmail = ref("");
const checkPhone = ref("");

const isEmailEmpty = computed(() => email.value === "");
const isPasswordMatch = computed(() => password.value === passwordAgain.value);

const apiUrl = ref("");

const emailIconVisible = ref(null); // 아이콘의 가시성을 제어하는 변수
const nickNameIconVisible = ref(null); // 아이콘의 가시성을 제어하는 변수


const emailRegexCheck = (email) => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (emailRegex.test(email)) {
    return true;
  } else {
    return false;
  }
};

const duplCheckEmail = (email) => {
  emailRegexCheck(email);
  if (!isEmailEmpty.value && emailRegexCheck(email)) {
    axios
        .post("/api/member/duplCheckEmail", {email: email})
        .then((response) => {
          checkEmail.value = response.data;
          console.log(response.data)
          if (checkEmail.value === 1) {
            alert("이메일 중복입니다.")
            emailIconVisible.value = false; // 중복된 이메일일 경우 아이콘 숨김
          } else {
            alert("사용 가능한 이메일입니다.")
            emailIconVisible.value = true; // 사용 가능한 이메일일 경우 아이콘 표시
          }
        })
        .catch((error) => {
          console.error("중복 체크 요청에 실패했습니다.", error);
        });
  } else {
    alert("이메일 형식을 맞춰주세요")
    checkEmail.value = 3;
    emailIconVisible.value = false; // 이메일 형식이 잘못된 경우 아이콘 숨김
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
          nickNameIconVisible.value = false; // 중복된 닉네임일 경우 아이콘 숨김
        } else {
          alert("사용할 수 있는 닉네임입니다.")
          nickNameIconVisible.value = true; // 사용 가능한 닉네임일 경우 아이콘 표시
        }
      })
      .catch((error) => {
        console.error(error)
        console.error("중복 체크 요청에 실패했습니다.", error);
      });
};

const isSignupDisabled = computed(() => {
  return (
      name.value === '' ||
      phone.value === '' ||
      checkNickname.value === 1 ||
      email.value === '' ||
      password.value === '' ||
      address.value === '' ||
      checkEmail.value === 1
  );
});

watchEffect(() => {
  phone.value = phone.value.replace(/[^0-9]/g, "");

  address.value = roadAddress.value + extraAddress.value + detailAddress.value;

});

const signup = () => {
  const signupMember = {
    name: name.value,
    nickName: nickName.value,
    phone: phone.value,
    email: email.value,
    pwd: password.value,
    address: roadAddress.value + extraAddress.value + detailAddress.value,
  };
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  axios
      .post("/api/auth/signup", {signupMember: signupMember}, config)
      .then((response) => {
        // Handle successful signup
        console.log(signupMember);
        alert("회원가입에 성공하였습니다.")
        router.push('/login')
      })
      .catch((error) => {
        alert("회원가입 중 오류가 발생했습니다.");
        console.error(error);
      });
};

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
});

</script>

<style scoped>
.disabled-button {
  background-color: lightgray;
  color: white;
  cursor: not-allowed;
}

.active-button {
  background-color: blue;
  color: white;
}
</style>
