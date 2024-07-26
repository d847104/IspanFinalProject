<template>
<div class="notification-page">
    <h1>通知訊息</h1>
    <div class="notification-list">
    <div class="notification-item" v-for="notification in notifications" :key="notification.notificationID">
        <div class="notification-info">
        <img :src="notification.image" alt="用户头像" class="notification-image" />
        <div class="notification-details">
            <p>
            <span class="sender">{{ notification.senderName }}</span>
            {{ notification.content }}
            <span class="action">{{ notification.action }}</span>
            </p>
        </div>
        </div>
        <div class="notification-action">
        <button class="action-button">前往商品管理</button>
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref } from 'vue';

// 假数据，实际应用中从后端获取
const notifications = ref([
{
    notificationID: 1,
    senderName: '會員 Alan',
    content: '對您的商品感興趣，提出',
    action: '交換',
    image: '/src/img/user1.jfif',
    date: '2024-07-26',
},
{
    notificationID: 2,
    senderName: '會員 Tom',
    content: '對您的商品感興趣，提出',
    action: '購買',
    image: '/src/img/user2.jfif',
    date: '2024-07-26',
},
{
    notificationID: 3,
    senderName: '會員 Jack',
    content: '對您的商品感興趣，提出',
    action: '交換',
    image: '/src/img/user3.jfif',
    date: '2024-07-26',
},
]);
</script>

<style scoped>
.notification-page {
padding: 20px;
}

.notification-list {
margin-top: 20px;
}

.notification-item {
display: flex;
align-items: center;
justify-content: space-between;
padding: 10px;
border-bottom: 1px solid #ddd;
}

.notification-info {
display: flex;
align-items: center;
}

.notification-image {
width: 50px;
height: 50px;
margin-right: 10px;
}

.notification-details {
display: flex;
flex-direction: column;
}

.sender {
color: #e74c3c;
font-weight: bold;
}

.action {
color: #e74c3c;
}

.notification-action {
text-align: right;
}

.action-button {
color: #e74c3c;
background: none;
border: none;
cursor: pointer;
text-decoration: underline;
}
</style>
