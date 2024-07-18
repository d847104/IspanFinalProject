package com.ispan.warashibe.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "rankID")
@Entity
@Table(name = "Rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rankID")
    private Integer rankID;

    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnoreProperties("productSpecs") // 防止無限遞歸
    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    private Products product;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "memberID", referencedColumnName = "memberID")
    private Members member;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private Orders order;
    
    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "rankmsg")
    private String rankmsg;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rankDate")
    private Date rankDate;
    
    @JsonProperty("member")
    public void setMemberById(Integer memberID) {
    	this.member = new Members();
    	this.member.setMemberID(memberID);
    }
    
    @JsonProperty("order")
    public void setOrderById(Integer orderID) {
    	this.order = new Orders();
    	this.order.setOrderID(orderID);
    }
    
    @JsonProperty("product")
    public void setProductById(Integer productID) {
    	this.product = new Products();
    	this.product.setProductID(productID);
    }
}
