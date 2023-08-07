<template>
  <footer :style="footerStyle">
    <div class="container-fluid">
      <div style="margin-top: 30px">
        <div class="row">
          <div class="col">
            <h1>TouchDown</h1>
            <p>{{ shopName }}</p>
            <p>주소: {{ address }}</p>
            <p>전화번호: {{ phoneNumber }}</p>
            <p>Email: {{ email }}</p>
            <div>
              <a style="margin-left: 5px" href="#">이용약관</a>
              <a style="margin-left: 5px" href="#">개인정보처리방침</a>
              <a style="margin-left: 5px" href="#">고객센터</a>
            </div>
          </div>
          <div class="col">
            <h1>About Us</h1>
            <p>국내 유일의 미식축구 쇼핑몰입니다.</p>
            <i style="margin-left: 5px" class="fab fa-youtube fa-xl"></i>
            <i style="margin-left: 5px" class="fab fa-instagram fa-xl"></i>
            <i style="margin-left: 5px" class="fab fa-line fa-xl"></i>
            <i style="margin-left: 5px" class="fab fa-facebook fa-xl"></i>
            <br>
            <br>
            <button class="btn btn-primary" @click="goToLogin">가입하기</button>
            <br>
            <br>
            <a href="http://www.kafa.org/"><h5>대한미식축구협회</h5></a>
            <br>
          </div>

          <br>
          <p>ⓒ {{ currentYear }} {{ shopName }}. All rights reserved.</p>

        </div>
        <br>
      </div>
    </div>
  </footer>
</template>

<script setup>
import {ref} from 'vue';
import router from "@/router";

// Footer 배경색과 글씨 색상 정의
const footerStyle = ref({
  backgroundColor: 'black',
  color: 'white',
});

const goToLogin = () => {
  router.push("/login")
}
router.beforeEach((to, from, next) => {
  // 페이지가 이동되면 스크롤을 맨 위로 이동
  window.scrollTo(0, 0);
  next();
});

const shopName = 'TouchDown';
const address = '서울특별시 강남구 쇼핑로 123';
const phoneNumber = '02-123-4567';
const email = 'shop@example.com';
const currentYear = new Date().getFullYear();
</script>

<style scoped>
/* Footer 스타일링 */
footer {
  background-color: #f2f2f2;
  padding: 20px;
  font-size: 14px;
}

.footer-links {
  display: flex;
  text-align: left;
  margin-left: 5px;
}
.footer-icons {
  display: flex;
  text-align: end;
}

.footer-links a {
  text-align: left;
}

.footer-icons i {
  text-align: right;
  margin-left: 5px;
}

.footer-links-icons a:hover {
  text-decoration: underline;
}

</style>