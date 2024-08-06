package com.ispan.warashibe.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class Favorite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FavoriteID favID;
	
	@ManyToOne
	@JoinColumn(name = "memberID", nullable = false, insertable = false, updatable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members member;
	
	@ManyToOne
	@JoinColumn(name = "productID", nullable = false, insertable = false, updatable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Products product;
	
	@ManyToOne
	@JoinColumn(name = "sellerID", nullable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Members seller;
}
