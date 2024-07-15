package com.ispan.warashibe.model;

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
@Table(name="ProductDelivery")
public class ProductDelivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
//	@Column(name="deliveryID")
	@ManyToOne
	@JoinColumn(name = "deliveryID")
	private Members deliveryID;
	
//	@Column(name="productID")
	@ManyToOne
	@JoinColumn(name = "productID")
	private Members productID;
}
