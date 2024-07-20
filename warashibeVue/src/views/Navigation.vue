<template>
    <nav>
        <div class="navbar">
        <div class="navbar-left">
            <a href="#" class="logo"><img src="" alt="Logo"></a>
            <a href="#" class="nav-item">商城/二手</a>
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