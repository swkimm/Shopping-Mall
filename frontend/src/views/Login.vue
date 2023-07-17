<template>

  <div v-if="checkLoginStatus">
    <div class="login-container">
      <h1>Login</h1>
      <div class="login-form">
        <input v-model="email" type="email" placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <button type="button" class="btn btn-primary" @click="loginPost">로그인</button>
        <div>
          <RouterLink :to="{path : '/signup', replace:true}">
            <button type="button" class="btn btn-secondary">회원가입</button>
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
  <div v-else="">

  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import router from '@/router';
import { useStore } from 'vuex';

const store = useStore();
const email = ref('');
const password = ref('');
const isLoggedIn = ref(false); // 로그인 상태를 저장하는 변수
const user = ref({});

const loginPost = () => {
  axios
      .post('/api/auth/login', {
        email: email.value,
        password: password.value,
      })
      .then(response => {
        user.value = response.data;
        localStorage.setItem('user', JSON.stringify(user.value)); // 로컬 스토리지에 저장

        isLoggedIn.value = true; // 로그인 상태로 변경
        store.dispatch('loginUser', user.value);
        router.push('/');
      })
      .catch(error => {
        // 로그인 실패 처리
        console.error(error);
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