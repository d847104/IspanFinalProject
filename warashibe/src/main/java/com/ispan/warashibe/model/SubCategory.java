package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

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
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private List<Products> products;

    // Constructors, Getters, and Setters
    public SubCategory() {}

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(int subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
