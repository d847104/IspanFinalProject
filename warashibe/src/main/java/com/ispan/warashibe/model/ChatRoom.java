package com.ispan.warashibe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ChatRoom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant1_id")
    private Members participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_id")
    private Members participant2;

    @Column(name = "last_message_time")
    private Date lastMessageTime;

    // Getters and Setters
}
