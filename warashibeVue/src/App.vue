<template>
  <Navigation></Navigation>
  <RouterView></RouterView>
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
  
<style></style>