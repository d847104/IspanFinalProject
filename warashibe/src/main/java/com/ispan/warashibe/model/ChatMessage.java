package com.ispan.warashibe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ChatMessage")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Members sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Members receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "is_read")
    private Boolean isRead;

    // Getters and Setters
}
