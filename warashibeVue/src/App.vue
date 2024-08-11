<template>
  <div class="background container p-4 min-vh-100">
    <Navigation></Navigation>
    <categoryBar></categoryBar>
    <RouterView></RouterView>
  </div>
</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'sweetalert2/dist/sweetalert2.min.css'
import 'flatpickr/dist/flatpickr.css'
import { RouterView } from 'vue-router';
import { onMounted, inject, provide } from 'vue';
import Navigation from './layout/Navigation.vue'
import categoryBar from '@/layout/categoryBar.vue';

const isLogin = inject("isLogin");
const updateCartQt = inject("updateCartQt");
const loginUserName = inject("loginUserName");
const loginUserId = inject("loginUserId");
const loginAccount = inject("loginAccount");

onMounted(function(){
  isLogin.value = sessionStorage.getItem("username")? true : false;
  loginUserName.value = sessionStorage.getItem("username");
  loginUserId.value = sessionStorage.getItem("memberID");
  loginAccount.value = sessionStorage.getItem("account");
  updateCartQt();
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