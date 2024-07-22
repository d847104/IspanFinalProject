<template>

    <div class="">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <img src="/src/img/ss.png" href="#">
                <a class="navbar-brand" href="#">物換心儀</a>
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
                        <li class="nav-item dropdown">
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
    <br><br>

    <nav class="nav flex-column w-25 p-3">
        <a class="nav-link" href="#">服飾</a>
        <a class="nav-link" href="#">圖書</a>
        <a class="nav-link" href="#">3C用品</a>
        <a class="nav-link" href="#">生活用品</a>
        <a class="nav-link" href="#">家電用品</a>
        <a class="nav-link" href="#">運動用品</a>
        <a class="nav-link" href="#">寵物用品</a>
        <a class="nav-link active" aria-current="page" href="#">嬰幼兒用品</a>
        <!-- <a class="nav-link disabled" aria-disabled="true">Disabled</a> -->
    </nav>
    <div class="container">
        <div class="text-left">
            <h4>熱門商品</h4>
        </div>
        <div class="card d-inline-flex p-2 mb-3 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯8807.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">【馬克杯】</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>

        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯21569.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯36344.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯8807.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯21569.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
    </div>
    <br>
    <div class="container">
        <div class="text-left">
            <h4>推薦給您</h4>
        </div>
        <div class="card d-inline-flex p-2 mb-3 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯84089.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>

        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯88795.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
        <div class="card d-inline-flex p-2 mx-2" style="max-width: 225px;">
            <img src="/src/img/馬克杯27117.jpg" class="card-img-top" alt="...">

            <div class="card-body">
                <h5 class="card-title">馬克杯</h5>
                <p class="card-text">「單色馬克杯」『單色馬克杯』【單色馬克杯】</p>
                <a href="#" class="btn btn-secondary btn-sm mx-1">商品介紹</a>
                <a href="#" class="btn btn-secondary btn-sm ">加入購物車</a>
            </div>
        </div>
    </div>
</template>

<script setup>
//     .card{
//     margin-right:20px;
// }

</script>

<style></style>