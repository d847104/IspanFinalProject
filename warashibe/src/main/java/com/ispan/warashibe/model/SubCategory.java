package com.ispan.warashibe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private int subCategoryID;

    @NotNull
    @Size(max = 255)
    private String subCategory;

    @NotNull
    @ManyToOne
    
    @JoinColumn(name = "mainCategoryID")
    @JsonIgnoreProperties("subCategories") // 防止無限遞歸
    private MainCategory mainCategory;

    @JsonIgnoreProperties("subCategory") // 防止無限遞歸
    @OneToMany(mappedBy = "subCategory")
    private List<Products> products;
}
