package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.service.CategoryService;
import com.ispan.warashibe.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/random")
    public List<Products> getRandomProducts() {
        return productService.getRandomProducts();
    }

    @GetMapping("/second-hand")
    public List<Products> getSecondHandProducts() {
        return productService.getSecondHandProducts();
    }

    @GetMapping("/popular")
    public List<Products> getPopularProducts() {
        return productService.getPopularProducts();
    }

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

    @GetMapping("/{id}")
    public Products getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public void saveProduct(@RequestBody String jsonProduct) throws JsonProcessingException {
        productService.saveProductFromJson(jsonProduct);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody String jsonProduct) throws JsonProcessingException {
        productService.updateProductFromJson(id, jsonProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}
