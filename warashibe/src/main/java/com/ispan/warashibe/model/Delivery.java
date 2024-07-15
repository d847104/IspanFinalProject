package com.ispan.warashibe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Delivery")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deliveryID")
	private Integer deliveryID;

	// @ManyToOne
	// @JoinColumn(name = "delivery")
	@Size(max = 255)
	private String delivery;
	// private Delivery delivery;

	@Column(name = "deliveryFee")
	private Integer deliveryFee;

	@OneToMany(mappedBy = "delivery")
	private List<Delivery> deliverys;
}
