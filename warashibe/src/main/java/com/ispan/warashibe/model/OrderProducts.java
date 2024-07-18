package com.ispan.warashibe.model;

import org.hibernate.annotations.DynamicInsert;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrderProducts")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "orderID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "productID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Products product;

	@Column(name = "productName", nullable = false)
	private String productName;

	@Column(name = "specOneName")
	private String specOneName;

	@Column(name = "specTwoName")
	private String specTwoName;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@JsonProperty("order")
	public void setOrderById(Integer orderId) {
		this.order = new Orders();
		this.order.setOrderID(orderId);
	}

	@JsonProperty("product")
	public void setProductById(Integer productId) {
		this.product = new Products();
		this.product.setProductID(productId);
	}
}
