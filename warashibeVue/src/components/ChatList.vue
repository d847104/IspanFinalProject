<template>
<!-- <div>
    <h1>聊天列表</h1>
    <ul>
    <li v-for="chatRoom in chatRooms" :key="chatRoom.id" @click="goToChatRoom(chatRoom.id)">
        <p>{{ chatRoom.participant1.username }} 和 {{ chatRoom.participant2.username }}</p>
        <p>最后消息时间: {{ chatRoom.lastMessageTime }}</p>
    </li>
    </ul>
</div> -->
</template>

<script>
// import { connect, subscribeToTopic } from '@/service/websocket';
// import axiosapi from '@/plugins/axios';

// export default {
//     data() {
//         return {
//         chatRooms: []
//         };
//     },
//     methods: {
//         goToChatRoom(chatRoomId) {
//         this.$router.push(`/chatroom/${chatRoomId}`);
//         },
//         async fetchChatRooms() {
//         try {
//             const response = await axiosapi.get(`/api/chat/rooms`);
//             this.chatRooms = response.data;

//             // 连接到 WebSocket
//             connect(() => {
//             this.chatRooms.forEach(chatRoom => {
//                 this.subscribeToChatRoom(chatRoom.id);
//             });
//             });
//         } catch (error) {
//             console.error('Error fetching chat rooms:', error);
//         }
//         },
//         subscribeToChatRoom(chatRoomId) {
//         subscribeToTopic(`/topic/messages/${chatRoomId}`, message => {
//             const receivedMessage = JSON.parse(message.body);
//             const chatRoomIndex = this.chatRooms.findIndex(room => room.id === chatRoomId);

//             if (chatRoomIndex !== -1) {
//             this.chatRooms[chatRoomIndex].lastMessageTime = receivedMessage.timestamp;
//             // 如果需要，可以更新 chatRooms 的内容
//             }
//         });
//         }
//     },
//     created() {
//         this.fetchChatRooms();
//     }
// };
</script>
