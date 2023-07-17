<template>

  <Header></Header>
  <br>
  <EventBanner></EventBanner>
  <RouterView />
  <br>
  <h5>{{user}}</h5>
</template>

<script setup>
import Header from '@/components/Header.vue'
import EventBanner from '@/components/EventBanner.vue'
import { useStore } from 'vuex'
import {computed} from "vue";

const store = useStore()
const user = computed(() => store.getters.getUser)

</script>


<style scoped>

</style>
