    <template>
        <div class="container p-4">
            <div id="carouselExampleFade" class="carousel slide carousel-fade">
                <div class="carousel-inner">
                    <!-- 使用 v-for 遍歷 productImgs 並顯示每張圖片 -->
                    <div class="carousel-item" :class="{ active: index === 0 }"
                        v-for="(productImg, index) in productImgs" :key="productImg.imgID">
                        <!-- 使用 VITE_API_PHOTO 作為圖片 URL 的基本路徑 -->
                        <p>{{ productImg.img }}</p>
                        <img :src="getImageSrc(productImg.img)" :alt="productImg.img" class="d-block w-100"
                            @error="handleImageError">
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
                <compCard v-for="product in productsPopular" :key="product.productID" :product="product"></compCard>
            </div>
            <br>
            <h2 class="lh-lg">隨機精選</h2>
            <div class="row">
                <compCard v-for="product in productsRandom" :key="product.productID" :product="product"></compCard>
            </div>
            <br>
            <h2 class="lh-lg">物換心儀</h2>
            <div class="row">
                <compCard v-for="product in productsSecHand" :key="product.productID" :product="product"></compCard>
            </div>
        </div>
    </template>

<script setup>
import { ref, onMounted } from 'vue';
import compCard from '@/components/compCard.vue';
import callPopular from '@/plugins/products/product_popular.js';
import callSecondHand from '@/plugins/products/product_secondhand.js';
import callRandom from '@/plugins/products/product_random';
import axios from 'axios';
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

const productImgs = ref([]);

onMounted(function () {
    callPopular(productsPopular, startPopular, rowsPopular, currentPagePopular, totalPagesPopular, totalPopular, lastPageRowsPopular);
    callRandom(productsRandom, startRandom, rowsRandom, currentPageRandom, totalPagesRandom, totalRandom, lastPageRowsRandom);
    callSecondHand(productsSecHand, startSecHand, rowsSecHand, currentPageSecHand, totalPagesSecHand, totalSecHand, lastPageRowsSecHand);
})

// 載入數據
onMounted(async () => {
    try {
        const response = await axios.get('/api/productImg/all');

        console.log('API Response:', response.data); // 打印 API 響應數據
        productImgs.value = response.data; // 確保這裡的數據格式與預期一致
        
    } catch (error) {
        console.error('Error fetching product images:', error);
    }
});

// 確保圖片數據有效
function getImageSrc(base64Data) {
    if (!base64Data || typeof base64Data !== 'string') {
        console.warn('Base64 data is missing or invalid:', base64Data);
        return 'path/to/default/image.jpg';
    }
    return `data:image/jpeg;base64,${base64Data}`;
}  
// 處理圖片加載錯誤
function handleImageError(event) {
    event.target.src = 'path/to/default/image.jpg'; // 替換為預設圖片路徑
}

 


</script>

<style scoped>
.carousel {
    width: 80vw;
    /* 設定輪播寬度為螢幕寬度的80% */
    height: 50vh;
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