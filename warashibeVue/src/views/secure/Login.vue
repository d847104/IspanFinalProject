<!-- src/views/Login.vue -->
<template>
    <div>
        <Navbar />
        <div class="login-form">
            <h2>登入會員</h2>
            <input type="text" placeholder="請輸入帳號">
            <input type="password" placeholder="請輸入密碼">
            <button>登入</button>
            <div>或</div>
            <button>使用Google帳號登入</button>
        </div>
    </div>
</template>

<script>
    import Navbar from '../Navigation.vue';
</script>

<style scoped>
    .login-form {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    text-align: center;
    }

    .login-form input {
    display: block;
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    }

    .login-form button {
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    }
</style>
