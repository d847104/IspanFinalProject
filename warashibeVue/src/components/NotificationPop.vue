<template>
<div @mouseover="showPopup" @mouseleave="hidePopup" class="notification-bell">
    <font-awesome-icon icon="fa-solid fa-bell" />
    <div v-if="popupVisible" class="notification-popup">
    <div v-if="!isLoggedIn" class="login-message">
        <p>請登入以查看通知</p>
        <button @click="goToLogin">登入查看全部</button>
    </div>
    <div v-else>
        <div v-if="notifications.length === 12">
        <p>沒有通知</p>
        </div>
        <div v-else>
        <ul>
            <li v-for="notification in notifications" :key="notification.notificationID"
                :class="{'unread': !notification.isRead}"
                @mouseover="highlight(notification)" @mouseleave="removeHighlight(notification)">
            <p>
                <span class="sender">來自會員 {{ notification.senderID }}</span>
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
import { ref, watch, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const popupVisible = ref(false);
const notifications = ref([]);
const isLoggedIn = ref(true); // 假设用户已经登录
const router = useRouter();

const fetchNotifications = async () => {
try {
    const receiverID = 1; // 假设这是当前用户的 ID，需要替换为实际数据
    const response = await axios.get(`/ajax/notification/receiver/${receiverID}`);
    notifications.value = response.data.list || [];
    console.log(notifications.value);
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

const highlight = (notification) => {
// 设置高亮显示
};

const removeHighlight = (notification) => {
// 移除高亮显示
};

// 监听 popupVisible 的变化
watch(popupVisible, (newValue) => {
if (newValue) {
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
top: 100%;
right: 0;
width: 300px;
background-color: white;
border: 1px solid #ddd;
box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
z-index: 1000;
margin-top: 5px; /* 调整通知框和导航栏之间的间距 */
}

.notification-popup .login-message,
.notification-popup ul {
padding: 16px;
}

.notification-popup ul {
list-style: none;
padding: 0;
margin: 0;
}

.notification-popup li {
padding: 10px;
border-bottom: 1px solid #ddd;
}

.notification-popup li.unread {
background-color: #f8f9fa;
}

.notification-popup button {
background-color: #007bff;
color: white;
border: none;
padding: 10px;
cursor: pointer;
width: 100%;
text-align: center;
}

.notification-popup button:hover {
background-color: #0056b3;
}
</style>
