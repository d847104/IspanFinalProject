<template>
<div class="product-page">
    <div class="container">
    <div class="row">
        <!-- 左側商品圖片區域 -->
        <div class="col-md-7">
            <div class="product-image">
            <img :src="currentImage" class="main-image" alt="Product Image" />
                <div class="image-thumbnails-wrapper">
                    <button v-if="imageIndex > 0" @click="prevImages" class="carousel-control-prev">
                        <span class="carousel-control-prev-icon">&lt;</span>
                    </button>
                    <div class="thumbnails">
                        <img
                            v-for="(img, index) in displayedImages"
                            :key="img.imgID || img.specID"
                            :src="img.img || img.specImg"
                            class="thumbnail"
                            @mouseover="currentImage = img.img || img.specImg"
                            :class="{ active: currentImage === (img.img || img.specImg) }"
                        />
                    </div>
                    <button v-if="imageIndex + 5 < combinedImages.length" @click="nextImages" class="carousel-control-next">
                        <span class="carousel-control-next-icon">&gt;</span>
                    </button>
                </div>
            </div>
        </div>
        <!-- 右側商品資訊區域 -->
        <div class="col-md-5">
        <h1>{{ product.productName }}</h1>
        <p class="price">NT$ {{ product.price }}</p>
        <p class="description">{{ product.description }}</p>
        <div class="specs">
            <div v-for="(specs, specName) in groupedSpecs" :key="specName" class="spec">
                <h5 style="margin-top:15px;">{{ specName }}</h5>
                <div v-for="spec in specs" :key="spec.specOne || spec.specTwo" style="display:inline-block; margin-right: 10px;">
                    <button v-if="spec.specOne" class="btn btn-outline-secondary" @click="selectSpecOne(spec.specOne)">
                        {{ spec.specOne }}
                    </button>
                    <button v-if="spec.specTwo" class="btn btn-outline-secondary" @click="selectSpecTwo(spec.specTwo)">
                        {{ spec.specTwo }}
                    </button>
                </div>
            </div>
        </div>
        <div class="quantity">
            <button @click="decreaseQuantity">-</button>
            <input type="number" v-model="quantity" />
            <button @click="increaseQuantity">+</button>
        </div>
        <button class="btn btn-primary" @click="addToCart">加入購物車</button>
        <button class="btn btn-success" @click="buyNow">直接購買</button>
        </div>
    </div>
    <!-- 查看更多 -->
    <div class="row mt-4">
        <div class="col-md-12">
        <h3>查看更多</h3>
        <div class="row">
            <LittleProductCard v-for="product in relatedProducts" :key="product.productID" :product="product" />
        </div>
        <Paginate :page-count="pages" :click-handler="callFind" :prev-text="'&lt;'" :next-text="'&gt;'" />
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRoute } from 'vue-router';
import LittleProductCard from '@/components/LittleProductCard.vue';
import Paginate from 'vuejs-paginate-next';

const route = useRoute();
const productID = ref(route.query.productID || 10);
const product = ref({});
const productImages = ref([]);
const specImages = ref([]);
const combinedImages = ref([]);
const displayedImages = ref([]);
const currentImage = ref('');
const imageIndex = ref(0);
const productSpecs = ref([]); // 确保初始化productSpecs
const groupedSpecs = ref({});
const quantity = ref(1);
const selectedSpecOne = ref('');
const selectedSpecTwo = ref('');
const relatedProducts = ref([]);
const total = ref(0);
const pages = ref(0);
const current = ref(1);
const rows = ref(4);

const fetchProduct = async () => {
    // 清空与图片相关的数组
    productImages.value = [];
    specImages.value = [];
    combinedImages.value = [];
    displayedImages.value = [];
    currentImage.value = '';

    const { data } = await axiosapi.get(`/api/products/${productID.value}`);
    product.value = data;
    await fetchProductImages(data.productImgs);
    await fetchProductSpecs(data.productSpecs);
    combineImages();
    await fetchRelatedProducts(data.subCategory);
};

const fetchRelatedProducts = async (subCategoryID) => {
    const data = {
        start: (current.value - 1) * rows.value,
        max: rows.value,
        dir: false,
        order: 'productID',
    };

    const response = await axiosapi.post(`/api/products/category/${subCategoryID}`, data);
    const products = response.data.list;

    for (let product of products) {
        const images = [];
        for (let imgID of product.productImgs) {
            const { data } = await axiosapi.get(`/api/productImg/${imgID}`);
            images.push({ imgID, img: `data:image/jpeg;base64,${data.list.img}` });
        }
        product.productImgs = images;
    }

    relatedProducts.value = products;
    total.value = response.data.count;
    pages.value = Math.ceil(total.value / rows.value);
};

