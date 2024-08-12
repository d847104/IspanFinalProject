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
                                            <div class="bg-image hover-zoom ripple rounded ripple-surface me-md-3 mb-3 mb-md-0">
                                                <RouterLink :to="{name: 'pages-productpage-link', query: {productID: item.productID}}">
                                                    <img :src="imageSrc" :alt="item.productName" class="w-100" v-if="imageSrc" />
                                                    <p v-else>Loading...</p>
                                                </RouterLink>
                                                    <div class="hover-overlay">
                                                        <div class="mask" style="background-color: rgba(253, 253, 253, 0.15);"></div>
                                                    </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-6 col-md-5 col-sm-7">
                                            <h5>{{ item.productName }}</h5>
                                            <div class="d-flex flex-row">
                                                <div class="text-warning mb-1 me-2">
                                                    <span class="ms-1">評價：{{ productRank }}</span>
                                                </div> 
                                                <span class="text-muted">數量：{{ item.stock }}</span>
                                            </div>
                                            <p class="text mb-4 mb-md-0">
                                                {{ item.description }}
                                            </p>
                                        </div>
                                        <div class="col-xl-3 col-md-3 col-sm-5">
                                            <div class="d-flex flex-row align-items-center mb-1">
                                                <h4 class="mb-1 me-1">NT$ {{ item.price }}</h4>
                                            </div>
                                            <div class="mt-4">
                                                <!-- <a href="#"><font-awesome-icon :icon="['fas', 'cart-plus']" size="2x" pull="left" /></a>
                                                <button class="btn btn-primary shadow-0" type="button">直接購買</button>
                                                <a href="#!" class="btn btn-light border px-2 pt-2 icon-hover">
                                                    <font-awesome-icon icon="fas fa-heart fa-lg px-1"></font-awesome-icon>
                                                </a> -->
                                                <button class="btn btn-success" @click="addToFavorite">加入最愛</button>&nbsp;
                                                <!-- <button class="btn btn-primary" @click="addToCart">加入購物車</button>&nbsp; -->
                                                <!-- <button class="btn btn-danger" @click="buyNow">直接購買</button> -->
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
import { ref, onMounted, inject } from 'vue';
import axiosapi from '@/plugins/axios.js';
import Swal from 'sweetalert2';
import { RouterLink } from 'vue-router';
const props = defineProps(["item"]);
const imageSrc = ref(null); // 定義一個 ref 來存儲圖片來源
const productRank = ref(0);
const isLogin = inject("isLogin");
const quantity = ref(1);
const selectedSpecOne = ref('');
const selectedSpecTwo = ref('');

const addToFavorite = async () => {
    let data = {
            memberID: sessionStorage.getItem("memberID"),
            productID: props.item.productID,
            sellerID: props.item.memberID
        }
    console.log(data);
    console.log(props.item);
    try {
        await axiosapi.post('/ajax/favorite/insert', {
            memberID: sessionStorage.getItem("memberID"),
            productID: props.item.productID,
            sellerID: props.item.member
        });
        Swal.fire('成功', '已將該商品加入最愛', 'success');
        // 新增通知给卖家
        const notificationContent = `會員${sessionStorage.getItem("username")}喜歡了你的商品${props.product.productName}`;
        await axiosapi.post('/ajax/notification/insert', {
            content: notificationContent,
            isRead: false,
            notifyDate: new Date().toISOString().slice(0, 10),  // 当前日期
            receiverID: props.item.member,  // 商品卖家的ID
            senderID: sessionStorage.getItem("memberID"),
            orderID: null  // 这里设置为null
        });
    } catch (error) {
        console.error('加入最愛失敗', error);
        Swal.fire('失敗', '加入最愛失敗', 'error');
    }
};

const addToCart = async () => {
    if (!isLogin.value) {
        Swal.fire('請登入會員', '', 'warning');
        return;
    }
    console.log(props.item.specs);
    if ((!selectedSpecOneID.value || !selectedSpecTwoID.value) && props.item.specs) {
        Swal.fire('失敗', '請選擇完整的商品規格', 'error');
        return;
    }

    const data = {
        member: sessionStorage.getItem("memberID"),
        product: props.item.productID,
        // specOne: props.item.
        // specTwo: selectedSpecTwoID.value,
        quantity: quantity.value,
        seller: props.member,
    };

    try {
        await axiosapi.post('/api/cart/create', data);
        Swal.fire('成功', '已將該商品加入購物車', 'success');
    } catch (error) {
        console.error('加入購物車失敗', error);
        Swal.fire('失敗', '加入購物車失敗', 'error');
    }
};



// 定義一個函數來獲取圖片數據
const fetchImage = async () => {
    try {
        if (props.item && props.item.productImgs && props.item.productImgs.length > 0) {
            const response = await axiosapi.get(`/api/productImg/product/${props.item.productID}`);            
            const base64Image = response.data.list[0].img;
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
    getProductsRank();
});




async function getProductsRank() {
    try {
        const response = await axiosapi.get(`/api/ranks/product/${props.item.productID}`);
        const rankingList = Array.isArray(response.data.list) ? response.data.list : [];
        if (rankingList.length > 0) {
            // 提取 ranking 數值並計算總和
            const totalRanking = rankingList.reduce((sum, item) => sum + item.ranking, 0);
            // 計算平均值
            productRank.value = totalRanking / rankingList.length;
        } else {
            productRank.value = 0; // 如果沒有資料，設置平均值為 0 或其他適當值
        }
    } catch (error) {
        console.error('Error getting products rank:', error);
        productRank.value = 0; // 在錯誤情況下，設置平均值為 0 或其他適當值
    }
}








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