<template>
  <!-- <div id="app" class="background">
      <div class="container">
        <NavBar></NavBar>
      </div>
  </div> -->
  <div class="app-container background">
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
.app-container {
    padding-top: 7%; /* Adjust this value based on Navbar height */
}
.background {
  background-image: url('/src/img/bg01.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed; /* 背景圖固定，滾動時不移動 */
  width: 100%;
  height: 100%;
  min-height: 100vh; /* 確保背景圖佔滿整個可視區域 */
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 80px 20px 20px; /* Padding top to avoid content hidden behind NavBar */
  background: rgba(255, 255, 255, 0.8);
  /* box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); */
  /* border-radius: 8px; */
  flex: 1; /* Make sure content area takes up the remaining space */
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

</style>