const fetchProductImages = async (imageIDs) => {
    const images = [];
    for (let imgID of imageIDs) {
        const { data } = await axiosapi.get(`/api/productImg/${imgID}`);
        images.push({ imgID, img: `data:image/jpeg;base64,${data.list.img}` });
    }
    productImages.value = images;
};

const fetchProductSpecs = async (specIDs) => {
    const specs = [];
    for (let specID of specIDs) {
        const { data } = await axiosapi.get(`/api/productSpec/${specID}`);
        specs.push(data.list);
        if (data.list.specImg) {
            specImages.value.push({ specID, specImg: `data:image/jpeg;base64,${data.list.specImg}` });
        }
    }
    productSpecs.value = specs;
    groupSpecs();
};

const groupSpecs = () => {
    const grouped = {};
    productSpecs.value.forEach(spec => {
        if (spec.specOneName) {
            if (!grouped[spec.specOneName]) {
                grouped[spec.specOneName] = [];
            }
            grouped[spec.specOneName].push({ specOne: spec.specOne });
        }
        if (spec.specTwoName) {
            if (!grouped[spec.specTwoName]) {
                grouped[spec.specTwoName] = [];
            }
            grouped[spec.specTwoName].push({ specTwo: spec.specTwo });
        }
    });
    groupedSpecs.value = grouped;
};

const combineImages = () => {
    combinedImages.value = [...productImages.value, ...specImages.value];
    if (combinedImages.value.length > 0) {
        currentImage.value = combinedImages.value[0].img || combinedImages.value[0].specImg;
        updateDisplayedImages();
    }
};

const updateDisplayedImages = () => {
    displayedImages.value = combinedImages.value.slice(imageIndex.value, imageIndex.value + 5);
};

const prevImages = () => {
    if (imageIndex.value > 0) {
        imageIndex.value -= 1;
        updateDisplayedImages();
    }
};

const nextImages = () => {
    if (imageIndex.value + 5 < combinedImages.value.length) {
        imageIndex.value += 1;
        updateDisplayedImages();
    }
};

const decreaseQuantity = () => {
    if (quantity.value > 1) quantity.value -= 1;
};

const increaseQuantity = () => {
    quantity.value += 1;
};

const selectSpecOne = (spec) => {
    selectedSpecOne.value = spec;
};

const selectSpecTwo = (spec) => {
    selectedSpecTwo.value = spec;
};

const addToCart = () => {
    console.log('加入購物車');
};

const buyNow = () => {
    console.log('直接購買');
};

const callFind = (page) => {
    current.value = page;
    fetchRelatedProducts(product.value.subCategory);
};

onMounted(fetchProduct);

// 监视路由参数变化并重新获取数据
watch(() => route.query.productID, (newProductID) => {
    productID.value = newProductID;
    fetchProduct();
});
</script>

<style scoped>
.product-page {
    padding: 2rem;
}

.product-image {
    position: relative;
}

.main-image {
    width: 100%;
    max-height: 400px;
    object-fit: contain;
}

.image-thumbnails-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    margin-top: 1rem;
}

.thumbnails {
    display: flex;
    align-items: center;
}

.thumbnail {
    width: 100px;
    height: 100px;
    margin: 0 5px;
    object-fit: contain;
    cursor: pointer;
}

.thumbnail.active {
    border: 2px solid #007bff;
}

.carousel-control-prev,
.carousel-control-next {
    background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
    border: none;
    color: white; /* 白色字体 */
    font-size: 1.5rem; /* 减小字体大小 */
    cursor: pointer;
    padding: 0 5px; /* 减小按钮的内边距，缩小宽度 */
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    width: 30px; /* 确保按钮条更窄 */
    height: 100%; /* 按钮高度与缩略图一致 */
    z-index: 1; /* 保证按钮在图片前面 */
}

.carousel-control-prev {
    left: 65px; /* 控制按钮与缩略图的距离 */
}

.carousel-control-next {
    right: 65px; /* 控制按钮与缩略图的距离 */
}

.carousel-control-prev:hover,
.carousel-control-next:hover {
    background-color: rgba(0, 0, 0, 0.7); /* 悬停时背景色更深 */
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
    font-size: 0rem; /* 增大箭头图标的大小 */
    color: white; /* 白色箭头 */
}

.specs {
    margin: 1rem 0;
}

.spec-img {
    width: 50px;
    height: 50px;
    object-fit: contain;
}

.quantity {
    display: flex;
    align-items: center;
    margin: 1rem 0;
}

.quantity button {
    width: 30px;
    height: 30px;
}

.quantity input {
    width: 50px;
    text-align: center;
    margin: 0 5px;
}
</style>
