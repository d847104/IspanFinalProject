<template> 
        <nav class="navbar navbar-expand-lg fixed-top">
            <div class="container-fluid">                 
<template>

        <nav class="navbar navbar-expand-lg fixed-top">
            <div class="container-fluid">
        

                <img src="/src/img/ss.png" href="#">
                <RouterLink class="navbar-brand" :to="{ name: 'home' }" style="color:#3C3C3C;">物換心儀</RouterLink>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <RouterLink to="/change"><button type="button" class="btn btn-outline-light mx-2" >換物</button></RouterLink>
                    <form class="d-flex" role="search">
                            <input class="form-control me-2" type="search" placeholder="輸入搜尋條件" aria-label="Search">
                            <button class="btn btn-outline-secondary" type="submit" style="color:#3C3C3C;">
                                <font-awesome-icon icon="fa-solid fa-magnifying-glass" />
                            </button>

                    </form>
                        
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">


                        <li class="nav-item">
                            <a class="nav-link" style="color:#3C3C3C;" href="#">
                                <font-awesome-icon icon="fa-regular fa-comments" style="color:#3C3C3C;"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" style="color:#3C3C3C;" href="#">
                            <font-awesome-icon icon="fa-regular fa-bell" style="color:#3C3C3C;"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" style="color:#3C3C3C;" href="#">
                                <font-awesome-icon icon="fa-solid fa-cart-shopping" style="color:#3C3C3C;"/></a>
                        </li>
                        <li class="nav-item">                           
                            <a class="nav-link" style="color:#3C3C3C;" href="#">
                            <font-awesome-icon icon="fa-solid fa-user-plus" style="color:#3C3C3C;"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" style="color:#3C3C3C;" href="#">
                            <font-awesome-icon icon="fa-regular fa-user" style="color:#3C3C3C;"/></a>
                        </li>
                        <li class="nav-item dropdown">
                            <a style="color:#3C3C3C;" class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <font-awesome-icon icon="fa-regular fa-user" style="color:#3C3C3C;"/>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><RouterLink class="dropdown-item" :to="{ name: 'members-basicinformation-link' }">會員基本資料</RouterLink></li>
                                <li><RouterLink class="dropdown-item" :to="{ name: 'buyer-BuyerOrder-link' }">購物訂單管理</RouterLink></li>
                                <!-- <li><a class="dropdown-item" href="#">Something else here</a></li> -->
                            </ul>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>

</template>

<script setup>
import { RouterLink } from 'vue-router';
import "@/css/home.css"

</script>

<style>
    .navbar {
        width: 95%;
        background: transparent;
        position: fixed;
        top: 0;
        left: 25px;
        z-index: 1000; /* 固定navbar */
        
    }
</style>