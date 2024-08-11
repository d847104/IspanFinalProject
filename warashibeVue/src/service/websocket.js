import SockJS from "sockjs-client";
import Stomp from "stompjs";

const socket = new SockJS(import.meta.env.VITE_API_URL + "/ws");
const stompClient = Stomp.over(socket);

export const connect = (onMessageReceived) => {
    stompClient.connect({}, () => {
        stompClient.subscribe("/topic/messages", (message) => {
            onMessageReceived(JSON.parse(message.body));
        });
    });
};

export const sendMessage = (message) => {
    if (stompClient.connected) {
        stompClient.send("/app/chat", {}, JSON.stringify(message));
    } else {
        console.error('WebSocket is not connected.');
    }
};
