<template>
    <h3>Product Table</h3>
    
    <!-- v-for="item in products" :key="item.id" :product="item" -->
    <div class="row">
        <ProductList>
        </ProductList>
    </div>

</template>
    
<script setup>
    import ProductList from '@/components/ProductList.vue';

</script>
    
<style>
    
</style>