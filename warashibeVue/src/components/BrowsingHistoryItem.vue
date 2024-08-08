<template>
    <div class="row justify-content-center mb-3" v-if="product">
        <div class="col-md-12">
            <div class="card shadow-0 border rounded-3">
                <div class="card-body">
                    <div class="row g-0">
                        <div class="col-xl-3 col-md-4 d-flex justify-content-center">
                            <div class="bg-image hover-zoom ripple rounded ripple-surface me-md-3 mb-3 mb-md-0">
                                <img :src="imageSrc" :alt="product.productName" class="w-100" v-if="imageSrc" @click="navigateToProduct" style="cursor: pointer;" />
                                <p v-else>Loading...</p>
                                <a href="javascript:void(0);" @click="navigateToProduct">
                                    <div class="hover-overlay">
                                        <div class="mask" style="background-color: rgba(253, 253, 253, 0.15);"></div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-xl-6 col-md-5 col-sm-7" @click="navigateToProduct" style="cursor: pointer;">
                            <h5>{{ product.productName }}</h5>
                            <div class="d-flex flex-row">
                                <div class="text-warning mb-1 me-2">
                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                    <font-awesome-icon icon="fas fa-star-half-alt"></font-awesome-icon>
                                    <span class="ms-1">4.5</span>
                                </div>
                                <span class="text-muted">數量 {{ product.stock }}</span>
                            </div>
                            <p class="text mb-4 mb-md-0">{{ product.description }}</p>
                        </div>
                        <div class="col-xl-3 col-md-3 col-sm-5">
                            <div class="d-flex flex-row align-items-center mb-1">
                                <h4 class="mb-1 me-1">{{ product.price }}</h4>
                                <span class="text-danger"><s>NT$1,500</s></span>
                            </div>
                            <h6 class="text-success">Free shipping</h6>
                            <div class="mt-4">
                                <button class="btn btn-primary shadow-0" type="button" @click="viewProductDetail">直接購買</button>
                                <a href="javascript:void(0);" class="btn btn-light border px-2 pt-2 icon-hover">
                                    <font-awesome-icon icon="fas fa-heart fa-lg px-1" @click="addToFavorite"></font-awesome-icon>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted,inject } from 'vue';
import { useRouter } from 'vue-router';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const user = inject("user");
const props = defineProps(["item"]);
const product = ref(null);
const imageSrc = ref(null);
const router = useRouter();

const viewProductDetail = () => {
    router.push(`/pages/productpage?productID=${props.item.product}`);
};

const addToFavorite = async () => {
    try {
        await axiosapi.post('/ajax/favorite/insert', {
            memberID: user.value.id,
            productID: props.item.product,
            sellerID: props.item.member
        });
        Swal.fire('成功', '已將該商品加入最愛', 'success');
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
};

const fetchProduct = async () => {
    try {
        const response = await axiosapi.get(`/api/products/${props.item.product}`);
        product.value = response.data;
        fetchImage();
    } catch (error) {
        console.error('獲取產品資料失敗:', error);
    }
};

const fetchImage = async () => {
    try {
        const response = await axiosapi.get(`/api/productImg/product/${product.value.productID}`);
        const images = response.data.list;
        if (images && images.length > 0) {
            imageSrc.value = `data:image/jpeg;base64,${images[0].img}`;
        } else {
            imageSrc.value = "../../src/assets/images/no-image.jpg";
        }
    } catch (error) {
        console.error('讀取圖片失敗:', error);
        imageSrc.value = "../../src/assets/images/no-image.jpg";
    }
};

const navigateToProduct = () => {
    router.push(`/pages/productpage?productID=${product.value.productID}`);
};

onMounted(() => {
    fetchProduct();
});
</script>

<style scoped>
.icon-hover:hover {
    border-color: #3b71ca !important;
    background-color: white !important;
}

.icon-hover:hover i {
    color: #3b71ca !important;
}
</style>
