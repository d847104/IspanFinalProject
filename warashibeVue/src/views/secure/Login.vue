<!-- src/views/Login.vue -->
<template>
    <section class="vh-100">
    <div class="container-fluid">
        <div class="row">
        <div class="col-sm-6 text-black">

            <div class="px-5 ms-xl-4">
            <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
            <span class="h1 fw-bold mb-0">Logo</span>
            </div>

            <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                <form style="width: 23rem;">

                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">登入</h3>

                    <div data-mdb-input-init class="form-outline mb-4">
                    <input type="email" id="form2Example18" class="form-control form-control-lg" />
                    <label class="form-label" for="form2Example18">電子郵件帳號</label>
                    </div>

                    <div data-mdb-input-init class="form-outline mb-4">
                    <input type="password" id="form2Example28" class="form-control form-control-lg" />
                    <label class="form-label" for="form2Example28">密碼</label>
                    </div>

                    <div class="pt-1 mb-4">
                    <button data-mdb-button-init data-mdb-ripple-init class="btn btn-info btn-lg btn-block" type="button">登入</button>
                    </div>

                    <div class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-end">
                        <a href="#!" class="link-secondary text-decoration-none">建立新帳號</a>
                        <a href="#!" class="link-secondary text-decoration-none">忘記密碼</a>
                    </div>

                    <hr class="my-4">

                    <a href="#!" class="btn bsb-btn-xl btn-outline-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
                        <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                        </svg>
                        <span class="ms-2 fs-6">使用Google帳號登入</span>
                    </a>




                </form>
            </div>

        </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img3.webp"
                alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
            </div>
        </div>
    </div>
    </section>
</template>

<script>
    // import Navbar from '../Navigation.vue';
</script>

<style scoped>
    .bg-image-vertical {
    position: relative;
    overflow: hidden;
    background-repeat: no-repeat;
    background-position: right center;
    background-size: auto 100%;
    }

    @media (min-width: 1025px) {
    .h-custom-2 {
    height: 100%;
    }
    }
</style>
