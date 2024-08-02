<template>
<div class="col-lg-3 col-md-6 mb-4">
    <div class="card little-product-card" @click="viewProductDetail" v-show="product.productStatus">
        <img :src="productImageSrc" class="card-img-top" :alt="product.productName" />
        <div class="card-body">
            <h5 class="card-title">{{ product.productName }}</h5>
            <p class="card-text text-danger text-end">NT$ {{ product.price }}</p>
        </div>
    </div>
</div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps(["product"]);
const router = useRouter();

const productImageSrc = computed(() => {
return props.product.productImgs && props.product.productImgs.length
    ? props.product.productImgs[0].img
    : 'default-image-path'; // 替換為默認圖片的路徑
});

const viewProductDetail = () => {
    router.push(`/pages/productpage?productID=${props.product.productID}`);
};
</script>

<style scoped>
.little-product-card {
cursor: pointer;
}
.little-product-card .card-img-top {
height: 250px;
object-fit: cover;
}
</style>
