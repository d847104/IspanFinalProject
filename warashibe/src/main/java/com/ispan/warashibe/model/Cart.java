package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	@JoinColumn(name = "memberID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members member;
	
	@ManyToOne
	@JoinColumn(name = "productID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Products product;
	
	@OneToOne
	@JoinColumn(name = "specID", nullable = true)
	@JsonIdentityReference(alwaysAsId = true)
	private ProductSpec productSpec;
	
	@ManyToOne
	@JoinColumn(name = "sellerID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members seller;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@JsonProperty("member")
	public void setMemberById(Integer sellerId) {
		this.member = new Members();
		this.member.setMemberID(sellerId);
	}
	
	@JsonProperty("seller")
	public void setSellerById(Integer sellerId) {
		this.seller = new Members();
		this.seller.setMemberID(sellerId);
	}
	
	@JsonProperty("product")
	public void setProductById(Integer productId) {
		this.product = new Products();
		this.product.setProductID(productId);
	}
	
	@JsonProperty("productSpec")
	public void setSpecById(Object specId) {
		this.productSpec = new ProductSpec();
		if(specId!=null) {
			this.productSpec.setSpecID((Integer)specId);			
		} this.productSpec = null;
	}
}