<template>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top container">
    <div class="container-fluid">
        <!-- 網站LOGO -->
        <RouterLink class="navbar-brand" to="/">
            <img src="@/assets/logo.png" alt="Logo" class="logo navbar-brand">
        </RouterLink>

    <!-- 漢堡選單按鈕 -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mb-2 mb-lg-0 w-100">
        <li class="nav-item">
            <RouterLink class="nav-link" to="#">商城/二手</RouterLink>
        </li>
        <!-- 搜尋列 --> 
        <form class="d-flex search-form d-none d-lg-flex mx-auto" role="search">
            <input class="form-control me-1 search-bar" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success search-button" type="submit">
            <font-awesome-icon icon="fa-solid fa-search" />
            </button>
        </form>
        <!-- 右側功能選單 --> 
        <li class="nav-item d-none d-lg-block">
            <RouterLink class="nav-link" :to="{name: 'cart'}">
                <font-awesome-icon :icon="['fas', 'cart-shopping']" size="2x" />
            </RouterLink>
        </li>
        <li class="nav-item d-none d-lg-block">
            <RouterLink class="nav-link" to="#">
            <font-awesome-icon icon="fa-solid fa-comments" size="2x" />
            </RouterLink>
        </li>
        <li class="nav-item d-none d-lg-block">
            <RouterLink class="nav-link" :to="{path:'/other/notification'}">
            <font-awesome-icon icon="fa-solid fa-bell" size="2x" />
            </RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink class="nav-link" to="/secure/login">登入/註冊</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink class="nav-link" :to="{path:'/secure/member'}">會員中心</RouterLink>
        </li>
        <!-- 會員相關 Dropdown List --> 
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            會員相關
            </a>
            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
            <li><RouterLink class="dropdown-item" to="#" @click="checkAuth('/profile')">會員基本資料</RouterLink></li>
            <li><RouterLink class="dropdown-item" to="#" @click="checkAuth('/buyer/buyerorder')">買家訂單</RouterLink></li>
            <li><RouterLink class="dropdown-item" to="#" @click="checkAuth('/seller-orders')">賣家訂單</RouterLink></li>
            <li><RouterLink class="dropdown-item" to="#" @click="checkAuth('/seller/sellermanageproduct')">賣家商品管理</RouterLink></li>
            <li><RouterLink class="dropdown-item" to="#" @click="checkAuth('/pages/productUploadPage')">上架商品</RouterLink></li>
            </ul>
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
    //if (!user.value) <-實際是這個,測試先不開這個功能
    if (user.value) {
        router.push('/login')
    } else {
        router.push(path)
    }
}
</script>

<style scoped>
.navbar {
padding: 5px 15px;
background-color: #343a40;
border-bottom: 1px solid #e7e7e7;
font-size: 1.5rem;
}

.logo {
width: 100px;
height: auto;
}

.nav-item {
margin: 0 15px;
text-decoration: none;
color: #fff;
}

.nav-link {
font-size: 1.5rem;
}

.search-form {
width: 30%; /* 調整搜尋框寬度 */
max-width: 1000px;
}

.search-bar {
width: 100%;
padding: 10px;
font-size: 1.5rem;
}

.search-button {
padding: 10px 15px;
font-size: 1.5rem;
}

.w-100 {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

@media (max-width: 992px) {
    .search-form {
        display: none;
    }
}
</style>
