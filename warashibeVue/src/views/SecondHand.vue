<template>
    
</template>

<script setup>
    import { onMounted } from 'vue';
    import callFind from '@/plugins/product_find';
    onMounted(function(){
        callFind();
    });
</script>

<style>

</style>