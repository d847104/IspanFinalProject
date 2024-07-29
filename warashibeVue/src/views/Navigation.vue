<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <!-- 網站LOGO -->
            <RouterLink class="navbar-brand" to="/">
                <img src="@/assets/logo.png" alt="Logo" class="logo navbar-brand">
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
                        <button class="btn btn-outline-success search-button" type="submit">
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
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            會員相關
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li>
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/profile')">會員基本資料
                                </RouterLink>
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
                                <RouterLink class="dropdown-item" to="#" @click="checkAuth('/add-product')">上架商品
                                </RouterLink>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div id="carouselExampleSlidesOnly" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active" data-bs-interval="2000">
                <img src="/src/img/馬克杯21569.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item" data-bs-interval="2000">
                <img src="/src/img/馬克杯36344.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item" data-bs-interval="2000">
                <img src="/src/img/馬克杯84089.jpg" class="d-block w-100" alt="...">
            </div>
        </div>
    </div>
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
    /* 背景顏色透明度設為 0，表示完全透明 */
    border-bottom: 1px solid rgba(231, 231, 231, 0.5);
    /* 設置邊框顏色的透明度 */
    font-size: 1.5rem;
    z-index: 1000;
    /* 確保 navbar 顯示在輪播上方 */
    line-height: calc(50px * 0.8);
    /* 調整行高以保持內容居中 */
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

.carousel {
    width: 80vw;
    /* 設定輪播寬度為螢幕寬度的80% */
    height: 50vh;
    /* 設定輪播高度為螢幕高度的50% */
    margin: auto;
    /* 使輪播居中 */
    overflow: hidden;
    /* 隱藏溢出內容 */
}

.carousel-inner {
    height: 100%;
    /* 使輪播內部容器高度與輪播相同 */
    align-items: center;
    justify-content: center;

}

.carousel-item {

    /* 使用 flexbox 使圖片在輪播項目中居中 */
    height: 100%;
}


.carousel-item img {
    max-width: 100%;
    max-height: 100%;
    /* 確保圖片在容器內最大化，但不超過容器的大小 */
    object-fit: cover;
    /* 保持圖片比例，並填充容器 */
    display: block;
    /* 確保圖片是塊級元素 */
}
</style>
