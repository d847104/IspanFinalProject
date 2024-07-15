package com.ispan.warashibe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper; // 用來解析 JSON 字符串

    public List<Products> getRandomProducts() {
        return productRepository.findRandomProducts(PageRequest.of(0, 4));
    }

    public List<Products> getSecondHandProducts() {
        return productRepository.findSecondHandProducts();
    }

    public List<Products> getPopularProducts() {
        return productRepository.findPopularProducts(PageRequest.of(0, 4));
    }

    public List<Products> getRecommendedProducts(List<SubCategory> categories) {
        return productRepository.findRecommendedProducts(categories, PageRequest.of(0, 4));
    }

    public List<Products> getProductsBySubCategory(SubCategory subCategory) {
        return productRepository.findBySubCategory(subCategory);
    }

    public List<Products> getProductsByMember(Members member) {
        return productRepository.findByMember(member);
    }

    public List<Products> getProductsByStatusAndMember(Boolean status, Members member) {
        return productRepository.findByProductStatusAndMember(status, member);
    }

    public Products getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProductFromJson(String jsonProduct) throws JsonProcessingException {
        Products product = objectMapper.readValue(jsonProduct, Products.class);
        productRepository.save(product);
    }

    public void updateProductFromJson(int id, String jsonProduct) throws JsonProcessingException {
        Products existingProduct = getProductById(id);
        if (existingProduct != null) {
            Products updatedProduct = objectMapper.readValue(jsonProduct, Products.class);
            updatedProduct.setProductID(id); // 保持ID一致
            productRepository.save(updatedProduct);
        }
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
