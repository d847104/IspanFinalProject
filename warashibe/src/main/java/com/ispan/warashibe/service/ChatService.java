package com.ispan.warashibe.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.ChatMessage;
import com.ispan.warashibe.model.ChatRoom;
import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.repository.ChatMessageRepository;
import com.ispan.warashibe.repository.ChatRoomRepository;
import com.ispan.warashibe.repository.MembersRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private MembersRepository memberRepository; // 假设您已有 MemberRepository

    public ChatRoom findOrCreateChatRoom(Members participant1, Members participant2) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByParticipant1AndParticipant2(participant1, participant2)
                .or(() -> chatRoomRepository.findByParticipant2AndParticipant1(participant1, participant2));

        return chatRoom.orElseGet(() -> createChatRoom(participant1, participant2));
    }

    public ChatRoom createChatRoom(Members participant1, Members participant2) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setParticipant1(participant1);
        chatRoom.setParticipant2(participant2);
        chatRoom.setLastMessageTime(new Date());
        return chatRoomRepository.save(chatRoom);
    }

    public ChatMessage sendMessage(ChatRoom chatRoom, Members sender, Members receiver, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setMessage(message);
        chatMessage.setTimestamp(new Date());
        chatMessage.setIsRead(false);
        chatRoom.setLastMessageTime(new Date());
        chatRoomRepository.save(chatRoom);
        return chatMessageRepository.save(chatMessage);
    }

    public void markAsRead(Long messageId) {
        ChatMessage chatMessage = chatMessageRepository.findById(messageId).orElseThrow();
        chatMessage.setIsRead(true);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }

    public Members findMemberById(Integer memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
    }
}

