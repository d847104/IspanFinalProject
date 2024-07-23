<template>
        <navBar></navBar>
        <RouterView></RouterView>
</template>

<script setup>
        import 'bootstrap/dist/css/bootstrap.min.css'
        import 'bootstrap/dist/js/bootstrap.bundle.min.js'
        import { provide, ref } from 'vue';
        import { RouterView } from 'vue-router';
        import navBar from '@/layout/navBar.vue';
        const user = ref(null);

        provide('user',user)
        provide('setUser',newUser =>{
                user.value = newUser;
        })
</script>

<style>
        
</style>