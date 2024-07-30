<template>

    <section class="vh-100">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-6 text-black">

                    <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                        <form style="width: 23rem;">

                            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">登入</h3>

                            <div class="form-floating mb-3">
                                <input v-model="account" type="email" class="form-control" id="floatingInput"
                                    placeholder="name@example.com">
                                <label for="floatingInput">電子郵件帳號</label>
                            </div>
                            <div class="form-floating">
                                <input v-model="password" type="password" class="form-control" id="floatingPassword"
                                    placeholder="Password">
                                <label for="floatingPassword">密碼</label>
                            </div>


                            <div class="pt-3 mb-4">
                                <button @click="login" data-mdb-button-init data-mdb-ripple-init
                                    class="btn btn-info btn-lg btn-block" type="button">登入</button>
                            </div>

                            <div class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-end">
                                <RouterLink :to="{ name: 'register-one-link' }"
                                    class="link-secondary text-decoration-none">建立新帳號</RouterLink>
                                <a href="#!" class="link-secondary text-decoration-none">忘記密碼</a>
                            </div>

                            <hr class="my-4">

                            <a href="#!" class="btn bsb-btn-xl btn-outline-primary">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                    class="bi bi-google" viewBox="0 0 16 16">
                                    <path
                                        d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                                </svg>
                                <span class="ms-2 fs-6">使用Google帳號登入</span>
                            </a>

                        </form>
                    </div>
                </div>
                <div class="col-sm-6 px-0 d-none d-sm-block">
                    <img src="../../assets/images/barter.jpg" alt="Login image" class="w-100 vh-90"
                        style="object-fit: cover; object-position: left;">
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const account = ref("");
const password = ref("");
const router = useRouter();

function login() {
    axiosapi.defaults.headers.authorization = "";
    sessionStorage.removeItem("memberID");

    if (account.value == "") {
        account.value == null;
    }

    if (password.value == "") {
        password.value == null;
    }

    let request = {
        "account": account.value,
        "password": password.value,
    }

    axiosapi.post("/auth/login", request).then(function (response) {
        if (response.data.success) {
            swal.fire({
                icon: "success",
                text: response.data.message,
            }).then(function (result) {
                axiosapi.defaults.headers.authorization = `Bearer ${response.data.token}`;
                sessionStorage.setItem("memberID", response.data.memberID);
                router.push("/");
                console.log("登入成功");
            });
        } else {
            swal.fire({
                icon: "warning",
                text: response.data.message,
            })
        }
    }).catch(function (error) {
        console.log("error", error);
        swal.fire({
            icon: "error",
            text: "登入失敗: " + error.message,
        });
    });

}



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
