<template>
    <div v-if="images.length" id="carouselExampleFade" class="carousel slide carousel-fade">
        <div class="carousel-inner">
            <div v-for="(image, index) in images" :key="image.imgID"
                :class="['carousel-item', { active: index === 0 }]">
                <img :src="`data:image/jpeg;base64,${image.img}`" class="d-block w-100"
                    :alt="`Image ${image.imgID}`">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade"
            data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade"
            data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <h2 class="lh-lg">熱門商品</h2>
    <div class="row">
        <compCard v-for="product in productsPopular" :key="product.productID" :product="product" @add-cart="addCart"></compCard>
    </div>
    <br>
    <h2 class="lh-lg">隨機精選</h2>
    <div class="row">
        <compCard v-for="product in productsRandom" :key="product.productID" :product="product" @add-cart="addCart"></compCard>
    </div>
    <br>
    <h2 class="lh-lg">物換心儀</h2>
    <div class="row">
        <compCard v-for="product in productsSecHand" :key="product.productID" :product="product" @add-cart="addCart"></compCard>
    </div>

</template>

<script setup>
    import { inject ,ref, onMounted } from 'vue';
    import compCard from '@/components/compCard.vue';
    import callPopular from '@/plugins/products/product_popular.js';
    import callSecondHand from '@/plugins/products/product_secondhand.js';
    import callRandom from '@/plugins/products/product_random';
    import axiosapi from '@/plugins/axios';
    import addCartApi from '@/plugins/cart_add';

    const isLogin = inject("isLogin");
    const productsPopular = ref([]);
    const startPopular = ref(0);
    const rowsPopular = ref(8);
    const currentPagePopular = ref(0);
    const totalPagesPopular = ref(0);
    const totalPopular = ref(0);
    const lastPageRowsPopular = ref(0);

    const productsRandom = ref([]);
    const startRandom = ref(0);
    const rowsRandom = ref(8);
    const currentPageRandom = ref(0);
    const totalPagesRandom = ref(0);
    const totalRandom = ref(0);
    const lastPageRowsRandom = ref(0);

    const productsSecHand = ref([]);
    const startSecHand = ref(0);
    const rowsSecHand = ref(8);
    const currentPageSecHand = ref(0);
    const totalPagesSecHand = ref(0);
    const totalSecHand = ref(0);
    const lastPageRowsSecHand = ref(0);

    const images = ref([]);

    onMounted(function () {
        callPopular(productsPopular, startPopular, rowsPopular, currentPagePopular, totalPagesPopular, totalPopular, lastPageRowsPopular);
        callRandom(productsRandom, startRandom, rowsRandom, currentPageRandom, totalPagesRandom, totalRandom, lastPageRowsRandom);
        callSecondHand(productsSecHand, startSecHand, rowsSecHand, currentPageSecHand, totalPagesSecHand, totalSecHand, lastPageRowsSecHand);
    })

    // 這邊先 Hard Code 會員ID, 待加入登入功能後應實際從 httpSession 取得
    const memberId = sessionStorage.getItem("memberID");
    function addCart(productId,sellerId,specOne,specTwo,quantity){
        addCartApi(memberId,productId,sellerId,specOne,specTwo,quantity);
    }
</script>

<style scoped>
    .carousel {
        width: 100%;
        /* 設定輪播寬度為容器的90% */
        height: 40vh;
        /* 設定輪播高度為螢幕高度的50% */
        margin: auto;
        /* 使輪播居中 */
        overflow: hidden;
        /* 隱藏溢出內容 */
    }

    .carousel-inner {
        height: 100%;
        /* 使輪播內部容器高度與輪播相同 */
        align-items: center;
        justify-content: center;

    }

    .carousel-item {

        /* 使用 flexbox 使圖片在輪播項目中居中 */
        height: 100%;
    }

    .carousel-item img {
        max-width: 100%;
        max-height: 100%;
        /* 確保圖片在容器內最大化，但不超過容器的大小 */
        object-fit: cover;
        /* 保持圖片比例，並填充容器 */
        display: block;
        /* 確保圖片是塊級元素 */
    }
</style>