package com.ispan.warashibe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private Long chatRoomId;
    private Integer senderId;
    private Integer receiverId;
    private String message;

    // Getters and Setters
}
