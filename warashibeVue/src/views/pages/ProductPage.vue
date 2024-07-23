<template>
<div class="product-page container">
    <div class="row">
    <div class="col-md-8">
        <div class="product-info">
        <h2>{{ product.productName }}</h2>
        <p>價格: ${{ product.price }}</p>
        <p>商品描述: {{ product.description }}</p>
        </div>
    </div>
    <div class="col-md-4">
        <img :src="productImgUrl" alt="Product Image" class="product-img">
    </div>
    </div>
    <div class="row mt-4">
    <div class="col-md-12">
        <button class="btn btn-primary" @click="addToFavorites">加入最愛</button>
        <button class="btn btn-secondary" @click="addToCart">加入購物車</button>
        <button class="btn btn-success" @click="buyNow">直接購買</button>
    </div>
    </div>
    <div class="row mt-4">
    <div class="col-md-12">
        <h3>查看更多</h3>
        <div class="row">
        <ProductCard v-for="product in relatedProducts" :key="product.productID" :product="product"></ProductCard>
        </div>
        <Paginate :page-count="pages" :click-handler="fetchRelatedProducts" :prev-text="'&lt;'" :next-text="'&gt;'" />
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axiosapi from '../plugins/axiosapi';
import Swal from 'sweetalert2';
import Paginate from 'vuejs-paginate-next';
import ProductCard from '@/components/ProductCard.vue';

const route = useRoute();
const productID = route.params.id;

const product = ref({});
const relatedProducts = ref([]);
const productImgUrl = ref('');
const total = ref(0);
const current = ref(1);
const pages = ref(0);
const rows = ref(4);
const start = ref(0);

const fetchProductDetails = async () => {
try {
    const response = await axiosapi.get(`/api/products/${productID}`);
    product.value = response.data;
    if (product.value.productImgs.length > 0) {
    productImgUrl.value = product.value.productImgs[0];
    }
    fetchRelatedProducts();
} catch (error) {
    console.error("Error fetching product details:", error);
    Swal.fire({
    text: "獲取商品詳情失敗：" + error.message,
    icon: "error"
    });
}
};

const fetchRelatedProducts = async (page = 1) => {
Swal.fire({
    text: "執行中.....",
    allowOutsideClick: false,
    showConfirmButton: false
});

try {
    start.value = (page - 1) * rows.value;
    current.value = page;

    const data = {
    start: start.value,
    max: rows.value,
    dir: false,
    order: "productID"
    };

    const response = await axiosapi.post(`/api/products/category/${product.value.subCategoryID}`, data);
    relatedProducts.value = response.data.list;
    total.value = response.data.count;
    pages.value = Math.ceil(total.value / rows.value);

    setTimeout(() => {
    Swal.close();
    }, 500);
} catch (error) {
    console.error("Error fetching related products:", error);
    Swal.fire({
    text: "獲取相關商品失敗：" + error.message,
    icon: "error"
    });
}
};

const addToFavorites = () => {
Swal.fire({
    text: "加入最愛功能尚未實現",
    icon: "info"
});
};

const addToCart = () => {
Swal.fire({
    text: "加入購物車功能尚未實現",
    icon: "info"
});
};

const buyNow = () => {
Swal.fire({
    text: "直接購買功能尚未實現",
    icon: "info"
});
};

onMounted(() => {
fetchProductDetails();
});
</script>

<style scoped>
.product-page {
margin-top: 20px;
}

.product-img {
width: 100%;
height: auto;
}

.related-products {
display: flex;
flex-wrap: wrap;
gap: 20px;
}

.related-product-card {
width: 150px;
border: 1px solid #ddd;
padding: 10px;
text-align: center;
}

.related-product-img {
width: 100%;
height: auto;
}
</style>
