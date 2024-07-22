<!-- <template>
    <nav>
        <div class="navbar">
        <div class="navbar-left">
            <a href="#" class="logo"><img src="" alt="Logo"></a>
            <a href="#" class="nav-item">商城/二手</a>
            <RouterLink class="navbar-brand" to="/productList">productList</RouterLink> 
        </div>
        <div class="navbar-center">
            <input type="text" class="search-bar" placeholder="Search...">
            <button class="search-button"><i class="fa fa-search"></i></button>
        </div>
        <div class="navbar-right">
            <a href="#" class="nav-item">登入</a>
            <a href="#" class="nav-item">註冊</a>
            <a href="#" class="nav-item"><i class="fa fa-shopping-cart"></i></a>
            <a href="#" class="nav-item"><i class="fa fa-bars"></i></a>
        </div>
        </div>
    </nav>
</template> -->

<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="#!">Start Bootstrap</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item"><a class="nav-link active" aria-current="page" href="#!">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="#!">About</a></li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#!">All Products</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="#!">Popular Items</a></li>
                            <li><a class="dropdown-item" href="#!">New Arrivals</a></li>
                        </ul>
                    </li>
                </ul>
                <form class="d-flex">
                    <button class="btn btn-outline-dark" type="submit">
                        <i class="bi-cart-fill me-1"></i>
                        Cart
                        <span class="badge bg-dark text-white ms-1 rounded-pill">0</span>
                    </button>
                </form>
            </div>
        </div>
    </nav>
</template>
    
<script setup>

</script>
    
<style scoped>
    .navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background-color: #f8f8f8;
    border-bottom: 1px solid #e7e7e7;
    }

    .navbar-left, .navbar-right {
    display: flex;
    align-items: center;
    }

    .nav-item {
    margin: 0 10px;
    text-decoration: none;
    color: #333;
    }

    .search-bar {
    width: 200px;
    padding: 5px;
    }

    .search-button {
    padding: 5px 10px;
    }
</style>