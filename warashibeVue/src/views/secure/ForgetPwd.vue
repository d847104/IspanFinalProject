<template>
    <div class="container">
        <div class="row">
            <div class="col-sm-6 text-black">

                <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                    <form style="width: 23rem;">

                        <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">忘記密碼</h3>

                        <div class="form-floating mb-3">
                            <input v-model="account" type="email" class="form-control" id="floatingInput"
                                placeholder="請輸入您的帳號 (Email)">
                            <label for="floatingInput">電子郵件帳號</label>
                        </div>

                        <div class="pt-3 mb-4">
                            <button @click="requestPasswordReset" data-mdb-button-init data-mdb-ripple-init
                                class="btn btn-info btn-lg btn-block" type="button">發送驗證碼</button>
                        </div>

                        <div class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-end">
                            <RouterLink :to="{ name: 'register-one-link' }" class="link-secondary text-decoration-none">建立新帳號</RouterLink>
                            <RouterLink :to="{ name: 'secure-forgetPwd-link' }" class="link-secondary text-decoration-none">忘記密碼</RouterLink>
                        </div>

                        <hr class="my-4">

                        <!-- <a href="#!" class="btn bsb-btn-xl btn-outline-primary">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-google" viewBox="0 0 16 16">
                                <path
                                    d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                            </svg>
                            <span class="ms-2 fs-6">使用Google帳號登入</span>
                        </a> -->

                    </form>
                </div>
            </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="../../assets/images/barter.jpg" alt="Login image" class="w-100"
                    style="object-fit: cover; object-position: left;">
            </div>
        </div>
    </div>


</template>

<script setup>
import { ref } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';


const account = ref('');
const router = useRouter();

const requestPasswordReset = async () => {
    let request = {
        "account" : account.value,
    }
    try {
        await axiosapi.post('/auth/forgot-password', request);
        Swal.fire({
                icon: "success",
                text: "驗證碼已發送至您的Email",
            })
        router.push({query: {account: account.value}});

    } catch (error) {
        console.error(error);
        alert('發送失敗，請確認Email是否正確');
    }
};
</script>