<template>
  <!-- <div id="app" class="background">
      <div class="container">
        <NavBar></NavBar>
      </div>
  </div> -->
  <!-- <div class="container"> -->
    <Navigation></Navigation>
    <!-- <categoryBar></categoryBar> -->
    <RouterView></RouterView>
  <!-- </div> -->
</template>
  
<script setup>
    import 'bootstrap/dist/css/bootstrap.min.css'
    import 'bootstrap-vue/dist/bootstrap-vue.min.css'
    import 'bootstrap/dist/js/bootstrap.bundle.js'
    import 'bootstrap/dist/js/bootstrap.bundle.min.js'
    // import 'sweetalert2/dist/sweetalert2.min.css'
    // import 'flatpickr/dist/flatpickr.css'
    // import Navigation from './views/Navigation.vue'
    import { RouterView } from 'vue-router';
    import { provide, ref } from 'vue';
    import Navigation from './views/Navigation.vue'
    import NavBar from './views/NavBar.vue'
    import categoryBar from '@/layout/categoryBar.vue';

    const user = ref(null);

    provide('user',user)
    provide('setUser',newUser =>{
            user.value = newUser;
    })
</script>

<style>


</style>
