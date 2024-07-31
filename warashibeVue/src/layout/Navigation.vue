<template>
    <div class="background"></div>
    <nav class="navbar navbar-expand-lg fixed-top ">
        <div class="container-fluid">
            <!-- 網站LOGO -->
            <RouterLink class="navbar-brand" to="/">
                <img src="/src/img/logoBSS.png" alt="Logo" class="logo navbar-brand">
                <RouterLink class="navbar-brand" :to="{ name: 'home' }" style="color:#3C3C3C;">物換心儀</RouterLink>
            </RouterLink>

            <!-- 漢堡選單按鈕 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mb-2 mb-lg-0 w-100">
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="#">商城/二手</RouterLink>
                    </li>
                    <!-- 搜尋列 -->
                    <form class="d-flex search-form d-none d-lg-flex mx-auto" role="search">
                        <input class="form-control me-1 search-bar" type="search" placeholder="Search"
                            aria-label="Search">
                        <button class="btn btn-outline-secondary " type="submit">
                            <font-awesome-icon icon="fa-solid fa-search" />
                        </button>
                    </form>
                    <!-- 右側功能選單 -->
                    <li class="nav-item d-none d-lg-block">
                        <RouterLink class="nav-link" to="#">
                            <font-awesome-icon :icon="['fas', 'cart-shopping']" size="2x" />
                        </RouterLink>
                    </li>
                    <li class="nav-item d-none d-lg-block">
                        <RouterLink class="nav-link" to="#">
                            <font-awesome-icon icon="fa-solid fa-comments" size="2x" />
                        </RouterLink>
                    </li>
                    <li class="nav-item d-none d-lg-block">
                        <RouterLink class="nav-link" :to="{ path: '/other/notification' }">
                            <font-awesome-icon icon="fa-solid fa-bell" size="2x" />
                        </RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/secure/login">登入/註冊</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" :to="{ path: '/secure/member' }">會員中心</RouterLink>
                    </li>

                    <!-- 會員相關 Dropdown List -->
                    <li class="nav-item dropdown" @click="toggleDropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            會員相關
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li>
                                <RouterLink class="dropdown-item" :to="{ name: 'members-basicinformation-link' }">會員基本資料
                                </RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" :to="{ name: 'buyer-BuyerOrder-link' }">
                                    買家訂單
                                </RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/seller-orders')">賣家訂單
                                </RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="#"
                                    @click="checkAuth('/seller/sellermanageproduct')">賣家商品管理</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/add-product')">上架商品
                                </RouterLink>
                            </li>
                        </ul>
                    </li>
                    <a>HI~{{ memberID }}</a>
                </ul>
            </div>
        </div>
    </nav>
    <nav class="navbar navbar-expand-lg bg-body-tertiary container custom-navbar">
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav w-100 justify-content-evenly">
                <template v-for="mainCategory in mainCategories">
                    <li class="nav-item" v-if="mainCategory.subCategories.length == 0">
                        <a class="nav-link fs-5" href="#">{{ mainCategory.mainCategory }}</a>
                    </li>
                    <li class="nav-item dropdown" v-if="mainCategory.subCategories.length !== 0">
                        <a class="nav-link dropdown-toggle fs-5 fw-bold" href="#" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">{{
                                mainCategory.mainCategory }}</a>
                        <ul class="dropdown-menu">
                            <template v-for="subCategory in subCategories">
                                <li v-if="subCategory.mainCategory == mainCategory.mainCategoryID">
                                    <a class="dropdown-item fs-5 fw-bold" href="#">{{ subCategory.subCategory
                                        }}</a>
                                </li>
                            </template>
                        </ul>
                    </li>
                </template>
            </ul>
        </div>
    </nav>


</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
let memberID = sessionStorage.getItem("memberID");// 抓登入ID

// const user = ref(null) // 模擬登入狀態，後續可以替換為實際邏輯
const router = useRouter()

// const checkAuth = (path) => {
//if (!user.value) <-實際是這個,測試先不開這個功能
//     if (user.value) {
//         router.push('/login')
//     } else {
//         router.push(path)
//     }
// }

// const toggleDropdown = () => {
//     isDropdownOpen.value = !isDropdownOpen.value;
// };


import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const mainCategories = ref([]);
const subCategories = ref([]);

axiosapi.get("/api/categories/mainCategory/all")
    .then(function (response) {
        mainCategories.value = response.data.list;
    }).catch(function (error) {
        console.log("navBar.vue: getMainCategory", error);
        Swal.fire({
            icon: "error",
            text: "navBar.vue: getMainCategory() 錯誤" + error.message,
            allowOutsideClick: false,
        })
    });

axiosapi.get("/api/categories/subCategory/all")
    .then(function (response) {
        subCategories.value = response.data.list;
    }).catch(function (error) {
        console.log("navBar.vue: getSubCategory", error);
        Swal.fire({
            icon: "error",
            text: "navBar.vue: getSubCategory() 錯誤" + error.message,
            allowOutsideClick: false,
        })
    });


</script>

<style scoped>
.navbar {
    padding: 5px 15px;
    width: 95%;
    background: transparent;
    font-size: 1rem;
    /* z-index: 1000; */
    /* 確保 navbar 顯示在輪播上方 */
    line-height: calc(50px * 0.8);
    /* 調整行高以保持內容居中 */
}

.custom-navbar {
    padding: 10px 15px;
    /* 調整padding使其高度為原來的兩倍 */
}

.logo {
    width: 100px;
    height: auto;
}

.nav-item {
    margin: 0 5px;
    text-decoration: none;
    color: #fff;
}

.nav-link {
    font-size: 1.1rem;
}

.search-form {
    width: 30%;
    /* 調整搜尋框寬度 */
    max-width: 1000px;
}

.search-bar {
    width: 100%;
    padding: 10px;
    font-size: 1rem;
}

.search-button {
    padding: 10px 15px;
    font-size: 1rem;
}

.w-100 {
    display: flex;
    justify-content: space-between;
    align-items: center;
}




/* 背景設定&圖 STAR*/
.background {
    background-image: url('/src/img/bluesky.jpg');
    position: fixed;
    background-repeat: no-repeat;
    background-attachment: fixed;
    /* 背景圖固定，滾動時不移動 */
    width: 100%;
    height: 100%;
    min-height: 100vh;
    /* 確保背景圖佔滿整個可視區域 */
    overflow: hidden;
    display: flex;
    flex-direction: column;
    z-index: -1;
}

/* 背景設定&圖 END*/
</style>
