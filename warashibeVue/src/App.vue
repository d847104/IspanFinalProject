<template>
        <Navigation></Navigation>
        <categoryBar></categoryBar>
        <RouterView></RouterView>
</template>

<script setup>
        import 'bootstrap/dist/css/bootstrap.min.css'
        import 'bootstrap/dist/js/bootstrap.bundle.min.js'
        // import 'sweetalert2/dist/sweetalert2.min.css'
        // import 'flatpickr/dist/flatpickr.css'
        import { RouterView } from 'vue-router';
        import { provide, ref } from 'vue';
        import Navigation from '@/views/Navigation.vue'
        import categoryBar from '@/layout/categoryBar.vue';

        const user = ref(null);

        provide('user', user)
        provide('setUser', newUser => {
                user.value = newUser;
        })
</script>

<style>
</style>
