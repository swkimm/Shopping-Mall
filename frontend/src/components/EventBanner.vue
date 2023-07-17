<template>
  <div class="banner">
    <div class="banner">
      <div class="banner-navigation">
        <i class="fa-solid fa-chevron-left" style="color: #fafafa;"></i>

      </div>
      <div class="banner-content">
        <div v-for="(banner, index) in eventBanner" :key="banner.id"
             :style="{ display: activeIndex === index ? 'block' : 'none' }">
          <div class="bannerContent">{{ banner.content }}</div>
        </div>
      </div>
      <i class="fa-solid fa-chevron-right" style="color: #fafafa;"></i>
    </div>

  </div>
</template>

<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";

const eventBanner = ref({});
const activeIndex = ref(0);

onMounted(async () => {
  try {
    const response = await axios.get("/api/event/getEventBanners");
    eventBanner.value = response.data;
    console.log(eventBanner.value);

    startBannerRotation();
  } catch (error) {
    if (error.response) {
      alert(error.response.data.message);
    }
  }
});

function startBannerRotation() {
  setTimeout(() => {
    activeIndex.value = (activeIndex.value + 1) % eventBanner.value.length;
    startBannerRotation();
  }, 5000);
}

</script>

<style scoped>
.fa-chevron-left {
  margin-right: 50px;
}

.fa-chevron-right {
  margin-left: 50px;
}

.banner-navigation {
  display: flex;
  justify-content: space-between;
  margin-right: 16px; /* 옵션: 좌우 여백 조절 */
}


.bannerContent {
  text-size-adjust: auto;
}

.banner {
  font-size: 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
  background-color: black;
  color: white;
  padding-top: 10px;
  padding-bottom: 10px;
  margin-left: auto;
  margin-right: auto;
}
</style>