package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartID")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartID")
	private Integer cartID;
	
	@ManyToOne
	@JoinColumn(name = "member", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members member;
	
	@ManyToOne
	@JoinColumn(name = "product", nullable = false)
	@JsonIncludeProperties({"productID","productName","productImgs","stock","specOneName","price","productDeliveries","productPayMethods"})
	private Products product;
	
	@ManyToOne
	@JoinColumn(name = "seller", nullable = false)
	@JsonIncludeProperties({"memberID","username"})
	private Members seller;
	
	@ManyToOne
	@JoinColumn(name = "specOne", nullable = true)
	@JsonIncludeProperties({"specOneID","specOne","specOneQt","specOneImg","specOneName"})
	private SpecOnes specOne;
	
	@ManyToOne
	@JoinColumn(name = "specTwo", nullable = true)
	@JsonIncludeProperties({"specTwoID","specTwo","specTwoQt","specTwoImg","specTwoName"})
	private SpecTwos specTwo;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	/////////// 以下用於反序列化JSON ///////////
	
	@JsonProperty("member")
	public void setMemberById(Integer buyerId) {
		this.member = new Members();
		this.member.setMemberID(buyerId);
	}
	
	@JsonProperty("product")
	public void setProductById(Integer productId) {
		this.product = new Products();
		this.product.setProductID(productId);
	}
		
	@JsonProperty("seller")
	public void setSellerById(Integer sellerId) {
		this.seller = new Members();
		this.seller.setMemberID(sellerId);
	}
	
	@JsonProperty("specOne")
	public void setSpecOneById(Integer specOneId) {
		this.specOne = new SpecOnes();
		if(specOneId!=null) {
			this.specOne.setSpecOneID(specOneId);			
		} else {this.specOne = null;}
	}
	
	@JsonProperty("specTwo")
	public void setSpecTwoById(Integer specTwoId) {
		this.specTwo = new SpecTwos();
		if(specTwoId!=null) {
			this.specTwo.setSpecTwoID(specTwoId);			
		} else {this.specTwo = null;}
	}
}