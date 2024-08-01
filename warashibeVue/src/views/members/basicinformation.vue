<template>

    <br><br><br><br><br><br>
    <div v-if="selectedMember">
        <h3 style="color:#CCEEFF;">會員基本資料</h3>
        <a>HI~{{ memberID }}</a>
        <div class="row">
            <div class="col-md-8">
                <div class="alert alert-light" role="alert">
                    <h5>關於我</h5>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">會員ID</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.id" disabled>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.username" disabled>
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                                icon="fa-solid fa-pen-clip" /></button>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">帳號</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.account" disabled>
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                                icon="fa-solid fa-pen" /></button>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">手機</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.mobile" disabled>
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                                icon="fa-solid fa-pencil" /></button>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">Email</label>
                        <div class="col-sm-7">
                            <input type="email" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.account" disabled>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">性別</label>
                        <div class="col-sm-7">
                            <button type="button" class="btn btn-outline-secondary mx-2" disabled>男</button>
                            <button type="button" class="btn btn-outline-secondary mx-2" disabled>女</button>
                            <button type="button" class="btn btn-secondary mx-2" disabled>不願透露</button>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">註冊時間</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.createTime" disabled>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">最後登入</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.lastLogin" disabled>
                        </div>
                    </div>

                </div>
            </div>

            <div class="col-md-4 ">
                <div class="card" style="width: 18rem;">
                    <img src="/src/img/貓頭鷹.jpg" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">{{ selectedMember.username }}</h5>
                        <p class="card-text">帳號：{{ selectedMember.account }}</p>
                        <a href="#" class="btn btn-secondary float-end"><font-awesome-icon
                                icon="fa-solid fa-repeat" /></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';

const member = ref({});
const members = ref([]);
const memberID = ref(sessionStorage.getItem("memberID") || 1);
const selectedMember = ref(false);

onMounted(() => {
    findByMemberID(memberID.value);
});

function findByMemberID(memberID) {
    Swal.fire({
        text: "處理中.....",
        allowOutsideClick: false,
        showConfirmButton: false
    });

    axiosapi.get(`/ajax/members/${memberID}`).then(function (response) {
        console.log('Response data:', response.data.list[0].id);
        selectedMember.value = response.data.list[0];
        setTimeout(function () {
            Swal.close();
        }, 500);
    }).catch(function (error) {
        console.log("error", error);
        Swal.fire({
            text: "查詢失敗：" + error.message,
            icon: "error"
        });
    });
}


</script>

<style></style>