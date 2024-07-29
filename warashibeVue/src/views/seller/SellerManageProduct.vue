<template>
<div class="seller-manage-product">
    <h1>上架商品</h1>
    <div class="product-list">
    <div class="product-item" v-for="product in onSaleProducts" :key="product.id">
        <div class="product-info">
        <img :src="product.image" alt="商品圖片" class="product-image" />
        <div class="product-details">
            <p>商品名稱: {{ product.name }}</p>
            <div class="quantity">
            數量: 
            <button @click="decreaseQuantity(product)">-</button>
            <input type="number" v-model="product.quantity" />
            <button @click="increaseQuantity(product)">+</button>
            </div>
            <div class="price">
            定價: 
            <button @click="decreasePrice(product)">-</button>
            <input type="number" v-model="product.price" />
            <button @click="increasePrice(product)">+</button>
            </div>
            <p>已售出量: {{ product.sold }}</p>
            <p>總數量: {{ product.total }}</p>
        </div>
        </div>
        <button class="action-button" @click="takeOffProduct(product)">下架</button>
        <button class="action-button" @click="deleteProduct(product)">刪除</button>
    </div>
    </div>
    <hr />
    <h1>下架商品</h1>
    <div class="product-list">
    <div class="product-item" v-for="product in offSaleProducts" :key="product.id">
        <div class="product-info">
        <img :src="product.image" alt="商品圖片" class="product-image" />
        <div class="product-details">
            <p>商品名稱: {{ product.name }}</p>
            <div class="quantity">
            數量: 
            <button @click="decreaseQuantity(product)">-</button>
            <input type="number" v-model="product.quantity" />
            <button @click="increaseQuantity(product)">+</button>
            </div>
            <div class="price">
            定價: 
            <button @click="decreasePrice(product)">-</button>
            <input type="number" v-model="product.price" />
            <button @click="increasePrice(product)">+</button>
            </div>
            <p>未售出量: {{ product.unsold }}</p>
            <p>總數量: {{ product.total }}</p>
        </div>
        </div>
        <button class="action-button" @click="putOnProduct(product)">上架</button>
        <button class="action-button" @click="deleteProduct(product)">刪除</button>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref } from 'vue';

const onSaleProducts = ref([
{ id: 1, name: '商品一', image: '/src/img/product1.jfif', quantity: 10, price: 100, sold: 5, total: 15 },
{ id: 2, name: '商品二', image: '/src/img/product2.jfif', quantity: 20, price: 200, sold: 10, total: 30 },
]);

const offSaleProducts = ref([
{ id: 3, name: '商品三', image: '/src/img/product3.jfif', quantity: 5, price: 50, unsold: 5, total: 5 },
]);

const decreaseQuantity = (product) => {
if (product.quantity > 0) product.quantity--;
};

const increaseQuantity = (product) => {
product.quantity++;
};

const decreasePrice = (product) => {
if (product.price > 0) product.price--;
};

const increasePrice = (product) => {
product.price++;
};

const takeOffProduct = (product) => {
// 將商品下架的邏輯
};

const putOnProduct = (product) => {
// 將商品上架的邏輯
};

const deleteProduct = (product) => {
// 刪除商品的邏輯
};
</script>

<style scoped>
.seller-manage-product {
padding: 20px;
}

.product-list {
margin-bottom: 20px;
}

.product-item {
border: 1px solid #ddd;
padding: 10px;
margin-bottom: 10px;
display: flex;
align-items: center;
justify-content: space-between;
}

.product-info {
display: flex;
align-items: center;
}

.product-image {
width: 100px;
height: 100px;
margin-right: 10px;
}

.product-details {
display: flex;
flex-direction: column;
}

.quantity, .price {
display: flex;
align-items: center;
}

.quantity button, .price button {
margin: 0 5px;
}

.action-button {
margin-left: 10px;
}
</style>
