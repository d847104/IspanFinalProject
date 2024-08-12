package com.ispan.warashibe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ContactMessages")
@Getter
@Setter
@NoArgsConstructor
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String questionType;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String status;

    private LocalDateTime submitTime;

    @PrePersist
    public void onCreate() {
        if (submitTime == null) {
            submitTime = LocalDateTime.now();
        }
        if (status == null) {
            status = "處理中"; // 默认状态为“處理中”
        }
    }
}

