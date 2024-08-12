package com.ispan.warashibe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "deliveryID")
@Table(name = "Delivery")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deliveryID")
	private Integer deliveryID;

	@Size(max = 255)
	private String delivery;

	@Column(name = "deliveryFee")
	private Integer deliveryFee;

	@OneToMany(mappedBy = "delivery")
	@JsonIdentityReference(alwaysAsId = true)
	private List<Delivery> deliverys;

//	@Override
//	public String toString() {
//		return "model.Delivery["+deliveryID+","+ delivery+","+deliveryFee+"]";
//		return deliveryID;
//	}

}
