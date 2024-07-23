<template>
    <div>
        <div class="homepage-content">
        <div class="sidebar">
            <h3>商品分類</h3>
            <ul>
            <li>分類 1</li>
            <li>分類 2</li>
            <li>分類 3</li>
            </ul>
        </div>
        <div class="main-content">
            <h2>熱門商品</h2>
            <div class="products">
            <div class="product-card">商品圖 金額</div>
            <div class="product-card">商品圖 金額</div>
            </div>
            <h2>推薦商品</h2>
            <div class="products">
            <div class="product-card">商品圖 金額</div>
            <div class="product-card">商品圖 金額</div>
            </div>
        </div>
        </div>
    </div>
</template>
    
<script setup>

</script>
    
<style scoped>
    .homepage-content {
    display: flex;
    }

    .sidebar {
    width: 20%;
    background-color: #f0f0f0;
    padding: 20px;
    }

    .main-content {
    width: 80%;
    padding: 20px;
    }

    .products {
    display: flex;
    }

    .product-card {
    width: 150px;
    height: 150px;
    border: 1px solid #ddd;
    margin: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    }
</style>