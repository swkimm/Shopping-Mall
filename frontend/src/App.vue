<template>

  <Header></Header>
  <br>
  <EventBanner></EventBanner>
  <RouterView />
  <br>
<!--  <h5>{{user}}</h5>-->
  <Footer></Footer>
</template>

<script setup>
import Header from '@/components/Header.vue'
import EventBanner from '@/components/EventBanner.vue'
import { useStore } from 'vuex'
import {computed} from "vue";
import Footer from "@/components/Footer.vue";

const store = useStore()
const user = computed(() => store.state.user);

</script>


<style scoped>

</style>
