<template>
        <div class="col-lg-3 col-md-6">
                <div class="card h-100">
                        <img class="card-img-top h-100 object-fit-scale" :alt="product.productName" :src="path">
                        <div class="card-body">
                                <h6 class="card-title fw-bold">{{product.productName}}</h6>
                                <div class="card-text text-danger text-end fs-5">NT$ {{product.price}}</div>
                                <div class="mt-3">
                                        <a href="#"><font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" /></a>
                                        <a href="#"><font-awesome-icon :icon="['far', 'heart']" size="2x" beat style="color:lightcoral;--fa-beat-scale: 1.0" pull="right" @mouseover="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.3)}" @mouseout="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.0)}" /></a>
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