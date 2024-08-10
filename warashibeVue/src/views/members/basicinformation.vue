<template>
    <br>
    <div v-if="selectedMember" class="container">
        <h3 style="color:#ECFFFF;">會員基本資料</h3>
        <div class="row">
            <div class="col-md-8">
                <div class="alert alert-light" role="alert">
                    <h5>關於我</h5>
                    <!-- <a>HI~{{ memberID }}</a> -->
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
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"
                            :class="isEditing.username ? 'btn-outline-secondary' : 'btn-outline-secondary'"
                            @click="toggleEdit('username')">
                            <font-awesome-icon icon="fa-solid fa-pencil" />
                        </button>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">帳號</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" v-model="selectedMember.account"
                                :disabled="!isEditing.account">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">手機</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" v-model="selectedMember.mobile"
                                :disabled="!isEditing.mobile">
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"
                            :class="isEditing.mobile ? 'btn-outline-secondary' : 'btn-outline-secondary'"
                            @click="toggleEdit('mobile')"><font-awesome-icon icon="fa-solid fa-pencil" /></button>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">Email</label>
                        <div class="col-sm-7">
                            <input type="email" class="form-control" id="floatingInputDisabled"
                                :value="selectedMember.account" disabled>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">商場/個人簡介</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" v-model="selectedMember.intro"
                                :disabled="!isEditing.intro">
                        </div>
                        <button type="button" class="col-sm-1 btn btn-outline-secondary"
                            :class="isEditing.intro ? 'btn-outline-secondary' : 'btn-outline-secondary'"
                            @click="toggleEdit('intro')"><font-awesome-icon icon="fa-solid fa-pencil" /></button>
                    </div>
                    <!-- gender判斷STAR -->
                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">性別</label>
                        <div class="col-sm-7">
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': selectedMember.gender === 'male',
                                'btn-outline-secondary': selectedMember.gender !== 'male'
                            }" disabled>男</button>
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': selectedMember.gender === 'female',
                                'btn-outline-secondary': selectedMember.gender !== 'female'
                            }" disabled>女</button>
                            <button type="button" class="btn mx-2" :class="{
                                'btn-secondary': !selectedMember.gender || selectedMember.gender === 'other',
                                'btn-outline-secondary': selectedMember.gender && selectedMember.gender !== 'other'
                            }" disabled>不願透露</button>
                        </div>
                    </div>
                    <!-- gender判斷END -->

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">註冊時間</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="formattedCreateTime" disabled>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label class="col-sm-2 col-form-label text-end">最後登入</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="floatingInputDisabled"
                                :value="formattedLastLogin" disabled>
                        </div>
                    </div>
                    <div class="d-flex justify-content-center">
                        <button type="button" class="btn btn-outline-secondary mx-2" @click="cancelEdit">取消</button>
                        <button type="button" class="btn btn-outline-secondary" @click="saveChanges">儲存</button>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card" style="width: 18rem;">
                    <img :src="getProfileImgUrl" class="card-img-top" alt="Profile Image">
                    <div class="card-body">
                        <h5 class="card-title">{{ selectedMember.username }}</h5>
                        <p class="card-text">帳號：{{ selectedMember.account }}</p>
                        <!-- <a href="#" class="btn btn-secondary float-end"><font-awesome-icon
                                icon="fa-solid fa-repeat" /></a> -->
                        <a href="#" class="btn btn-secondary float-end" @click="showFileInput = true">
                            <font-awesome-icon icon="fa-solid fa-repeat" />
                        </a>
                    </div>
                </div>
                <br>
                <!-- <div class="col-md-10">
                    <label for="formFile" class="form-label" style="color:#ECFFFF;">請選擇圖片</label>
                    <input class="form-control" type="file" id="formFile">
                </div> -->
                <div v-if="showFileInput" class="col-md-10">
                    <label for="formFile" class="form-label" style="color:#ECFFFF;">請選擇圖片</label>
                    <input class="form-control" type="file" id="formFile" @change="onFileChange">
                </div>
            </div>



        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import Swal from 'sweetalert2';
import axiosapi from '@/plugins/axios';
import { format } from 'date-fns';

const member = ref({});
const members = ref([]);
const memberID = ref(sessionStorage.getItem("memberID"));
const selectedMember = ref(null);
const isEditing = ref({ username: false, account: false, mobile: false }); // 編輯狀態
const originalMemberData = ref({});
const showFileInput = ref(false);

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
        selectedMember.value = response.data.list[0];
        originalMemberData.value = { ...response.data.list[0] };
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

