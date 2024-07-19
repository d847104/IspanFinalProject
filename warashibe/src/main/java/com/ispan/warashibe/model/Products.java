package com.ispan.warashibe.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productID")
@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productID;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "memberID")
	@JsonIgnoreProperties("productSpecs") // 防止無限遞歸
	@JsonIdentityReference(alwaysAsId = true)
	private Members member;

	@NotNull
	@Size(max = 255)
	private String productName;

	@NotNull
	private int price;

	@NotNull
	private int stock;

	@NotNull
	@ManyToOne
	@JsonIgnoreProperties("products") // 防止無限遞歸
	@JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "subCategoryID")
	private SubCategory subCategory;

	@Lob
	private String description;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@NotNull
	private Date uploadDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@NotNull
	private Date updateTime;

	@NotNull
	private boolean isSecondHand;

	@Column(name = "productStatus")
	private Boolean productStatus;

	@Lob
	private String wishItem;

	@JsonIgnoreProperties("products") // 防止無限遞歸
	@JsonIdentityReference(alwaysAsId = true)
	@OneToMany(mappedBy = "product")
	private List<ProductSpec> productSpecs;

	@JsonIgnoreProperties("products") // 防止無限遞歸
	@OneToMany(mappedBy = "product")
	@JsonIdentityReference(alwaysAsId = true)
	private List<ProductImg> productImgs;

	@JsonProperty("member")
	public void setMemberById(Integer memberID) {
		this.member = new Members();
		this.member.setMemberID(memberID);
	}

	@JsonProperty("subCategory")
	public void setSubCategoryById(Integer subCategoryID) {
		this.subCategory = new SubCategory();
		this.subCategory.setSubCategoryID(subCategoryID);
	}

	@OneToMany(mappedBy = "product")
	@JsonIdentityReference(alwaysAsId = true)
	private List<Cart> cart;
}
