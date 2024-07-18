package com.ispan.warashibe.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="PayMethod")
public class PayMethod {

	@Id
	@Column(name="payMethodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer payMethodID;
		
	@Column(name="payMethod")
	private String payMethod;
	
	@OneToMany
	private List<ProductPayMethod> productPayMethod;
	
//	@Override
//	public String toString() {
//		return "model.PayMethod["+payMethodID+","+payMethod+"]";
//	}
	
	
}
