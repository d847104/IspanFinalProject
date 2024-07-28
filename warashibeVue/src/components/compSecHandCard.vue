<template>
        <div class="col-lg-3 col-md-6">
                <div class="card">
                        <img class="card-img-top" :alt="product.productName" :src="path">
                        <div class="card-body">
                                <h6 class="card-title">{{product.productName}}</h6>
                                <div class="card-text text-success text-end">想交換 {{product.wishItem}}</div>
                                <div class="text-end">
                                        <a href="#" class="btn btn-outline-primary btn-sm">收藏</a>
                                        <a href="#" class="btn btn-outline-primary btn-sm">購物車</a>
                                </div>
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