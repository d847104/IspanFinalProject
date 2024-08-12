package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ispan.warashibe.dto.ChatRoomRequest;
import com.ispan.warashibe.model.ChatMessage;
import com.ispan.warashibe.model.ChatRoom;
import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.service.ChatService;

@Controller
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) throws Exception {
        ChatRoom chatRoom = chatService.findOrCreateChatRoom(message.getSender(), message.getReceiver());
        chatService.sendMessage(chatRoom, message.getSender(), message.getReceiver(), message.getMessage());
        return message;
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> getOrCreateChatRoom(@RequestBody ChatRoomRequest request) {
        Members participant1 = chatService.findMemberById(request.getParticipant1Id());
        Members participant2 = chatService.findMemberById(request.getParticipant2Id());
        ChatRoom chatRoom = chatService.findOrCreateChatRoom(participant1, participant2);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/room/{id}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long id) {
        List<ChatMessage> messages = chatService.getChatMessages(id);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/message/read/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        chatService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}

