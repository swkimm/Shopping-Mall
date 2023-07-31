<template>
  <div>

    <img class="mainTop" :src="mainTopImg1" alt="메인이미지"/>
    <hr>
    <div class="container">
      <div class="card" @click="goToHelmets">
        <div class="row align-items-center">
          <div class="col-3 d-flex align-items-center justify-content-center">
            <div class="image-container">
              <img style="width: 20rem" class="mainHelmet" :src="helmetMain">
            </div>
          </div>
          <div class="col" style="margin-left: 5px">
            <h5>안전과 스타일을 동시에 강조하는 최고급 헬멧을 선보입니다.</h5>
            <h5>우리는 세계적으로 인정받은 제조사와 함께 협력하여 품질이 검증된 제품만을 공급합니다.</h5>
            <h5>더욱 자세한 사양과 디자인은 제품 상세 페이지에서 확인하세요.</h5>
          </div>
        </div>
      </div>
      <br>
      <div class="card" @click="goToShoulderPads">
        <div class="row align-items-center">
          <div class="col" style="margin-left: 10px">
            <h5>우수한 보호 기능과 편안한 착용감으로 운동을 더욱 즐겁게 만들어줄 숄더패드를 소개합니다.</h5>
            <h5>제조사들과 함께 테스트를 거쳐 선별된 숄더패드만을 공급하여 최고의 품질을 보장합니다.</h5>
            <h5>자세한 정보는 각 제품의 상세 페이지에서 확인하실 수 있습니다.</h5>
          </div>
          <div class="col-3">
            <div class="image-container">
              <img style="width: 20rem" class="mainShoulderPad" :src="shoulderPadMain">
            </div>
          </div>
        </div>
      </div>
      <br>
      <div class="card" @click="goToApparel">
        <div class="row align-items-center">
          <div class="col-3 ">
            <div class="image-container">
              <img style="width: 20rem" class="mainApparel" :src="apparelMain">
            </div>
          </div>
          <div class="col">
            <h5>NFL 져지를 주력으로 삼아 최고의 품질과 스타일을 제공합니다.</h5>
            <h5>각 팀의 오피셜 져지를 제조사로부터 직접 공급하여 정품만을 보증합니다.</h5>
            <h5>다양한 사이즈와 디자인으로 라이딩과 일상에서도 빛을 발할 수 있습니다.</h5>
            <h5>제품 상세 페이지에는 각 팀의 하이라이트 영상과 해당 제품의 선수 하이라이트 영상</h5>
            <h5>더욱 특별한 경험을 누리실 수 있습니다.</h5>
          </div>
        </div>
      </div>
      <br>
      <div class="card" @click="goToAccessory">
        <div class="row align-items-center">
          <div class="col" style="margin-left: 10px">
            <h5>다양한 악세사리들로 스타일을 더욱 완성해보세요.</h5>
            <h5>헬멧 케이스, 헬멧 스티커, 그리고 기타 다양한 액세서리를 소개합니다.</h5>
            <h5>모든 악세사리들은 품질과 디자인에 중점을 두어 선별되었습니다.</h5>
            <h5>자세한 정보는 각 악세사리의 상세 페이지에서 확인하실 수 있습니다.</h5>
          </div>
          <div class="col-3">
            <div class="image-container">
              <img style="width: 20rem" class="mainAccessoryMain" :src="accessoryMain">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import mainTopImg1 from '@/assets/mainTopImg1.png';
import helmetMain from '@/assets/helmetMain.png';
import shoulderPadMain from '@/assets/shoulderPadMain.png';
import apparelMain from '@/assets/apparelMain.png';
import accessoryMain from '@/assets/accessoryMain.png';
import router from "@/router";


const goToHelmets = () => {
  router.push('/Helmets')
}

const goToShoulderPads = () => {
  router.push('/ShoulderPads')
}

const goToApparel = () => {
  router.push('/Apparel')
}

const goToAccessory = () => {
  router.push('/Accessories')
}

</script>

<style scoped>
.mainTop {
  margin-left: auto;
  margin-right: auto;
  display: flex;
  max-width: 70%;
}

.image-container {
  width: 20rem;
  height: 20rem; /* Set a fixed height for the image container */
  overflow: hidden; /* Hide any parts of the image that exceed the container */
}

.image-container img {
  width: 100%;
  height: 100%; /* Make the image fill the container */
  object-fit: cover; /* Maintain aspect ratio and crop if necessary */
}

.card {
  cursor: pointer;
}

</style>
