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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specOneID")
public class SpecOnes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specOneID")
	private Integer specOneID;
	
	@Column(name = "specOne", nullable = false)
	private String specOne;
	
	@Column(name = "specOneQt", nullable = true)
	private Integer specOneQt;
	
	@ManyToOne
	@JoinColumn(name = "specOneImg", nullable = true)
	@JsonIdentityReference(alwaysAsId = true)
	private SpecImg specOneImg;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specOne")
	@JsonIncludeProperties({"specTwoNameID","specTwoName","specTwos"})
	private List<SpecTwoNames> specTwoNames;
	
	@ManyToOne
	@JoinColumn(name = "specOneNameID", nullable = false)
	@JsonIncludeProperties({"specOneName","specOneNameID"})
	private SpecOneNames specOneName;
	
	@JsonProperty("specOneName")
	public void setSpecOneNameById(Integer specOneNameId) {
		this.specOneName = new SpecOneNames();
		this.specOneName.setSpecOneNameID(specOneNameId);
	}
	
	@JsonProperty("specOneImg")
	public void setSpecOneImgById(Integer specOneImgId) {
		this.specOneImg = new SpecImg();
		this.specOneImg.setSpecImgID(specOneImgId);
	}
	
	@OneToMany(mappedBy = "specOne")
	@JsonIdentityReference(alwaysAsId = true)
	private List<Cart> cart;
}
