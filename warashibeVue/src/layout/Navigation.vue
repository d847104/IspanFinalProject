<template>
    <!-- <div class="container"> -->
    <!-- navbar黑色 -->
    <!-- <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top"> -->
    <!-- navbar透明 -->
    <nav class="navbar navbar-expand-lg sticky-top">

        <div class="container-fluid">
            <!-- 網站LOGO -->
            <RouterLink class="navbar-brand" to="/">
                <img src="@/img/logo.png" alt="Logo" class="logo navbar-brand">
            </RouterLink>
            <!-- 漢堡選單按鈕 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mb-2 mb-lg-0 w-100">
                    <!-- 左側導航連結 -->
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="/"><font-awesome-icon icon="fa-solid fa-house" /></RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" to="#">商城/二手</RouterLink>
                    </li>
                    <!-- 搜尋列 -->
                    <form class="d-flex search-form d-none d-lg-flex mx-auto" role="search">
                        <input class="form-control me-1 search-bar" type="search" placeholder="Search"
                            aria-label="Search">
                        <button class="btn btn-outline-secondary search-button" type="submit">
                            <font-awesome-icon icon="fa-solid fa-search" />
                        </button>
                    </form>
                    <!-- 右側功能選單 -->
                    <li class="nav-item d-none d-lg-block">
                        <RouterLink class="nav-link" to="#">
                            <font-awesome-icon icon="fa-solid fa-comments" />
                        </RouterLink>
                    </li>
                    <li class="nav-item d-none d-lg-block" @mouseover="showPopup" @mouseleave="hidePopup">
                        <NotificationPop :popupVisible="popupVisible" />
                    </li>
                    <li class="nav-item" v-if="!user">
                        <RouterLink class="nav-link" to="/secure/login"><font-awesome-icon
                                :icon="['fas', 'right-to-bracket']" /></RouterLink>
                    </li>
                    <li class="nav-item" v-if="!user">
                        <RouterLink class="nav-link" to="/secure/registerOne"><font-awesome-icon
                                icon="fa-solid fa-user-plus" /></RouterLink>
                    </li>
                    <li class="nav-item" v-if="user">
                        <span class="nav-link">HI~{{ user?.username }}</span>
                    </li>
                    <li class="nav-item" v-if="user">
                        <button class="nav-link active" @click="logout"><font-awesome-icon
                                icon="fa-solid fa-right-from-bracket" /></button>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" :to="{ path: '/secure/member' }">會員中心</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link" :to="{ name: 'pages-checkout-link' }">結帳</RouterLink>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            <font-awesome-icon icon="fa-solid fa-user" />
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li>
                                <RouterLink class="dropdown-item" to="#"
                                    @click="checkAuth('/members/basicinformation')">會員基本資料</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/buyer/buyerorder')">買家訂單
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
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/pages/productUploadPage')">
                                    上架商品</RouterLink>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- </div> -->
</template>

<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'
import NotificationPop from '@/components/NotificationPop.vue';
import axiosapi from '@/plugins/axios';

const router = useRouter();

// 登入相關
const user = inject('user');
const setUser = inject('setUser');
function logout() {
    axiosapi.defaults.headers.authorization = "";
    sessionStorage.removeItem("memberID");
    sessionStorage.removeItem("token");
    setUser(false);
    router.push("/secure/login");
}

const popupVisible = ref(false);

const showPopup = () => {
    console.log("show");
    popupVisible.value = true;
};

const hidePopup = () => {
    console.log("hide");
    popupVisible.value = false;
};

const checkAuth = (path) => {
    if (!user.value) {
        router.push('/secure/login');
    } else {
        router.push(path);
    }
}
</script>

<style scoped>
.navbar {
    position: fixed;
    /* 固定在視窗中的位置 */
    top: 0;
    /* 距離視窗頂部 0 像素 */
    left: 50px;
    /* 距離視窗左邊 0 像素 */
    width: 92.85%;
    /* 設定寬度跟category一樣*/
    background-color: #FCFCFC;
    /* 背景顏色 */
    font-size: 1.5rem;
    /* 字體大小 */
    box-sizing: border-box;
    /* 確保 padding 不會超出容器 */
    z-index: 1000;
    /* 確保在其他內容之上 */
    /* padding: 5px 15px; */
}

.logo {
    width: 75px;
    height: auto;
}

.nav-item {
    margin: 0 5px;
    text-decoration: none;
    /* color: #fff; */
}

.nav-link {
    font-size: 1.5rem;
}

.search-form {
    width: 30%;
    /* 調整搜尋框寬度 */
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

.dropdown:hover>.dropdown-menu {
    display: block;
}

.dropdown>.dropdown-toggle:active {
    pointer-events: none;
}
</style>