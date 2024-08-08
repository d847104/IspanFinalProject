<template>
  <div class="background container p-4">
    <Navigation></Navigation>
    <categoryBar></categoryBar>
    <RouterView></RouterView>
  </div>
</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
// import 'bootstrap-vue/dist/bootstrap-vue.min.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'sweetalert2/dist/sweetalert2.min.css'
import 'flatpickr/dist/flatpickr.css'
import { RouterView } from 'vue-router';
import { provide, ref, onMounted, inject } from 'vue';
import Navigation from './layout/Navigation.vue'
import categoryBar from '@/layout/categoryBar.vue';
import axiosapi from './plugins/axios';

const isLogin = inject("isLogin");
onMounted(function(){
  isLogin.value = sessionStorage.getItem("username")? true : false;
})

</script>

<style>
body {
  background: url('/src/img/bg01.jpg') no-repeat fixed;
}
.background{
  background-color: rgba(255, 255, 255, 0.6);
}
</style>