package com.ispan.warashibe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specOneNameID")
public class SpecOneNames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specOneNameID")
	private Integer specOneNameID;
	
	@Column(name = "specOneName", nullable = false)
	private String specOneName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specOneName")
	@JsonIncludeProperties({"specOneID","specOne","specOneQt","specOneImg","specTwoNames"})
	private List<SpecOnes> specOnes;
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "productID", nullable = false)
	private Products product;
	
	@JsonProperty("product")
	private void setProductId(Integer productId) {
		this.product = new Products();
		this.product.setProductID(productId);
	}
}
