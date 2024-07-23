<template>
<div class="product-page">
    <div class="container">
    <div class="row">
        <!-- 左側商品圖片區域 -->
        <div class="col-md-7">
        <div class="product-image">
            <img :src="currentImage" class="main-image" alt="Product Image" />
            <div class="image-thumbnails">
            <button v-if="imageIndex > 0" @click="prevImages" class="carousel-control-prev">
                <span class="carousel-control-prev-icon"></span>
            </button>
            <div class="thumbnails">
                <img
                v-for="(img, index) in displayedImages"
                :key="img.imgID"
                :src="img.img"
                class="thumbnail"
                @mouseover="currentImage = img.img"
                :class="{ active: currentImage === img.img }"
                />
            </div>
            <button v-if="imageIndex + 5 < productImages.length" @click="nextImages" class="carousel-control-next">
                <span class="carousel-control-next-icon"></span>
            </button>
            </div>
        </div>
        </div>
        <!-- 右側商品資訊區域 -->
        <div class="col-md-5">
        <h1>{{ product.productName }}</h1>
        <p class="price">NT$ {{ product.price }}</p>
        <p class="description">{{ product.description }}</p>
        <p class="stock">庫存: {{ product.stock }}</p>
        <div class="specs">
            <div v-for="spec in productSpecs" :key="spec.specID" class="spec">
            <h5>{{ spec.specOneName }}: {{ spec.specOne }}</h5>
            <h5>{{ spec.specTwoName }}: {{ spec.specTwo }}</h5>
            <img :src="`data:image/jpeg;base64,${spec.specImg}`" class="spec-img" alt="Spec Image">
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
            <LittleProductCard v-for="product in relatedProducts" :key="product.productID" 
            :product="product" @custom-open="openModal" @custom-delete="callDelete"></LittleProductCard>
        </div>
        <Paginate :page-count="pages" :click-handler="callFind" :prev-text="'&lt;'" :next-text="'&gt;'" />
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRoute } from 'vue-router';
import LittleProductCard from '@/components/LittleProductCard.vue';
import Paginate from 'vuejs-paginate-next';

const route = useRoute();
// const productID = route.params.id;
const productID = 10;
const product = ref({});
const productImages = ref([]);
const productSpecs = ref([]);
const currentImage = ref('');
const imageIndex = ref(0);
const displayedImages = ref([]);
const quantity = ref(1);

const relatedProducts = ref([]);
const total = ref(0);
const pages = ref(0);
const current = ref(1);
const rows = ref(4);

const fetchProduct = async () => {
    const { data } = await axiosapi.get(`/api/products/${productID}`);
    product.value = data;
    await fetchProductImages(data.productImgs);
    await fetchProductSpecs(data.productSpecs);
    await fetchRelatedProducts(data.subCategory);
};

const fetchProductImages = async (imageIDs) => {
const images = [];
for (let imgID of imageIDs) {
    const { data } = await axiosapi.get(`/api/productImg/${imgID}`);
    images.push({ imgID, img: `data:image/jpeg;base64,${data.list.img}` });
}
productImages.value = images;
currentImage.value = images[0].img;
updateDisplayedImages();
};

const fetchProductSpecs = async (specIDs) => {
const specs = [];
for (let specID of specIDs) {
    const { data } = await axiosapi.get(`/api/productSpec/${specID}`);
    specs.push(data.list);
}
productSpecs.value = specs;
};

const fetchRelatedProducts = async (subCategoryID) => {
    const data = {
        "start": (current.value - 1) * rows.value,
        "max": rows.value,
        "dir": false,
        "order": "productID"
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

const updateDisplayedImages = () => {
displayedImages.value = productImages.value.slice(imageIndex.value, imageIndex.value + 5);
};

const prevImages = () => {
if (imageIndex.value > 0) {
    imageIndex.value -= 1;
    updateDisplayedImages();
}
};

const nextImages = () => {
if (imageIndex.value + 5 < productImages.value.length) {
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
height: auto;
}

.image-thumbnails {
display: flex;
align-items: center;
justify-content: center;
margin-top: 1rem;
}

.thumbnails {
display: flex;
overflow-x: auto;
}

.thumbnail {
width: 60px;
height: 60px;
margin: 0 5px;
cursor: pointer;
}

.thumbnail.active {
border: 2px solid #007bff;
}

.carousel-control-prev,
.carousel-control-next {
cursor: pointer;
background-color: rgba(0, 0, 0, 0.5);
border: none;
}

.specs {
margin: 1rem 0;
}

.spec-img {
width: 50px;
height: auto;
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
