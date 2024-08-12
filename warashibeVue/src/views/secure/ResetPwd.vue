<template>

    <div class="container">
        <div class="row">
            <div class="col-sm-6 text-black">

                <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                    <form style="width: 23rem;">

                        <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">重設密碼</h3>

                        <div class="form-floating mb-3">
                            <input v-model="code" type="text" class="form-control" id="floatingInput"
                                placeholder="請輸入驗證碼">
                            <label for="floatingInput">請輸入驗證碼</label>
                        </div>
                        <div class="form-floating">
                            <input v-model="newPassword" type="password" class="form-control" id="floatingPassword"
                                placeholder="Password">
                            <label for="floatingPassword">請輸入新密碼</label>
                        </div>


                        <div class="pt-3 mb-4">
                            <button @click="resetPassword" data-mdb-button-init data-mdb-ripple-init
                                class="btn btn-info btn-lg btn-block" type="button">確認重設密碼</button>
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
import { ref, onMounted,  onUnmounted} from 'vue';
import axiosapi from '@/plugins/axios';
import { useRoute, useRouter } from 'vue-router';
import Swal from 'sweetalert2';
const verificationLink = ref('');
const account = ref('');
const code = ref('')
const newPassword = ref('');
const route = useRoute();
const router = useRouter();

onMounted(() => {
    // 從 URL 參數中獲取帳號和驗證碼
    account.value = route.query.account || '';
});

const resetPassword = async () => {
    let request = {
        "account": account.value,
        "code": code.value,
        "newPassword": newPassword.value,
    }
    try {
        await axiosapi.post('/auth/reset-password', request);
        Swal.fire({
                icon: "success",
                text: "密碼重設成功，請重新登入",
            })
        router.push('/secure/login');
    } catch (error) {
        alert('重設失敗，請確認驗證碼是否正確');
    }
};

// 如果驗證碼存在於URL參數中，自動填入
verificationLink.value = route.query.verificationLink || '';
</script>