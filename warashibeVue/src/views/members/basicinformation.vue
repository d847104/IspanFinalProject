<template>

    <br><br><br><br><br><br>
    <div v-if="selectedMember" class="container">
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
                            <input type="text" class="form-control" v-model="selectedMember.username"
                                :disabled="!isEditing.username">
                        </div>
                        <button type="button" class="col-sm-1 btn"
                            :class="isEditing.username ? 'btn-outline-secondary' : 'btn-secondary'"
                            @click="toggleEdit('username')">
                            <font-awesome-icon icon="fa-solid fa-pen-clip" />
                        </button>
                    </div>

                    <!-- <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.username" disabled>
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                                icon="fa-solid fa-pen-clip" /></button>
                    </div> -->

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

                    <!-- gender判斷STAR -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">性別</label>
                        <div class="col-sm-7">
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': selectedMember.gender === 'Male',
                                'btn-outline-secondary': selectedMember.gender !== 'Male'
                            }" disabled>男</button>
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': selectedMember.gender === 'Female',
                                'btn-outline-secondary': selectedMember.gender !== 'Female'
                            }" disabled>女</button>
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': !selectedMember.gender || selectedMember.gender === '',
                                'btn-outline-secondary': selectedMember.gender && selectedMember.gender !== ''
                            }" disabled>不願透露</button>
                        </div>
                    </div>
                    <!-- gender判斷END -->

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
                    <button type="button" class="btn btn-light" @click="cancelEdit">取消</button>
                    <button type="button" class="btn btn-light" @click="saveChanges">儲存</button>
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
const selectedMember = ref(null);
const isEditing = ref({ username: false, account: false, mobile: false }); // 編輯狀態
const originalMemberData = ref({});


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
        console.log('Response data:', response.data.list[0]);
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

function toggleEdit(field) {
    isEditing.value[field] = !isEditing.value[field]; // 切換編輯狀態
}

function cancelEdit() {
    // 取消編輯，恢復原始數據
    memberData.value = { ...originalMemberData.value };
    isEditing.value = { username: false, account: false, mobile: false };
}

function saveChanges() {
    Swal.fire({
        text: "執行中",
        allowOutsideClick: false,
        showConfirmButton: false,
    });

    axiosapi.put(`/ajax/members/update/${selectedMember.value.id}`, selectedMember.value).then(response => {
        console.log("response", response);
        if (response.data.success) {
            Swal.fire({
                icon: "success",
                text: response.data.message,
            }).then(() => {
                modal.value.closeModal();
                findall();
            });
        } else {
            Swal.fire({
                icon: "warning",
                text: response.data.message,
            });
        }
    }).catch(function (error) {
        console.log("error", error);
        Swal.fire({
            icon: "error",
            text: "修改錯誤：" + error.message,
        });
    });
}


</script>

<style scoped></style>