package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mainCategoryID;

    @NotNull
    @Size(max = 255)
    private String mainCategory;

    @OneToMany(mappedBy = "mainCategory")
    private List<SubCategory> subCategories;

    // Constructors, Getters, and Setters
    public MainCategory() {}

    // Getters and Setters
}
