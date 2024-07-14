package com.ispan.warashibe.model;

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
public class OrderProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "orderID", nullable = false)
	private Orders order;
	
	@OneToOne
	@JoinColumn(name = "productID", nullable = false)
	private Products product;
	
	@Column(name = "quantuty", nullable = false)
	private Integer quantity;
}
