<template>
  <div class="page-container">
    <div class="sidebar">
      <div v-if="activeTab === '/memberList'">
        <h3 :class="{ 'underline': activeTab === '/profile'}" @click="goToProfile">개인정보수정</h3>
        <h3 :class="{ 'underline': activeTab === '/memberList'}" @click="goToMemberList">회원 목록</h3>
      </div>
      <div v-else>
        <h3 :class="{ 'underline': activeTab === '/profile'}" @click="goToProfile">개인정보수정</h3>
        <h3 :class="{ 'underline': activeTab === '/memberList'}" @click="goToMemberList">회원 목록</h3>
      </div>
    </div>
    <div class="content">
      <br>
    <h2>회원 목록</h2>
      <br>
      <table class="table">
        <thead>
        <tr>
          <th>이름</th>
          <th>닉네임</th>
          <th>이메일</th>
          <th>주소</th>
          <th>번호</th>
          <th>가입일시</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(member, index) in memberList" :key="index">
          <td>{{ member.name }}</td>
          <td>{{ member.nickName }}</td>
          <td>{{ member.email }}</td>
          <td>{{ member.address }}</td>
          <td>{{ member.phone }}</td>
          <td>{{ formattedRegDate(member.regDate) }}</td>
          <td><button @click="deleteMember(member.email)" class="btn btn-danger btn-sm">삭제</button></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import axios from "axios";

const activeTab = ref('/memberList');
const memberList = ref({
  name: '',
  phone: '',
  nickName: '',
  email: '',
  address: '',
  regDate: []
});


const formattedRegDate = (regDate) => {
  if (regDate && regDate.length >= 3) {
    return `${regDate[0]}년 ${regDate[1]}월 ${regDate[2]}일`;
  } else {
    return '';
  }
};
const goToProfile = () => {
  activeTab.value = '/profile'
  window.location.href = '/profile'
}

const goToMemberList = () => {
  activeTab.value = '/memberList';
  window.location.href = '/memberList';
}

const deleteMember = (email) => {
  console.log(email)
  console.log(email === String)
  console.log(email.value === String)
  console.log(email.valueOf())
  axios.post("/api/admin/deleteMember", {email: email})
      .then((response) => {
        console.log(response.data)
        alert("회원이 삭제되었습니다.")
        location.reload();
      })
      .catch((error)=> {
        if(error) {
          alert("회원 삭제 중 문제가 발생했습니다.")
        }
      })
}

const checkCurrentTab = () => {
  const activeTab = ref('/memberList');
  const params = new URLSearchParams(window.location.search);
  const tabParam = params.get('tab');
  if (tabParam === 'memberList') {
    activeTab.value = '/memberList';
  } else {
    activeTab.value = '/profile';
  }
}

const getMemberList = () => {
  axios.post("/api/admin/getMemberList")
      .then((response) => {
        memberList.value = response.data
        console.log(response.data)
      })
      .catch((error) => {
        alert("회원목록 불러오는 중 오류가 발생했습니다.")
      })
}

onMounted(() => {
  checkCurrentTab()
  getMemberList()
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
  max-width: 1000px; /* Optional: Limits the maximum width of the content */
  align-items: center;
}

.sidebar h3 {
  cursor: pointer;
}

</style>