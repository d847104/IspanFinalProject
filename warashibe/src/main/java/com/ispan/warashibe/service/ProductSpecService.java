package com.ispan.warashibe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.ProductSpec;
import com.ispan.warashibe.repository.ProductSpecRepository;

@Service
public class ProductSpecService {
    @Autowired
    private ProductSpecRepository productSpecRepository;

    public List<ProductSpec> getAllProductSpecs() {
        return productSpecRepository.findAll();
    }

    public ProductSpec getProductSpecById(int id) {
        return productSpecRepository.findById(id).orElse(null);
    }

    public ProductSpec saveProductSpec(ProductSpec productSpec) {
        return productSpecRepository.save(productSpec);
    }

    public ProductSpec updateProductSpec(int id, ProductSpec updatedProductSpec) {
        ProductSpec existingProductSpec = getProductSpecById(id);
        if (existingProductSpec != null) {
            updatedProductSpec.setSpecID(id);
            return productSpecRepository.save(updatedProductSpec);
        }
        return null;
    }

    public void deleteProductSpec(int id) {
        productSpecRepository.deleteById(id);
    }

    public void saveSpecImage(int id, MultipartFile file) throws Exception {
        ProductSpec productSpec = getProductSpecById(id);
        if (productSpec != null && !file.isEmpty()) {
            productSpec.setSpecImg(file.getBytes());
            productSpecRepository.save(productSpec);
        }
    }

    public void deleteSpecImage(int id) {
        ProductSpec productSpec = getProductSpecById(id);
        if (productSpec != null) {
            productSpec.setSpecImg(null);
            productSpecRepository.save(productSpec);
        }
    }
}