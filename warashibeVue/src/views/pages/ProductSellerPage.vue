<!-- ===================================================================================== -->
<template>
    <div class="container mt-5">
        <div class="row">

            <!-- 右側內容區域 -->
            <div class="col-md-9">
                <!-- 搜尋欄和賣家資訊 -->
                <div class="d-flex justify-content-between mb-3">

                    <div class="col-md-4">
                        <img src="" alt="賣家" class="img-fluid">
                    </div>
                    <div class="col-md-4">
                        <p>{{  }}</p>
                    </div>
                    <div>
                        <p>會員資訊</p>
                        <p>Rate: 4.5/5.0</p>
                        <p>Comments: 105</p>
                        <p>Message to Seller</p>
                    </div>
                </div>
                <!-- 商品列表 -->
                <div class="product-list">
                    <div class="product-item mb-4" v-for="(product, index) in products" :key="index">
                        <div class="row">
                            <div class="col-md-4">
                                <img :src="product.image" alt="商品" class="img-fluid">
                            </div>
                            <div class="col-md-8">
                                <h5>{{ product.name }}</h5>
                                <p>{{ product.description }}</p>
                                <p>NT${{ product.price }}</p>
                                <p>數量: {{ product.stock }}</p>
                                <div class="d-flex justify-content-between">
                                    <div>
                                        <button class="btn btn-outline-primary">加入購物車</button>
                                        <button class="btn btn-outline-primary">我的收藏</button>
                                    </div>
                                    <!-- <div>
                                        <button class="btn btn-outline-secondary">-</button>
                                        <input type="text" class="form-control d-inline-block" style="width: 50px;"
                                            v-model="product.quantity">
                                        <button class="btn btn-outline-secondary">+</button>
                                    </div> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 分頁排序 -->
                <div class="sort-by mt-4">
                    <p>Sort By:</p>
                    <button class="btn btn-outline-secondary">Popularity</button>
                    <button class="btn btn-outline-secondary">Price</button>
                    <button class="btn btn-outline-secondary">Latest</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const products = ref([
    {
        name: '商品名稱1',
        description: '商品描述1',
        price: 59,
        stock: 20,
        image: 'path/to/image1.jpg',
        quantity: 1,
    },
    {
        name: '商品名稱2',
        description: '商品描述2',
        price: 69,
        stock: 15,
        image: 'path/to/image2.jpg',
        quantity: 1,
    },
    // 可以添加更多的商品
]);
</script>

<style scoped>
.product-item {
    border-bottom: 1px solid #ccc;
    padding-bottom: 10px;
    margin-bottom: 10px;
}
</style>