<template>
    <div class="">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <img src="/src/img/ss.png" href="#">
                <RouterLink class="navbar-brand" :to="{ name: 'home-link' }">物換心儀</RouterLink>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <!-- <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">首頁</a>
                        </li> -->

                        <form class="d-flex" role="search">
                            <input class="form-control me-2" type="search" placeholder="輸入搜尋條件" aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Search</button>
                        </form>
                        <!-- <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                aria-expanded="false">
                                商品分類
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li> -->
                        <li class="nav-item">
                            <RouterLink class="nav-link" :to="{ path: '/buyer/buyerorder' }">購物訂單管理</RouterLink>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">聊天室</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">通知</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">購物車</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">註冊</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">登入</a>
                        </li>
                        <!-- <li class="nav-item">
            <a class="nav-link disabled" aria-disabled="true">Disabled</a>
          </li> -->
                    </ul>

                </div>
            </div>
        </nav>



    </div>


</template>

<script setup>
import { RouterLink } from 'vue-router';



</script>

<style></style>