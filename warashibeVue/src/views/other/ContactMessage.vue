<template>
    <div class="contact-us">
        <h2 class="text-center">聯繫客服</h2>
        <form @submit.prevent="submitContactForm">
            <div class="mb-3">
                <label for="name" class="form-label">姓名</label>
                <input type="text" class="form-control" v-model="name" id="name" placeholder="請輸入您的姓名" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">電子郵件</label>
                <input type="email" class="form-control" v-model="email" id="email" placeholder="請輸入您的電子郵件" required>
            </div>
            <div class="mb-3">
                <label for="questionType" class="form-label">問題類型</label>
                <select class="form-select" v-model="questionType" id="questionType" required>
                    <option value="" disabled selected>請選擇問題類型</option>
                    <option value="order">訂單問題</option>
                    <option value="product">產品問題</option>
                    <option value="account">帳戶問題</option>
                    <option value="other">其他</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="message" class="form-label">消息內容</label>
                <textarea class="form-control" v-model="message" id="message" rows="5" placeholder="請輸入您的消息內容" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axiosapi from '@/plugins/axios';
import Swal from 'sweetalert2';

const name = ref('');
const email = ref('');
const questionType = ref('');
const message = ref('');

async function submitContactForm() {
    const contactMessage = {
        name: name.value,
        email: email.value,
        questionType: questionType.value,
        message: message.value,
        submitTime: new Date().toISOString() // 提交時間
    };

    try {
        await axiosapi.post('/api/contact/submit', contactMessage);
        Swal.fire({
            title: '提交成功',
            text: '我們已收到您的消息，客服人員將盡快與您聯繫。',
            icon: 'success',
            confirmButtonText: '好的'
        });

        // 清空表單
        name.value = '';
        email.value = '';
        questionType.value = '';
        message.value = '';
    } catch (error) {
        Swal.fire({
            title: '提交失敗',
            text: '請稍後再試。',
            icon: 'error',
            confirmButtonText: '好的'
        });
    }
}
</script>

<style scoped>
.contact-us {
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.text-center {
    text-align: center;
    margin-bottom: 20px;
}

.form-label {
    font-weight: bold;
}

.btn {
    display: block;
    width: 100%;
}
</style>
