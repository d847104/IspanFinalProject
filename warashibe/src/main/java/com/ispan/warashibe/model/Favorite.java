package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "favID")
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer favID;
	
	@ManyToOne
	@JoinColumn(name = "memberID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members member;
	
	@OneToOne
	@JoinColumn(name = "productID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Products product;
	
	@OneToOne
	@JoinColumn(name = "sellerID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members seller;
}
