<template>


    <!-- sidebar + content -->
    <section class="">
        <div class="container">
            <div class="row">

                <!-- content -->
                <div class="col-lg-12">
                    <div class="row justify-content-center mb-3">
                        <div class="col-md-12">
                            <div class="card shadow-0 border rounded-3">
                                <div class="card-body">
                                    <div class="row g-0">
                                        <div class="col-xl-3 col-md-4 d-flex justify-content-center">
                                            <div
                                                class="bg-image hover-zoom ripple rounded ripple-surface me-md-3 mb-3 mb-md-0">
                                                <img :src="imageSrc" :alt="item.productName" class="w-100"
                                                    v-if="imageSrc" />
                                                <p v-else>Loading...</p>
                                                <a href="#!">
                                                    <div class="hover-overlay">
                                                        <div class="mask"
                                                            style="background-color: rgba(253, 253, 253, 0.15);"></div>
                                                    </div>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-md-5 col-sm-7">
                                            <h5>{{ item.productName }}</h5>
                                            <div class="d-flex flex-row">
                                                <div class="text-warning mb-1 me-2">
                                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                                    <font-awesome-icon icon="fa fa-star"></font-awesome-icon>
                                                    <font-awesome-icon icon="fas fa-star-half-alt"></font-awesome-icon>
                                                    <span class="ms-1">
                                                        4.5
                                                    </span>
                                                </div>
                                                <span class="text-muted">數量{{ item.stock }}</span>
                                            </div>
                                            <p class="text mb-4 mb-md-0">
                                                {{ item.description }}
                                            </p>
                                        </div>
                                        <div class="col-xl-3 col-md-3 col-sm-5">
                                            <div class="d-flex flex-row align-items-center mb-1">
                                                <h4 class="mb-1 me-1">{{ item.price }}</h4>
                                                <span class="text-danger"><s>NT$1,500</s></span>
                                            </div>
                                            <h6 class="text-success">Free shipping</h6>
                                            <div class="mt-4">
                                                <button class="btn btn-primary shadow-0" type="button">直接購買</button>
                                                <a href="#!" class="btn btn-light border px-2 pt-2 icon-hover">
                                                    <font-awesome-icon
                                                        icon="fas fa-heart fa-lg px-1"></font-awesome-icon>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import axiosapi from '@/plugins/axios.js';
const props = defineProps(["item"])
const photoPath = import.meta.env.VITE_API_PHOTO;

// 定義一個 ref 來存儲圖片來源
const imageSrc = ref(null);

// 定義一個函數來獲取圖片數據
const fetchImage = async () => {
    try {
        if (props.item && props.item.productImgs && props.item.productImgs.length > 0) {
            const response = await axiosapi.get(`${photoPath}${props.item.productImgs[0]}`);
            const base64Image = response.data.list.img;
            if (base64Image) {
                imageSrc.value = `data:image/png;base64,${base64Image}`;
            } else {
                imageSrc.value = "../../src/assets/images/no-image.jpg";
            }
        }else {
            imageSrc.value = "../../src/assets/images/no-image.jpg";
        }

    } catch (error) {
        console.error('讀取圖片失敗:', error);
        imageSrc.value = "../../src/assets/images/no-image.jpg";
    }
};

// 當組件掛載時調用 fetchImage 函數
onMounted(() => {
    fetchImage();
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