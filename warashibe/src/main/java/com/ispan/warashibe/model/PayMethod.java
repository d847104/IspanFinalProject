package com.ispan.warashibe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private Integer payMethodID;
		
	@Column(name="payMethod")
	private String payMethod;
	
}
