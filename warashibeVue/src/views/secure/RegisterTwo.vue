<template>

    <section class="vh-100">
    <div class="container-fluid">
        <div class="row">
        <div class="col-sm-6 text-black">

            <div class="d-flex align-items-start h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

                <form style="width: 23rem;">

                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">註冊會員</h3>

                    <div class="form-floating mb-3">
                        <input v-model="username" type="text" class="form-control" id="floatingInput" placeholder="name">
                        <label for="floatingInput">請輸入姓名</label>
                    </div>

                    <div class="d-md-flex justify-content-start align-items-center mb-4 py-2">

                        <h6 class="mb-0 me-4">性別: </h6>

                        <div class="form-check form-check-inline mb-0 me-4">
                            <input v-model="gender" class="form-check-input" type="radio" name="inlineRadioOptions" id="femaleGender"
                                value="female" />
                            <label class="form-check-label" for="femaleGender">女</label>
                        </div>

                        <div class="form-check form-check-inline mb-0 me-4">
                            <input v-model="gender" class="form-check-input" type="radio" name="inlineRadioOptions" id="maleGender"
                                value="male" />
                            <label class="form-check-label" for="maleGender">男</label>
                        </div>

                        <div class="form-check form-check-inline mb-0">
                            <input v-model="gender" class="form-check-input" type="radio" name="inlineRadioOptions" id="otherGender"
                                value="other" />
                            <label class="form-check-label" for="otherGender">其他</label>
                        </div>
                    </div>

                    <div class="form-floating mb-3">
                        <input v-model="mobile" type="text" class="form-control" id="floatingMobile" placeholder="mobile">
                        <label for="floatingInput">請輸入手機號碼</label>
                    </div>

                    <div class="pt-3 mb-4">
                        <RouterLink @click="register" :to="{name: 'register-three-link'}" class="btn btn-info btn-lg btn-block" type="button">註冊</RouterLink>
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
                <img src="../../assets/images/barter.jpg"
                alt="Login image" class="w-100 vh-90" style="object-fit: cover; object-position: left;">
            </div>
        </div>
    </div>
    </section>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import axiosapi from '@/plugins/axios';
    import Swal from 'sweetalert2';

    const username = ref("");
    const gender = ref("");
    const mobile = ref("");
    const storeAcc = ref('');
    const storePwd = ref('')

    onMounted(() => {
        storeAcc.value = sessionStorage.getItem('account');
        storePwd.value = sessionStorage.getItem('password');
    });

    function register() {
        let request = {
            "account": storeAcc.value,
            "password": storePwd.value,
            "username": username.value,
            "gender": gender.value,
            "mobile": mobile.value,
            "intro": "",
            "status": "active",
        }



        axiosapi.post("/auth/insert", request).then(function(response) {
            if(response.data.success) {
                Swal.fire({
                    icon: "success",
                    text: response.data.message,
                })
            }else {
                Swal.fire({
                    icon: "warning",
                    text: response.data.message,
                });
            }
            
        }).catch(function (error){
            Swal.fire({
                icon: "error",
                text: "註冊失敗: " + error.message,
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
