<template>

    <br><br><br><br><br><br>
    <h3 style="color:#CCEEFF;">會員基本資料</h3>
    <div class="row">
        <div class="col-md-8">
            <div class="alert alert-light" role="alert">
                <h5>關於我</h5>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">會員ID</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" v-model="members.memberID"
                            disabled>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">姓名</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" v-model="members.account"
                            disabled>
                    </div>
                    <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                            icon="fa-solid fa-pen-clip" /></button>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">帳號</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" disabled>{{ members.account
                        }}
                    </div>
                    <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                            icon="fa-solid fa-pen" /></button>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">手機</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" :value="members.mobile"
                            disabled>
                    </div>
                    <button type="button" class="col-sm-1 btn btn-outline-secondary"><font-awesome-icon
                            icon="fa-solid fa-pencil" /></button>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">Email</label>
                    <div class="col-sm-7">
                        <input type="email" class="form-control" id="floatingInputDisabled" placeholder="???" disabled>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">性別</label>
                    <div class="col-sm-7">
                        <button type="button" class="btn btn-outline-secondary mx-2"
                            :class="{ active: members.gender === '男' }" disabled>男</button>
                        <button type="button" class="btn btn-outline-secondary mx-2"
                            :class="{ active: members.gender === '女' }" disabled>女</button>
                        <button type="button" class="btn btn-secondary mx-2"
                            :class="{ active: members.gender === '不願透露' }" disabled>不願透露</button>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">註冊時間</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" :value="members.createTime"
                            disabled>
                    </div>
                </div>

                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label text-end">最後登入</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="floatingInputDisabled" :value="members.lastLogin"
                            disabled>
                    </div>
                </div>

            </div>
        </div>

        <div class="col-md-4 ">
            <div class="card" style="width: 18rem;">
                <img src="/src/img/貓頭鷹.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title" :value="members.username">YourName</h5>
                    <p class="card-text" :value="members.account">帳號：</p>
                    <a href="#" class="btn btn-secondary float-end"><font-awesome-icon icon="fa-solid fa-repeat" /></a>
                </div>
            </div>
        </div>
    </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';

const member = ref([]);
const members = ref({})
const isShowInsertButton = ref(false);

// 函數：獲取會員資料
function findMemberData() {
    Swal.fire({
        text: "處理中...",
        allowOutsideClick: false,
        showConfirmButton: false
    });

    const memberID = sessionStorage.getItem("memberID");
    // const token = sessionStorage.getItem("authToken");
    //↑抓登入token及memberid
    if (!memberID) {
        Swal.fire({
            text: "無此會員ID",
            icon: "error"
        });
        return;
    }
    // if (token) {
    //     axiosapi.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    // }
    // //↑儲存登入token
    axiosapi.get(`/ajax/members/${memberID}`) // 根據實際 API 路徑進行修改
        .then(response => {
            members.value = response.data; // 設置為 API 返回的會員數據
            Swal.close();
        })
        .catch(error => {
            console.error("Error fetching member data:", error);
            Swal.fire({
                text: "查詢失敗：" + error.message,
                icon: "error"
            });
        });
}

// 當組件加載時獲取資料
onMounted(() => {
    findMemberData();
});



</script>

<style></style>