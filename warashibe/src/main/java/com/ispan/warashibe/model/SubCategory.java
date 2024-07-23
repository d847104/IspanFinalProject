package com.ispan.warashibe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "subCategoryID")
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCategoryID;

    @NotNull
    @Size(max = 255)
    private String subCategory;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mainCategoryID")
    @JsonIdentityReference(alwaysAsId = true)
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Products> products;
    
    @JsonProperty("mainCategory")
    public void setMainCategoryById(Integer mainCategoryId) {
    	this.mainCategory = new MainCategory();
    	this.mainCategory.setMainCategoryID(mainCategoryId);
    }
}