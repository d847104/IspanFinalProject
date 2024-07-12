package com.ispan.warashibe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Recepient")
public class Recepient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recepientID")
	private Integer recepientID;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="memberID")
	private Members memberID;
	

}
