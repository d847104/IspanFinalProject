<template>
  <div class="app-container">
    <Navigation></Navigation>
    <RouterView></RouterView>
  </div>
</template>
  
<script setup>
    import 'bootstrap/dist/css/bootstrap.min.css'
    import 'bootstrap/dist/js/bootstrap.bundle.min.js'
    import { provide, ref } from 'vue';
    import Navigation from './views/Navigation.vue'
    import { RouterView } from 'vue-router';
    const user = ref(null);

    provide('user',user)
    provide('setUser',newUser =>{
      user.value = newUser;
    })
</script>
  
<style>
  .app-container {
    padding-top: 7%; /* Adjust this value based on Navbar height */
  }
</style>