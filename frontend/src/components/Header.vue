<template>
  <div>
    <div v-if="shouldShowFirstContent || user === null">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
          <div class="container-fluid">
            <RouterLink class="navbar-brand" aria-current="page" to="/">TouchDown</RouterLink>
            <div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
              <!-- 로그인 기능 구현 후 authority 값으로 수정  -->
              <ul class="navbar-nav">
                <li class="nav-item">
                  <RouterLink class="nav-link active" aria-current="page" to="/helmets">헬멧</RouterLink>
                </li>
                <li class="nav-item">
                  <RouterLink class="nav-link active" aria-current="page" to="/shoulder-pads">숄더 패드</RouterLink>
                </li>
                <li class="nav-item">
                  <RouterLink class="nav-link active" aria-current="page" to="/apparel">의류</RouterLink>
                </li>
                <li class="nav-item">
                  <RouterLink class="nav-link active" aria-current="page" to="/accessories">악세서리</RouterLink>
                </li>
              </ul>
            </div>
            <form class="d-flex" role="search">
              <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            <div v-if="user === null" class="nav-item">
              <RouterLink class="nav-link active" aria-current="page" to="/login">
                <i class="fa-solid fa-sign-in fa-xl"></i>
              </RouterLink>
            </div>
            <div v-else-if="user !== null">
              <button @click="logout" class="nav-item nav-link active fa-solid fa-sign-out fa-xl"
                      style="background-color: white; color: black"></button>
              <button @click="profile" class="nav-item nav-link active fa-solid fa-user fa-xl"
                      style="background-color: white; color: black"></button>
            </div>
            <div class="navbar-item">
              <RouterLink @click="askLoginOrGoToCart" to="/cart" class="fa-solid fa-cart-shopping fa-xl" style="background-color: white; color: black"></RouterLink>
            </div>
          </div>
        </nav>
      </div>

    <!-- 로그인 기능 구현 후 authority가 2(admin)인 경우 header가 변경됨 -->
    <div v-else>
      <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
          <RouterLink class="navbar-brand" aria-current="page" to="/">TouchDown</RouterLink>
          <div class="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
            <ul class="navbar-nav">
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/product">상품 관리</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/delivery">배송 관리</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/sales">매출 관리</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link active" aria-current="page" to="/requests">문의 게시글 관리</RouterLink>
              </li>
            </ul>
          </div>
          <div v-if="user === null" class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/login">
              <i class="fa-solid fa-sign-in fa-xl"></i>
            </RouterLink>
          </div>
          <div v-else-if="user !== null">
            <button @click="logout" class="nav-item nav-link active fa-solid fa-sign-out fa-xl"
                    style="background-color: white; color: black"></button>
            <button @click="profile" class="nav-item nav-link active fa-solid fa-user fa-xl"
                    style="background-color: white; color: black"></button>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<script setup>

import {computed} from "vue";
import {useStore} from 'vuex'
import {useRouter} from "vue-router";

const store = useStore()
const router = useRouter();
const user = computed(() => store.getters.getUser);

const shouldShowFirstContent = computed(() => {
  const currentUser = user.value;
  return !currentUser || currentUser.authority === 1;
});

const askLoginOrGoToCart = () => {
  // Check if the user is logged in
  if (!user.value) {
    // If not logged in, show the confirmation dialog
    const shouldLogin = window.confirm('로그인하시겠습니까?');
    if (shouldLogin) {
      // If the user confirms, redirect to the login page
      router.push('/login');
    } else {
      location.reload();
      // 이전 페이지로 이동
      // history.back();
    }
  } else {
    // If the user is logged in, directly navigate to the cart page
    router.push('/cart');
  }
};

const logout = () => {
  store.dispatch("logoutUser");
  router.push("/login");
};

const profile = () => {
  router.push("/profile")
}

</script>

<style scoped>
.fa-user, .fa-cart-shopping {
  margin-left: 30px;
}

.searchBar {
  align-content: end;
}

.form-control {
  margin: auto;
}

.navbar-nav {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: large;
}

.navbar-brand {
  font-size: xxx-large;
  font-weight: bold;
}

.nav-item {
  text-align: center;
  margin-left: 30px;
}

</style>