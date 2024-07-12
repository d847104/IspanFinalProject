package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int specID;

    @Size(max = 255)
    private String specOneName;

    @Size(max = 255)
    private String specOne;

    @Size(max = 255)
    private String specTwoName;

    @Size(max = 255)
    private String specTwo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productID")
    private Products product;

    @NotNull
    private int specQt;

    @Lob
    private byte[] specImg;

    // Constructors, Getters, and Setters
    public ProductSpec() {}

    // Getters and Setters
}
