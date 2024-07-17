package com.ispan.warashibe.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    }

   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberID")
    @JsonIgnore   //防止無限遞歸
    private List<Recepient> byRecepioent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverID")
    @JsonIgnore   //防止無限遞歸
    private List<Notification> receiverID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderID")
    @JsonIgnore   //防止無限遞歸
    private List<Notification> senderID;

    
    // products @ManyToMany , @JoinColumn(name = "sellerID")
//    @ManyToMany
//    @JoinTable(name = "Favorite", joinColumns = {
//            @JoinColumn(name = "memberID") }, inverseJoinColumns = @JoinColumn(name = "productID"))
//    private List<Products> Products;
    
    // mappedBy是對應屬性名稱
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Favorite> favoritesToBuyer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    private List<Favorite> favoritesToSeller;
    
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> roles;
    
    
}
