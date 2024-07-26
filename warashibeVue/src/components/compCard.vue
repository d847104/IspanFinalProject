<template>
        <div class="col-lg-2 col-md-6">
                <div class="card">
                        <img class="card-img-top" :alt="product.productName" :src="path">
                        <div class="card-body">
                                <h5 class="card-title">{{product.productName}}</h5>
                                <div class="card-text text-danger text-end">NT$ {{product.price}}</div>
                        </div>
                </div>
        </div>
</template>

<script setup>
        import { computed } from 'vue';

        const props = defineProps(["product"]);
        const path = computed(()=>{
                if(props.product.productImgs[0]){
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/${props.product.productImgs[0]}`
                } else {
                        return `${import.meta.env.VITE_API_URL}/api/productImg/img/comingsoon`
                }
        })
</script>

<style scoped>
</style>