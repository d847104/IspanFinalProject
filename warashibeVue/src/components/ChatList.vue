<template>
<div>
    <h1>聊天列表</h1>
    <ul>
    <li v-for="chatRoom in chatRooms" :key="chatRoom.id" @click="goToChatRoom(chatRoom.id)">
        <p>{{ chatRoom.participant1.username }} 和 {{ chatRoom.participant2.username }}</p>
        <p>最后消息时间: {{ chatRoom.lastMessageTime }}</p>
    </li>
    </ul>
</div>
</template>

<script>
export default {
data() {
    return {
    chatRooms: []
    };
},
methods: {
    goToChatRoom(chatRoomId) {
    this.$router.push(`/chatroom/${chatRoomId}`);
    },
    fetchChatRooms() {
    // 调用API获取聊天列表
    }
},
created() {
    this.fetchChatRooms();
}
};
</script>
