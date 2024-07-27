<template>
  <div id="app" class="background">
    <div class="container">
    <NavBar></NavBar>
    <RouterView></RouterView>
    </div>
  </div>

</template>

<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.js'
// import 'sweetalert2/dist/sweetalert2.min.css'
// import 'flatpickr/dist/flatpickr.css'
import NavBar from './views/NavBar.vue'
// import Navigation from './views/Navigation.vue'
import { RouterView } from 'vue-router';

</script>
