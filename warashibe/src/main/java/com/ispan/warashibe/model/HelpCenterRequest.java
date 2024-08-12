package com.ispan.warashibe.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HelpCenterRequests")
@Getter
@Setter
@NoArgsConstructor
public class HelpCenterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private String memberType;
    private String requestType;
    private Long relatedOrderId;
    private Long relatedProductId;
    private String description;
    private LocalDateTime requestTime;
    
    // 新增字段: 处理情形
    private String status; // "已處理" 或 "未處理"

    @PrePersist
    public void onCreate() {
        if (requestTime == null) {
            requestTime = LocalDateTime.now();
        }
    }
}
