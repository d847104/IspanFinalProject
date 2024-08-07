<template>
        
</template>
    
<script setup>
    
</script>
    
<style>
    
</style>
<!-- <template>
<div class="chat-container">
    <div class="chat-sidebar">
    <h2>好友列表</h2>
    <ul>
        <li v-for="friend in friends" :key="friend.id" @click="selectFriend(friend)">
        <img :src="friend.avatar" alt="Avatar" class="avatar">
        <span>{{ friend.name }}</span>
        </li>
    </ul>
    </div>
    <div class="chat-main">
    <div class="chat-header">
        <h2>{{ selectedFriend ? selectedFriend.name : '請選擇一個好友開始聊天' }}</h2>
    </div>
    <div class="chat-messages">
        <div v-for="message in messages" :key="message.msgID" :class="{'message-sent': message.senderID === user.id, 'message-received': message.senderID !== user.id}">
        <div class="message-content">{{ message.msg }}</div>
        <div class="message-time">{{ formatDate(message.msgTime) }}</div>
        </div>
    </div>
    <div class="chat-input">
        <input type="text" v-model="newMessage" @keydown.enter="sendMessage">
        <button @click="sendMessage">發送</button>
    </div>
    </div>
</div>
<div class="chat-input">
    <input type="text" v-model="newMessage" @keydown.enter="sendMessage">
    <emoji-picker @emoji-click="addEmoji" />
    <button @click="sendMessage">發送</button>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axiosapi from '@/plugins/axios';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { EmojiPicker } from 'emoji-mart-vue3';

const user = ref({ id: 1, name: 'CurrentUser' }); // 假設當前用戶
const friends = ref([
{ id: 2, name: 'Jason', avatar: 'path/to/avatar1.png' },
{ id: 3, name: 'Jack', avatar: 'path/to/avatar2.png' },
{ id: 4, name: 'Tom', avatar: 'path/to/avatar3.png' }
]);
const selectedFriend = ref(null);
const messages = ref([]);
const newMessage = ref('');
let stompClient = null;

const selectFriend = (friend) => {
selectedFriend.value = friend;
loadMessages(friend.id);
};

const loadMessages = async (friendId) => {
const response = await axiosapi.post('/messenger/find', {
senderID: user.value.id,
receiverID: friendId
});
messages.value = response.data.list;
};

const connect = () => {
const socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, () => {
stompClient.subscribe('/topic/messages', (message) => {
    const receivedMessage = JSON.parse(message.body);
    if (
    (receivedMessage.senderID === user.value.id && receivedMessage.receiverID === selectedFriend.value.id) ||
    (receivedMessage.senderID === selectedFriend.value.id && receivedMessage.receiverID === user.value.id)
    ) {
    messages.value.push(receivedMessage);
    }
});
});
};

const sendMessage = () => {
if (newMessage.value.trim() === '') return;

const message = {
senderID: user.value.id,
receiverID: selectedFriend.value.id,
msg: newMessage.value,
msgTime: new Date().toISOString()
};

stompClient.send('/app/chat', {}, JSON.stringify(message));
newMessage.value = '';
};

const addEmoji = (emoji) => {
newMessage.value += emoji.native;
};

const formatDate = (dateString) => {
const date = new Date(dateString);
return `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
};

onMounted(() => {
connect();
});
</script>

<style scoped>
.chat-container {
display: flex;
height: 100vh;
}

.chat-sidebar {
width: 20%;
background: #f0f0f0;
padding: 1rem;
}

.chat-sidebar h2 {
margin-bottom: 1rem;
}

.chat-sidebar ul {
list-style: none;
padding: 0;
}

.chat-sidebar li {
display: flex;
align-items: center;
padding: 0.5rem;
cursor: pointer;
}

.chat-sidebar li:hover {
background: #e0e0e0;
}

.chat-sidebar .avatar {
width: 40px;
height: 40px;
border-radius: 50%;
margin-right: 1rem;
}

.chat-main {
width: 80%;
display: flex;
flex-direction: column;
}

.chat-header {
padding: 1rem;
background: #f0f0f0;
border-bottom: 1px solid #ccc;
}

.chat-messages {
flex: 1;
padding: 1rem;
overflow-y: auto;
}

.message-sent {
text-align: right;
}

.message-received {
text-align: left;
}

.message-content {
display: inline-block;
padding: 0.5rem 1rem;
border-radius: 1rem;
margin: 0.5rem 0;
}

.message-sent .message-content {
background: #d1ffd6;
}

.message-received .message-content {
background: #ffffff;
}

.chat-input {
display: flex;
padding: 1rem;
border-top: 1px solid #ccc;
}

.chat-input input {
flex: 1;
padding: 0.5rem;
border: 1px solid #ccc;
border-radius: 1rem;
margin-right: 1rem;
}

.chat-input button {
padding: 0.5rem 1rem;
border: none;
background: #007bff;
color: white;
border-radius: 1rem;
cursor: pointer;
}
</style> -->
