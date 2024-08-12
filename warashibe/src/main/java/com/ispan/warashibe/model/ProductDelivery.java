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

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "ProductDelivery")
public class ProductDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "deliveryID")
	@JsonIncludeProperties({"delivery","deliveryID"})
	private Delivery delivery;

	@ManyToOne
	@JoinColumn(name = "productID")
	@JsonIdentityReference(alwaysAsId = true)
	private Products productID;

	@JsonProperty("delivery")
	public void setDeliveryID(Integer delivery) {
		this.delivery = new Delivery();
		this.delivery.setDeliveryID(delivery);
	}

	@JsonProperty("productID")
	public void setProductID(Integer productId) {
		this.productID = new Products();
		this.productID.setProductID(productId);
	}

}
