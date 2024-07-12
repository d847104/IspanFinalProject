package com.ispan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
	
//	@Column(name="payMethodID")
	@ManyToOne
	@MapsId("payMethodID")
	private Integer payMethodID;
	
//	@Column(name="productID")
	@ManyToOne
	@MapsId("productID")
	private Integer productID;
}
