package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specTwoID")
public class SpecTwos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specTwoID")
	private Integer specTwoID;
	
	@Column(name = "specTwo", nullable = false)
	private String specTwo;
	
	@Column(name = "specTwoQt", nullable = false)
	private Integer specTwoQt;
	
	@OneToOne
	@JoinColumn(name = "specTwoImg", nullable = true)
	@JsonIdentityReference(alwaysAsId = true)
	private SpecImg specTwoImg;
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "specTwoNameID", nullable = false)
	private SpecTwoNames specTwoName;
	
	@JsonProperty("specTwoName")
	public void setSpecTwoNameById(Integer specTwoNameId) {
		this.specTwoName = new SpecTwoNames();
		this.specTwoName.setSpecTwoNameID(specTwoNameId);
	}
	
	@JsonProperty("specTwoImg")
	public void setSpecOneImgById(Integer specTwoImgId) {
		this.specTwoImg = new SpecImg();
		this.specTwoImg.setSpecImgID(specTwoImgId);
	}
}
