<template>
        <!-- <div class="col-lg-3 col-md-6"> -->
                <div class="card">
                        <img class="card-img-top" :src="path + product.productID" :alt="product.productName">
                        <div class="card-body">
                                <h5 class="card-title">{{product.productName}}</h5>
                                <div class="card-text text-danger text-end">NT$ {{product.price}}</div>
                        </div>
                </div>
        <!-- </div> -->
</template>

<script setup>
        const props = defineProps(["product"])
        // const emits = defineEmits(["customOpen","customDelete"])
        const path = import.meta.env.VITE_API_URL + "/ajax/pages/detail/";
</script>

<style>

</style>