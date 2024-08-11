<template>
<div>
    <h1>与 {{ sellerName }} 的聊天</h1>
    <div class="chat-window">
    <div v-for="message in messages" :key="message.id" class="message">
        <p>{{ message.sender.username }}: {{ message.content }}</p>
    </div>
    </div>
    <input v-model="newMessage" @keyup.enter="sendMessageToServer" placeholder="输入消息..." />
    <button @click="sendMessageToServer">发送</button>
</div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import { useRoute } from 'vue-router';
import { connect, sendMessage } from '@/service/websocket';
import axiosapi from '@/plugins/axios';

const route = useRoute();
const sellerID = ref(route.query.sellerID);
const sellerName = ref('');

// 聊天内容的状态
const messages = ref([]);
const newMessage = ref('');

// 获取卖家信息
onMounted(async () => {
const { data } = await axiosapi.get(`/ajax/members/${sellerID.value}`);
if (data.list && data.list.length > 0) {
    sellerName.value = data.list[0].username;
}

// 初始化 WebSocket 连接
connect((message) => {
    messages.value.push(message);
});

// 获取聊天记录
const chatRoomID = await getChatRoomID();
fetchChatMessages(chatRoomID);
});

const getChatRoomID = async () => {
const response = await axiosapi.post('/api/chat/room', {
    participant1Id: sessionStorage.getItem('memberID'),
    participant2Id: sellerID.value
});
return response.data.id;
};

const fetchChatMessages = async (chatRoomID) => {
const response = await axiosapi.get(`/api/chat/room/${chatRoomID}/messages`);
messages.value = response.data;
};

const sendMessageToServer = async () => {
if (newMessage.value.trim()) {
    const chatRoomID = await getChatRoomID();
    sendMessage({
    chatRoomId: chatRoomID,
    senderId: sessionStorage.getItem("memberID"),
    receiverId: sellerID.value,
    message: newMessage.value
    });
    newMessage.value = '';
}
};
</script>

<style scoped>
/* 添加一些样式，使得聊天页面更美观 */
.chat-window {
border: 1px solid #ccc;
padding: 10px;
height: 400px;
overflow-y: scroll;
}

.message {
padding: 5px;
margin-bottom: 10px;
}

input {
width: 80%;
margin-right: 10px;
}

button {
width: 18%;
}
</style>
