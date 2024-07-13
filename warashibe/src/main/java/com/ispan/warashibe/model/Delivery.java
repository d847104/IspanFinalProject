package com.ispan.warashibe.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Delivery")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="deliveryID")
	private Integer deliveryID;
	
//	@Column(name="delivery")
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "delivery")
//	@MapsId("delivery")
	private String delivery;
	
	@Column(name="deliveryFee")
	private Integer deliveryFee;
}
