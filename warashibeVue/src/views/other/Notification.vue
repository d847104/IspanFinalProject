<template>
<div class="container">
    <div class="notification-page">
        <h1 class="notification-title">通知訊息</h1>
        <div class="notification-list">
        <div class="notification-item" v-for="notification in notifications" :key="notification.notificationID">
            <div class="notification-info">
                <img :src="notification.senderProfileImg || '/default-avatar.png'" alt="用戶頭像" class="notification-image" @click="navigateToSellerPage(notification.senderID)"/>
                <div class="notification-details">
                    <p>
                        <!-- <span class="sender">{{ notification.senderName }}</span> -->
                        {{ notification.content }}
                        <span class="action">{{ notification.action }}</span>
                    </p>
                </div>
            </div>
            <div class="notification-action">
                <button class="action-button" @click="gotoManageProduct">前往商品管理</button>
            </div>
        </div>
    </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axiosapi from '@/plugins/axios';
import router from '@/router/router';

const notifications = ref([]);

const fetchSenderInfo = async (senderID) => {
    try {
        const response = await axiosapi.get(`/ajax/members/${senderID}`);
        const member = response.data.list[0];
        return {
            username: member.username,
            profileImg: member.profileImg ? `data:image/png;base64,${member.profileImg}` : '/default-avatar.png'
        };
    } catch (error) {
        console.error(`Error fetching member info for ID ${senderID}:`, error);
        return { username: 'Unknown', profileImg: '/default-avatar.png' };
    }
};

const fetchNotifications = async () => {
    try {
        const response = await axiosapi.get(`/ajax/notification/receiver/${sessionStorage.getItem("memberID")}`);
        const notificationList = response.data.list || [];

        for (const notification of notificationList) {
            const senderInfo = await fetchSenderInfo(notification.senderID);
            notification.senderName = senderInfo.username;
            notification.senderProfileImg = senderInfo.profileImg;
        }

        notifications.value = notificationList;
    } catch (error) {
        console.error('Error fetching notifications:', error);
    }
};

const gotoManageProduct = ()=>{
    router.push("/seller/sellermanageproduct");
}

const navigateToSellerPage = (senderID) => {
    router.push(`/pages/productSellerPage?sellerID=${senderID}`);
};

onMounted(fetchNotifications);
</script>

<style scoped>
.notification-page {
    padding: 0; /* 去掉 padding */
    background-color: transparent;
}

.notification-title {
    margin: 0;
    padding: 10px;
    color: aliceblue;
}

.notification-list {
    margin-top: 10px; /* 调整为你所需要的间距 */
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
