<template>
  <h1>여기는 숄더 패드 파는곳</h1>
    <img class="shoulderPadTop" :src="shoulderTopImg" alt="메인이미지"/>
</template>

<script setup>

import shoulderTopImg from "@/assets/shoulderTopImg.png";
</script>

<style scoped>

.shoulderPadTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  max-width: 80%;
  max-height: 800px;
}
</style>