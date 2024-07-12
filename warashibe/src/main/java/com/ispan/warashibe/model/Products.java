package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "memberID")
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
    @JoinColumn(name = "subCategoryID")
    private SubCategory subCategory;

    @Lob
    private String description;

    @NotNull
    private LocalDateTime uploadDate;

    @NotNull
    private LocalDateTime updateTime;

    @NotNull
    private boolean isSecondHand;

    @Lob
    private String wishItem;

    @OneToMany(mappedBy = "product")
    private List<ProductSpec> productSpecs;

    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImgs;

    // Constructors, Getters, and Setters
    public Products() {}

    // Getters and Setters
}
