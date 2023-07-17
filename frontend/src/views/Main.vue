<template>
    <h1>여기는 메인 페이지</h1>
    <img class="mainTop" :src="mainTopImg1" alt="메인이미지" />
</template>

<script setup lang="js">
import mainTopImg1 from '@/assets/mainTopImg1.png';


</script>

<style scoped>
.mainTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  max-width: 85%;
}



</style>