// 計算屬性，動態獲取圖片 BASE64 URL
const getProfileImgUrl = computed(() => {
    return selectedMember.value && selectedMember.value.profileImg
        ? `data:image/png;base64,${selectedMember.value.profileImg}` // 假設 BASE64 編碼的圖片是 PNG 格式
        : '/src/img/海棉寶.png'; // 如果沒有圖片，顯示預設圖片
});

function toggleEdit(field) {
    isEditing.value[field] = !isEditing.value[field]; // 切換編輯狀態
}

function cancelEdit() {
    // 取消編輯，恢復原始數據
    selectedMember.value = { ...originalMemberData.value };
    isEditing.value = { username: false, account: false, mobile: false };
}

function saveChanges() {
    Swal.fire({
        text: "儲存中",
        allowOutsideClick: false,
        showConfirmButton: false
    });

    const formData = new FormData();

    // 確保文件已正確添加
    const fileInput = document.querySelector('input[type="file"]');
    if (fileInput && fileInput.files.length > 0) {
        formData.append('image', fileInput.files[0]);
    } else {
        // 如果沒有新的文件，將原始圖片轉換為 Blob
        if (selectedMember.value.profileImg) {
            const base64Image = selectedMember.value.profileImg;
            const blob = dataURLToBlob(`data:image/png;base64,${base64Image}`);
            formData.append('image', blob, 'profile-image.png');
        }
    }

    /// 將所有必要的欄位整合到一個 JSON 對象中
    const body = {
        id: selectedMember.value.id,
        username: selectedMember.value.username,
        mobile: selectedMember.value.mobile,
        intro: selectedMember.value.intro,
        lastLogin: new Date().toISOString() // 設定 lastLogin 為當前時間
    };

    // 添加 JSON 對象到 FormData 中
    formData.append('body', JSON.stringify(body));


    axiosapi.put(`/ajax/members/update/${selectedMember.value.id}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(response => {
        if (response.data.success) {
            Swal.fire({
                icon: "success",
                text: response.data.message
            }).then(() => {
                findByMemberID(memberID.value);
                cancelEdit();
            });
        } else {
            Swal.fire({
                icon: "warning",
                text: response.data.message
            });
        }
    }).catch(error => {
        console.error("error", error);
        Swal.fire({
            icon: "error",
            text: "修改錯誤：" + (error.response?.data?.message || error.message)
        });
    });
}

// Helper function to convert Base64 to Blob
function dataURLToBlob(dataURL) {
    const [header, data] = dataURL.split(',');
    const mime = header.split(':')[1].split(';')[0];
    const bytes = atob(data);
    const buffer = new ArrayBuffer(bytes.length);
    const uint8Array = new Uint8Array(buffer);
    for (let i = 0; i < bytes.length; i++) {
        uint8Array[i] = bytes.charCodeAt(i);
    }
    return new Blob([uint8Array], { type: mime });
}



const onFileChange = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('image', file); // 添加圖片文件
    formData.append('body', JSON.stringify({ id: selectedMember.value.id })); // 添加 body 參數

    try {
        const response = await axiosapi.put(`/ajax/members/update/${selectedMember.value.id}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        if (response.data.success) {
            const reader = new FileReader();
            reader.onloadend = () => {
                // 設定 Base64 編碼圖片，去掉前綴的 data:image/png;base64,
                selectedMember.value.profileImg = reader.result.split(',')[1];
                Swal.fire({
                    icon: 'success',
                    text: '修改成功'
                }).then(() => {
                    showFileInput.value = false;
                });
            };
            reader.readAsDataURL(file);
        } else {
            Swal.fire({
                icon: 'error',
                text: `修改失敗: ${response.data.message}`
            });
        }
    } catch (error) {
        // 詳細的錯誤處理
        if (error.response) {
            Swal.fire({
                icon: 'error',
                text: `上傳過程中發生錯誤: ${error.response.data.message || error.response.statusText}`
            });
        } else if (error.request) {
            Swal.fire({
                icon: 'error',
                text: '上傳過程中發生錯誤: 未收到服務器響應'
            });
        } else {
            Swal.fire({
                icon: 'error',
                text: `上傳過程中發生錯誤: ${error.message}`
            });
        }
    }
};

// 計算屬性，格式化註冊時間
const formattedCreateTime = computed(() => {
    return selectedMember.value && selectedMember.value.createTime
        ? format(new Date(selectedMember.value.createTime), 'yyyy-MM-dd HH:mm:ss')
        : '';
});

// 計算屬性，格式化最後登入時間
const formattedLastLogin = computed(() => {
    return selectedMember.value && selectedMember.value.lastLogin
        ? format(new Date(selectedMember.value.lastLogin), 'yyyy-MM-dd HH:mm:ss')
        : '';
});

</script>

<style scoped></style>