<template>
<<<<<<< HEAD
  <div class="container">
    <NavBar></NavBar>
    <RouterView></RouterView>
  </div>
=======
  <Navigation></Navigation>
  <RouterView></RouterView>
>>>>>>> 70d7daeb8bacde975e1d56d48957ab6d90e9bb52
</template>
  
<script setup>
    import 'bootstrap/dist/css/bootstrap.min.css'
    import 'bootstrap/dist/js/bootstrap.bundle.min.js'
    import { provide, ref } from 'vue';
    import Navigation from './views/Navigation.vue'
    import { RouterView } from 'vue-router';
    const user = ref(null);

<<<<<<< HEAD
<script setup>
import 'bootstrap/dist/css/bootstrap.min.css'
// import 'bootstrap/dist/js/bootstrap.bundle.js'
// import 'sweetalert2/dist/sweetalert2.min.css'
// import 'flatpickr/dist/flatpickr.css'
import NavBar from './views/NavBar.vue'
// import 'bootstrap-vue/dist/bootstrap-vue'
// import Navigation from './views/Navigation.vue'
import { RouterView } from 'vue-router';
</script>

<style></style>
=======
    provide('user',user)
    provide('setUser',newUser =>{
      user.value = newUser;
    })
</script>
  
<style></style>
>>>>>>> 70d7daeb8bacde975e1d56d48957ab6d90e9bb52
