package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    public SubCategory() {}

}
