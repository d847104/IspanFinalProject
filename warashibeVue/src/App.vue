<template>
  <div class="background">
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
    import { provide, ref, onMounted } from 'vue';
    import Navigation from './layout/Navigation.vue'
    import categoryBar from '@/layout/categoryBar.vue';
    import axiosapi from './plugins/axios';

    const user = ref(null);

    const fetchUserData = async () => {
    try {
        const memberID = sessionStorage.getItem("memberID");
        const token = sessionStorage.getItem("token");
        
        if (memberID && token) {
            axiosapi.defaults.headers.authorization = `Bearer ${token}`;
            const response = await axiosapi.get(`/ajax/members/${memberID}`);
            user.value = response.data.list[0];
        }
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
  };

  onMounted(fetchUserData);

  provide('user', user);
  provide('setUser', (newUser,token) => {
      user.value = newUser;
      if (newUser) {
          sessionStorage.setItem("memberID", newUser.id);
          sessionStorage.setItem("token", token);
          axiosapi.defaults.headers.authorization = `Bearer ${token}`;
      } else {
          sessionStorage.removeItem("memberID");
          sessionStorage.removeItem("token");
          axiosapi.defaults.headers.authorization = '';
      }
  });
</script>

<style>
body {
  background: url('/src/img/bg02.jpg') no-repeat center center fixed;
  background-size: cover;
}
/* .background{
  background-color: rgba(255, 255, 255, 0.6);
} */
</style>