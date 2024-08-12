<template>
    <div class="container">
        <div class="row">
            <div class="col-sm-6 text-black">
                <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                    <form style="width: 23rem;">
                        <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">修改密碼</h3>
                        <div class="form-floating mb-3">
                            <input v-model="oldPassword" type="password" class="form-control" id="oldPassword"
                                placeholder="請輸入舊密碼">
                            <label for="oldPassword">請輸入舊密碼</label>
                        </div>
                        <div class="form-floating">
                            <input v-model="newPassword" type="password" class="form-control" id="floatingPassword"
                                placeholder="Password">
                            <label for="floatingPassword">請輸入新密碼</label>
                        </div>
                        <div class="form-floating">
                            <input v-model="checkNewPassword" type="password" class="form-control" id="checkNewPassword"
                                placeholder="Password">
                            <label for="checkNewPassword">請確認新密碼</label>
                            <span v-if="passwordsDoNotMatch" class="text-danger">密碼不一致</span>
                        </div>
                        <div class="pt-3 mb-4">
                            <button @click="resetPassword" class="btn btn-info btn-lg btn-block" type="button"
                                :disabled="passwordsDoNotMatch">確認重設密碼</button>
                        </div>
                        <div class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-end">
                            <RouterLink :to="{ name: 'register-one-link' }" class="link-secondary text-decoration-none">
                                建立新帳號</RouterLink>
                            <RouterLink :to="{ name: 'secure-forgetPwd-link' }"
                                class="link-secondary text-decoration-none">忘記密碼</RouterLink>
                        </div>
                        <hr class="my-4">
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
import { ref, inject, watch } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRouter } from 'vue-router';
import Swal from 'sweetalert2';

const oldPassword = ref('');
const newPassword = ref('');
const checkNewPassword = ref('');
const account = ref(''); // 這裡假設你已經有了 account 資料
const router = useRouter();
const isLogin = inject("isLogin");
const passwordsDoNotMatch = ref(false);


watch([newPassword, checkNewPassword], () => {
    passwordsDoNotMatch.value = newPassword.value !== checkNewPassword.value;
});

const resetPassword = async () => {

    if (passwordsDoNotMatch.value) {
        Swal.fire({
            icon: "error",
            text: "新密碼與確認密碼不一致",
        });
        return;
    }
    if (isLogin.value && sessionStorage.getItem("memberID") != null) {
        let request = {
            "account": sessionStorage.getItem("account"),
            "oldPassword": oldPassword.value,
            "newPassword": newPassword.value,
        };
        try {
            await axiosapi.post('/auth/modifyPwd', request);
            Swal.fire({
                icon: "success",
                text: "密碼重設成功，請重新登入",
            });
            router.push('/secure/login');
        } catch (error) {
            Swal.fire({
                icon: "error",
                text: "重設失敗，請確認密碼是否正確",
            });
        }
    }


};
</script>

<style scoped>
.h-custom-2 {
    height: 100%;
}
</style>
