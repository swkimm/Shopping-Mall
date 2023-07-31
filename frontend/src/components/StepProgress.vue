<template>
  <div class="step-progress">
    <div v-for="(step, index) in steps" :key="index" :class="{ 'active': index === currentStep }">
      {{ step }}
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const { steps, currentStep } = defineProps(['steps', 'currentStep']);
</script>

<style scoped>
.step-progress {
  display: flex;
}

.step-progress div {
  flex: 1;
  text-align: center;
  padding: 10px;
}

.step-progress div.active {
  font-weight: bold;
  color: green;
}
</style>
