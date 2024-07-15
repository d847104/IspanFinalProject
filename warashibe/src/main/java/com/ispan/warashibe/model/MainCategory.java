package com.ispan.warashibe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mainCategoryID;

    @NotNull
    @Size(max = 255)
    private String mainCategory;

    @OneToMany(mappedBy = "mainCategory")
    @JsonIgnoreProperties("mainCategory") // 防止無限遞歸
    private List<SubCategory> subCategories;

}
