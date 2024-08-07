package com.ispan.warashibe.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BrowsingHistory")
@Getter
@Setter
public class BrowsingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "memberID")
    private Members member;

    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "productId")
    private Products product;

    @Column(name = "browseTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date browseTime;
    
    @JsonProperty("member")
    public void setMemberById(Integer memberID) {
    	this.member = new Members();
    	this.member.setMemberID(memberID);
    }
    
    @JsonProperty("product")
    public void setProductById(Integer productId) {
    	this.product = new Products();
    	this.product.setProductID(productId);;
    }
}
