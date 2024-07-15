package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.service.CategoryService;
import com.ispan.warashibe.service.ProductService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/recommended")
    public List<Products> getRecommendedProducts(@RequestParam List<Integer> categoryIds) {
        List<SubCategory> categories = categoryService.getSubCategoriesByIds(categoryIds);
        return productService.getRecommendedProducts(categories);
    }

    @GetMapping("/category/{id}")
    public List<Products> getProductsByCategory(@PathVariable int id) {
        SubCategory subCategory = categoryService.getSubCategoryById(id);
        return productService.getProductsBySubCategory(subCategory);
    }
}
