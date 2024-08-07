package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specImgID")
public class SpecImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer specImgID;
	
	@Lob
	private byte[] specImg;
	
	@OneToOne
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "specOne")
	private SpecOnes specOne;
	
	@OneToOne
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "specTwo")
	private SpecTwos specTwo;
	
	@JsonProperty("specOne")
	public void setSpecOneById(Integer specOneId) {
		this.specOne = new SpecOnes();
		this.specOne.setSpecOneID(specOneId);
	}
	
	@JsonProperty("specTwo")
	public void setSpecTwoById(Integer specTwoId) {
		this.specTwo = new SpecTwos();
		this.specTwo.setSpecTwoID(specTwoId);
	}
}