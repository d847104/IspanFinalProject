package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specID")
@Entity
public class ProductSpec {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int specID;

	@Size(max = 255)
	private String specOneName;

	@Size(max = 255)
	private String specOne;

	@Size(max = 255)
	private String specTwoName;

	@Size(max = 255)
	private String specTwo;

	@NotNull
	@JsonIgnoreProperties("productSpecs") // 防止無限遞歸
	@JsonIdentityReference(alwaysAsId = true)
	@ManyToOne
	@JoinColumn(name = "productID")
	private Products product;

	@NotNull
	private int specQt;

	@Lob
	private byte[] specImg;
}
