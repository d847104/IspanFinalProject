package com.ispan.warashibe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@OneToOne(mappedBy = "specTwoImg")
	@JsonIdentityReference(alwaysAsId = true)
	private SpecTwos specTwo;
	
	@OneToOne(mappedBy = "specOneImg")
	@JsonIdentityReference(alwaysAsId = true)
	private SpecOnes specOne;
}