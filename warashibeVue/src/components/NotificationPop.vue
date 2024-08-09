<template>
<div @mouseover="showPopup" @mouseleave="hidePopup" class="notification-bell">
    <font-awesome-icon icon="fa-solid fa-bell" />
    <div v-if="popupVisible" class="notification-popup">
        <div class="notification-header">最近收到的通知</div>
        <div v-if="!isLogin" class="login-message alert alert-primary" role="alert">
            <p>請登入以查看通知</p>
            <button @click="goToLogin">登入查看全部</button>
        </div>
        <div v-else>
            <div v-if="notifications.length === 0">
                <p>沒有通知</p>
            </div>
            <div v-else>
                <ul>
                    <li v-for="notification in notifications" :key="notification.notificationID"
                        :class="{'unread': !notification.isRead}" @click="handleNotificationClick(notification)">
                        <p>
                            <span class="sender">來自會員{{ notification.senderID }}：</span>
                            <span>{{ notification.content }}</span>
                        </p>
                    </li>
                </ul>
                <button @click="viewAllNotifications">查看全部</button>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import { ref, watch, inject } from 'vue';
import axiosapi from '@/plugins/axios';
import { useRouter } from 'vue-router';

const popupVisible = ref(false);
const notifications = ref([]);
const isLogin = inject('isLogin');
const router = useRouter();

const fetchNotifications = async () => {
    try {
        const response = await axiosapi.get(`/ajax/notification/receiver/${sessionStorage.getItem("memberID")}`);
        notifications.value = response.data.list || [];
    } catch (error) {
        console.error('Error fetching notifications:', error);
    }
};

const showPopup = () => {
    popupVisible.value = true;
};

const hidePopup = () => {
    popupVisible.value = false;
};

const goToLogin = () => {
    router.push('/secure/login');
};

const viewAllNotifications = () => {
    router.push('/other/notification');
};

const handleNotificationClick = async (notification) => {
    try {
        await axiosapi.put(`/ajax/notification/update/${notification.notificationID}`, {
            id: notification.notificationID,
            isRead: true
        });
        notification.isRead = true;
        router.push('/other/notification');
    } catch (error) {
        console.error('Error updating notification:', error);
    }
};

watch(popupVisible, (newValue) => {
    if (newValue && isLogin.value) {
        fetchNotifications();
    }
});
</script>

<style scoped>
.notification-bell {
    position: relative;
    display: inline-block;
}

.notification-popup {
    position: absolute;
    top: 75%;
    right: 0;
    width: 400px;
    background-color: white;
    border: 1px solid #ddd;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    margin-top: 5px;
}

.notification-header {
    font-size: 16px;
    color: #6c757d;
    padding: 16px;
    border-bottom: 1px solid #ddd;
    background-color: #f8f9fa;
}

.notification-popup .login-message,
.notification-popup ul {
    padding: 16px;
    color: black;
}

.notification-popup ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.notification-popup li {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    color: black;
}

.notification-popup li.unread {
    background-color: #d5dbbf;
}

.notification-popup li:hover {
    background-color: #a9aaaa;
    cursor: pointer;
}

.notification-popup button {
    background-color: white;
    color: black;
    border: none;
    padding: 10px;
    cursor: pointer;
    width: 100%;
    text-align: center;
}

.notification-popup button:hover {
    background-color: #e9ecef;
}
</style>
