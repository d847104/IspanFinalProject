<template>
<div class="container mt-5">
    <div class="row">
    <!-- 訂單相關 -->
    <div class="col-md-4 mb-4">
        <div class="card">
        <div class="card-body">
            <h5 class="card-title">訂單相關</h5>
            <p class="card-text">查看和管理訂單。</p>
            <button @click="navigateTo('/orders')" class="btn btn-primary">訂單查詢</button>
            <button @click="navigateTo('/reviews')" class="btn btn-primary mt-2">評價訂單</button>
            <button @click="navigateTo('/favorites')" class="btn btn-primary mt-2">收藏訂單</button>
        </div>
        </div>
    </div>

    <!-- 帳戶相關 -->
    <div class="col-md-4 mb-4">
        <div class="card">
        <div class="card-body">
            <h5 class="card-title">帳戶相關</h5>
            <p class="card-text">管理個人帳戶資訊。</p>
            <button @click="navigateTo('/profile')" class="btn btn-primary">個人資訊管理</button>
            <button @click="navigateTo('/change-password')" class="btn btn-primary mt-2">密碼管理</button>
            <button @click="navigateTo('/wallet')" class="btn btn-primary mt-2">錢包管理</button>
            <button @click="navigateTo('/bank-accounts')" class="btn btn-primary mt-2">銀行帳戶管理</button>
        </div>
        </div>
    </div>

    <!-- 客服相關 -->
    <div class="col-md-4 mb-4">
        <div class="card">
        <div class="card-body">
            <h5 class="card-title">客服相關</h5>
            <p class="card-text">聯繫客服和查看幫助。</p>
            <button @click="navigateTo('/customer-service')" class="btn btn-primary">聯繫客服</button>
            <button @click="navigateTo('/help-center')" class="btn btn-primary mt-2">幫助中心</button>
        </div>
        </div>
    </div>

    <!-- 瀏覽記錄 -->
    <div class="col-md-4 mb-4">
        <div class="card">
        <div class="card-body">
            <h5 class="card-title">瀏覽記錄</h5>
            <p class="card-text">查看和管理瀏覽過的商品。</p>
            <button @click="navigateTo('/history')" class="btn btn-primary">瀏覽記錄</button>
        </div>
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
    import Navbar from '../../layout/Navigation.vue';
    import { useRouter } from 'vue-router';

    const router = useRouter()

    const navigateTo = (path) => {
        router.push(path)
    }
</script>

<style scoped>
    .container {
    margin-top: 5rem;
    }

    .card {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    border: none;
    }

    .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    }

    .btn {
    width: 100%;
    }
</style>
