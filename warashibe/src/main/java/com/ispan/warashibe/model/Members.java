package com.ispan.warashibe.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "memberID") // 處理JACKSON循環引用
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Members")
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberID")
    private Integer memberID;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "gender")
    private String gender;

    @Lob
    @Column(name = "profileImg", columnDefinition = "image")
    private byte[] profileImg;

    @Column(name = "intro")
    private String intro;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "lastLogin")
    private Date lastLogin;

    @Column(name = "status")
    private String status;

    @PrePersist
    public void onCreate() {
        if (createTime == null) {
            createTime = new java.util.Date();
        }
        if (lastLogin == null) {
            lastLogin = new Date();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastLogin = new Date();
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberID")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Recepient> byRecepioent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverID")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Notification> receiverID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderID")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Notification> senderID;

    // products @ManyToMany , @JoinColumn(name = "sellerID")

    @ManyToMany
    @JsonIgnoreProperties("member") // 防止無限遞歸
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name = "Favorite", joinColumns = {
            @JoinColumn(name = "memberID") }, inverseJoinColumns = @JoinColumn(name = "productID"))
    private List<Products> Products;

    
    @OneToMany(mappedBy = "member")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Rank> ranks;
    
    // mappedBy是對應屬性名稱
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Favorite> favoritesToBuyer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Favorite> favoritesToSeller;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    private List<Orders> buyerOrders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    private List<Orders> sellerOrders = new ArrayList<>();
}
