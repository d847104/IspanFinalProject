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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "specTwoNameID")
public class SpecTwoNames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specTwoNameID")
	private Integer specTwoNameID;
	
	@Column(name = "specTwoName", nullable = false)
	private String specTwoName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specTwoName")
	@JsonIncludeProperties({"specTwo","specTwoQt","specTwoImg"})
	private List<SpecTwos> specTwos;
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "specOneID", nullable = false)
	private SpecOnes specOne;
	
	@JsonProperty("specOne")
	public void setSpecOneById(Integer specOneId) {
		this.specOne = new SpecOnes();
		this.specOne.setSpecOneID(specOneId);
	}
}
