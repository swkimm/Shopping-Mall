<template>

  <div v-if="checkLoginStatus">
    <div class="login-container">
      <h1>Login</h1>
      <div class="login-form">
        <input v-model="email" type="email" placeholder="Email" required/>
        <input v-model="password" type="password" placeholder="Password" required/>
        <div class="button-row">
          <button type="button" class="btn btn-primary" @click="loginPost">로그인</button>
          <RouterLink :to="{path : '/signup', replace:true}" class="btn btn-secondary">회원가입</RouterLink>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {computed, onMounted, ref} from 'vue';
import axios from 'axios';
import router from '@/router';

import {useStore} from 'vuex';


const store = useStore();
const email = ref('');
const password = ref('');
const isLoggedIn = ref(false); // 로그인 상태를 저장하는 변수
const user = ref({});

const nickName = computed(() => store.getters.getNickName)
const authority = computed(() => store.getters.getAuthority)


const loginPost = () => {
  console.log(password.value)
  // Login request data 생성
  const loginRequest = {
    email: email.value,
    pwd: password.value,
  };

  axios
      .post('/api/auth/login', loginRequest) // 수정된 부분: loginRequest 객체 전달
      .then(response => {
        if (response.data) {
          // Successful login
          user.value = response.data;
          localStorage.setItem('user', JSON.stringify(user.value));
          isLoggedIn.value = true;
          store.dispatch('loginUser', user.value);
          alert(nickName.value + '님 환영합니다.');
          router.push('/');
        } else {
          // Login failed
          isLoggedIn.value = false;
          alert('로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.');
        }
      })
      .catch(error => {
        // Error occurred during the login request
        console.error('Error during login:', error);
        alert('로그인 오류입니다.');
      });
};

const checkLoginStatus = () => {
  const userFromLocalStorage = localStorage.getItem('user'); // 로컬 스토리지에서 사용자 정보 가져오기
  if (userFromLocalStorage) {
    user.value = JSON.parse(userFromLocalStorage);
    isLoggedIn.value = true;
  }
};

const logout = () => {
  localStorage.removeItem('user'); // 로컬 스토리지에서 사용자 정보 제거
  localStorage.removeItem('orderId');

  user.value = {};
  isLoggedIn.value = false;
  router.push('/login');
};


</script>

<style>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start; /* 상단 정렬을 위해 flex-start로 수정 */
  height: 100vh;
  padding-top: 100px; /* 원하는 만큼의 상단 여백을 설정해주세요 */
}

.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f8f8f8;
}

h1 {
  margin-bottom: 20px;
}

input {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 200px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #4286f4;
  color: #fff;
  cursor: pointer;
}
</style>