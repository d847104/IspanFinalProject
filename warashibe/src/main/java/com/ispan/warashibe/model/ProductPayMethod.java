<<<<<<< HEAD
package com.ispan.warashibe.model;

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

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer favID;
	
	@ManyToOne
	@JoinColumn(name = "memberID", nullable = false)
	private Members member;
	
	@OneToOne
	@JoinColumn(name = "productID", nullable = false)
	private Products product;
	
	@OneToOne
	@JoinColumn(name = "sellerID", nullable = false)
	private Members seller;
}
=======
//package com.ispan.warashibe.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name="ProductPayMethod")
//public class ProductPayMethod {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="id")
//	private Integer id;
//	
////	@Column(name="payMethodID")
//	@ManyToOne
//	@MapsId("payMethodID")
//	private Integer payMethodID;
//	
////	@Column(name="productID")
//	@ManyToOne
//	@MapsId("productID")
//	private Integer productID;
//}
>>>>>>> dev
