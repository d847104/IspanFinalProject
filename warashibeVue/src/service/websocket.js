import SockJS from "sockjs-client";
import Stomp from "stompjs";

let stompClient = null;

export const connect = (onConnected) => {
    const socket = new SockJS(import.meta.env.VITE_API_URL + "/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        if (onConnected) {
            onConnected();
        }
    });
};

export const subscribeToTopic = (topic, onMessageReceived) => {
    if (stompClient) {
        stompClient.subscribe(topic, message => {
            if (onMessageReceived) {
                onMessageReceived(message);
            }
        });
    } else {
        console.error('WebSocket is not connected.');
    }
};

export const sendMessage = (destination, message) => {
    if (stompClient && stompClient.connected) {
        stompClient.send(destination, {}, JSON.stringify(message));
    } else {
        console.error('WebSocket is not connected.');
    }
};

