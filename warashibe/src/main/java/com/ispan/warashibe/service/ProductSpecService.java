package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.ProductSpec;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.repository.ProductSpecRepository;

@Service
public class ProductSpecService {

    @Autowired
    private ProductSpecRepository productSpecRepository;
    @Autowired
    private ProductService productService;

    public void saveProductSpecFromJson(String jsonProduct, MultipartFile image) throws Exception {
        JSONObject jsonObj = new JSONObject(jsonProduct);
        String specOne = jsonObj.getString("specOne");
        String specOneName = jsonObj.getString("specOneName");
        String specTwo = jsonObj.getString("specTwo");
        String specTwoName = jsonObj.getString("specTwoName");
        int productId = jsonObj.getInt("productID");

        Products product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        Optional<ProductSpec> existingSpec = productSpecRepository.findBySpecOneAndSpecOneNameAndSpecTwoAndSpecTwoNameAndProduct(specOne, specOneName, specTwo, specTwoName, product);
        if (existingSpec.isPresent()) {
            throw new IllegalArgumentException("相同規格已存在");
        }

        ProductSpec productSpec = new ProductSpec();
        productSpec.setSpecOne(specOne);
        productSpec.setSpecOneName(specOneName);
        productSpec.setSpecTwo(specTwo);
        productSpec.setSpecTwoName(specTwoName);
        productSpec.setSpecQt(jsonObj.getInt("specQt"));
        productSpec.setProduct(product);

        if (image != null && !image.isEmpty()) {
            productSpec.setSpecImg(image.getBytes());
        }

        productSpecRepository.save(productSpec);
    }

    public void updateProductSpecFromJson(int id, String jsonProduct, MultipartFile image) throws Exception {
        ProductSpec existingProductSpec = productSpecRepository.findById(id)
                .orElseThrow(() -> new Exception("規格未找到"));
        JSONObject jsonObj = new JSONObject(jsonProduct);
        
        existingProductSpec.setSpecOne(jsonObj.getString("specOne"));
        existingProductSpec.setSpecOneName(jsonObj.getString("specOneName"));
        existingProductSpec.setSpecTwo(jsonObj.getString("specTwo"));
        existingProductSpec.setSpecTwoName(jsonObj.getString("specTwoName"));
        existingProductSpec.setSpecQt(jsonObj.getInt("specQt"));
        
        // Update the product ID (assuming it's an existing ID in the database)
        int productId = jsonObj.getInt("productID");
        Products product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }
        existingProductSpec.setProduct(product);
        
        if (image != null && !image.isEmpty()) {
            existingProductSpec.setSpecImg(image.getBytes());
        }
        
        productSpecRepository.save(existingProductSpec);
    }

    public void deleteProductSpec(int id) throws Exception {
        ProductSpec productSpec = productSpecRepository.findById(id)
                .orElseThrow(() -> new Exception("規格未找到"));
        productSpecRepository.delete(productSpec);
    }

    public ProductSpec getProductSpecById(int id) throws Exception {
        return productSpecRepository.findById(id)
                .orElseThrow(() -> new Exception("規格未找到"));
    }

    public List<ProductSpec> getAllProductSpecs() {
        return productSpecRepository.findAll();
    }

    public void deleteProductSpecImage(int id) throws Exception {
        ProductSpec productSpec = productSpecRepository.findById(id)
                .orElseThrow(() -> new Exception("規格未找到"));
        productSpec.setSpecImg(null);
        productSpecRepository.save(productSpec);
    }
}
