<template>
<div class="col-lg-3 col-md-6 mb-4">
    <div class="card little-product-card" @click="viewProductDetail" v-show="product.productStatus">
        <img :src="productImageSrc" class="card-img-top" :alt="product.productName" />
        <div class="card-body">
            <h5 class="card-title">{{ product.productName }}</h5>
            <p class="card-text text-danger text-end">NT$ {{ product.price }}</p>
        </div>
        <div class="mt-3">
                <a href="#"><font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" /></a>
                <a href="#"><font-awesome-icon :icon="['far', 'heart']" size="2x" beat style="color:lightcoral;--fa-beat-scale: 1.0" pull="right" @mouseover="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.3)}" @mouseout="(e)=>{e.target.style.setProperty('--fa-beat-scale',1.0)}" @click="addToFavorite" /></a>
        </div>
    </div>
</div>
</template>

<script setup>
import { computed, inject } from 'vue';
import { useRouter } from 'vue-router';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const props = defineProps(["product"]);
const router = useRouter();
const user = inject("user");

const productImageSrc = computed(() => {
return props.product.productImgs && props.product.productImgs.length
    ? props.product.productImgs[0].img
    : 'default-image-path'; // 替換為默認圖片的路徑
});

const viewProductDetail = () => {
    router.push(`/pages/productpage?productID=${props.product.productID}`);
};

const addToFavorite = async () => {
    try {
        await axiosapi.post('/ajax/favorite/insert', {
            memberID: user.value.id,
            productID: props.product.productID,
            sellerID: props.product.member
        });
        Swal.fire('成功', '已將該商品加入最愛', 'success');
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
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
