<template>
<nav class="navbar navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
    <!-- 網站LOGO -->
    <RouterLink class="navbar-brand" to="/">
        <img src="@/assets/logo.png" alt="Logo" class="logo">
    </RouterLink>
    
    <!-- 左側導航連結 -->
    <ul class="navbar-nav inline-nav">
        <li class="nav-item">
        <RouterLink class="nav-link" to="/">Home</RouterLink>
        </li>
        <li class="nav-item">
        <RouterLink class="nav-link" to="#">商品分類</RouterLink>
        </li>
        <li class="nav-item">
        <RouterLink class="nav-link" to="#">商城/二手</RouterLink>
        </li>
        <li class="nav-item">
        <RouterLink class="nav-link" to="#">
            <font-awesome-icon icon="fa-solid fa-shopping-cart" />
        </RouterLink>
        </li>
    </ul>
    
    <!-- 搜尋列 -->
    <form class="d-flex search-form" role="search">
        <input class="form-control me-2 search-bar" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success search-button" type="submit">
        <font-awesome-icon icon="fa-solid fa-search" />
        </button>
    </form>

    <ul class="navbar-nav inline-nav">
        <!-- 聊天室 -->
        <li class="nav-item">
        <RouterLink class="nav-link" to="#">
            <font-awesome-icon icon="fa-solid fa-comments" />
        </RouterLink>
        </li>
        <!-- 小鈴鐺 -->
        <li class="nav-item">
        <RouterLink class="nav-link" to="#">
            <font-awesome-icon icon="fa-solid fa-bell" />
        </RouterLink>
        </li>
        <!-- 登入 -->
        <li class="nav-item" v-show="!user">
            <RouterLink class="nav-link" to="#">登入</RouterLink>
        </li>

        <!-- 註冊 -->
        <li class="nav-item" v-show="!user">
            <RouterLink class="nav-link" to="#">註冊</RouterLink>
        </li>
        
        <!-- 會員資訊 -->
        <li class="nav-item" v-show="user">
            <RouterLink class="nav-link" to="#">會員資訊</RouterLink>
        </li>
    </ul>
    
    <!-- 右側功能選單(漢堡選單) -->
    <ul class="navbar-nav">
        <li class="nav-item">
        <button class="btn nav-link" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasDarkNavbar" aria-controls="offcanvasDarkNavbar">
            <font-awesome-icon icon="fa-solid fa-bars" />
        </button>
        </li>
    </ul>
    </div>

    <!-- Offcanvas 菜單 -->
    <div class="offcanvas offcanvas-end text-bg-dark" tabindex="-1" id="offcanvasDarkNavbar" aria-labelledby="offcanvasDarkNavbarLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasDarkNavbarLabel">Dark offcanvas</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    
    <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">        
            <li class="nav-item">
                <RouterLink class="nav-link" to="#" @click="checkAuth('/profile')">會員基本資料</RouterLink>
            </li>
            <li class="nav-item">
                <RouterLink class="nav-link" to="#" @click="checkAuth('/buyer-orders')">買家訂單管理</RouterLink>
            </li>
            <li class="nav-item">
                <RouterLink class="nav-link" to="#" @click="checkAuth('/seller-orders')">賣家訂單管理</RouterLink>
            </li>
            <li class="nav-item">
                <RouterLink class="nav-link" to="#" @click="checkAuth('/seller-products')">賣家商品管理</RouterLink>
            </li>
            <li class="nav-item">
                <RouterLink class="nav-link" to="#" @click="checkAuth('/add-product')">上架商品頁面</RouterLink>
            </li>
        </ul>
    </div>
    </div>
</nav>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const user = ref(null) // 模擬登入狀態，後續可以替換為實際邏輯
const router = useRouter()

const checkAuth = (path) => {
if (!user.value) {
    router.push('/login')
} else {
    router.push(path)
}
}
</script>

<style scoped>
.navbar {
display: flex;
justify-content: space-between;
align-items: center;
padding: 15px 30px; /* 放大 padding */
background-color: #343a40;
border-bottom: 1px solid #e7e7e7;
font-size: 1.5rem; /* 放大整個導航列的字體 */
}

.logo {
width: 100px; /* 放大 logo */
height: auto;
}

.nav-item {
margin: 0 15px; /* 放大 margin */
text-decoration: none;
color: #fff;
}

.nav-link {
font-size: 1.5rem; /* 放大導航連結的字體 */
}

.inline-nav {
display: flex;
flex-direction: row;
align-items: center;
}

.search-form {
width: 50%; /* 放大搜尋列的最大寬度 */
max-width: 800px;
margin: 0 auto; /* 置中顯示 */
}

.search-bar {
width: 100%;
padding: 10px; /* 放大 padding */
font-size: 1.5rem; /* 放大字體 */
}

.search-button {
padding: 10px 15px; /* 放大 padding */
font-size: 1.5rem; /* 放大字體 */
}
</style>
