package com.ispan.warashibe.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ProductPayMethod")

public class ProductPayMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "payMethodID")
	@JsonIdentityReference(alwaysAsId = true)
	private PayMethod payMethodID;

	@JsonProperty("payMethodID")
	public void setPayMethodID(Integer payMethodId) {
		this.payMethodID=new PayMethod();
		this.payMethodID.setPayMethodID(payMethodId);
	}
	
	@JsonProperty("payMethodID")
	public Integer getpayMethodID() {
		return (this.payMethodID != null) ? this.payMethodID.getPayMethodID() : null;
	}
	
	@ManyToOne
	@JoinColumn(name = "productID")
	@JsonIdentityReference(alwaysAsId = true)
	private Products productID;
	
	@JsonProperty("productID")
	public void setProductID(Integer productId) {
		this.productID=new Products();
		this.productID.setProductID(productId);
	}
	
	@JsonProperty("productID")
	public Integer getProductID() {
		return (this.productID != null) ? this.productID.getProductID() : null;
	}
	
//	@Override
//	public String toString() {
//		return "model.ProductPayMethod["+id+","+ payMethodID+","+productID+"]";
//	}

}
