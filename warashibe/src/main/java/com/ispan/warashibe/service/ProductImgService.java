package com.ispan.warashibe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.ProductImg;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.repository.ProductImgRepository;

@Service
public class ProductImgService {

    @Autowired
    private ProductImgRepository productImgRepository;
    @Autowired
    private ProductService productService;

    public void saveProductImgFromJson(String jsonProduct, MultipartFile image) throws Exception {
        JSONObject jsonObj = new JSONObject(jsonProduct);
        int productId = jsonObj.getInt("productID");

        Products product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        ProductImg productImg = new ProductImg();
        productImg.setProduct(product);

        if (image != null && !image.isEmpty()) {
            productImg.setImg(image.getBytes());
        }

        productImgRepository.save(productImg);
    }

    public void updateProductImgFromJson(int id, String jsonProduct, MultipartFile image) throws Exception {
        ProductImg existingProductImg = productImgRepository.findById(id)
                .orElseThrow(() -> new Exception("圖片未找到"));
        JSONObject jsonObj = new JSONObject(jsonProduct);

        // Update the product ID (assuming it's an existing ID in the database)
        int productId = jsonObj.getInt("productID");
        Products product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }
        existingProductImg.setProduct(product);

        if (image != null && !image.isEmpty()) {
            existingProductImg.setImg(image.getBytes());
        }

        productImgRepository.save(existingProductImg);
    }

    public void deleteProductImg(int id) throws Exception {
        ProductImg productImg = productImgRepository.findById(id)
                .orElseThrow(() -> new Exception("圖片未找到"));
        productImgRepository.delete(productImg);
    }

    public ProductImg getProductImgById(int id) throws Exception {
        return productImgRepository.findById(id)
                .orElseThrow(() -> new Exception("圖片未找到"));
    }

    public List<ProductImg> getAllProductImgs() {
        return productImgRepository.findAll();
    }
    public void deleteAllProductImgsByProductId(int productId) throws Exception {
        List<ProductImg> productImgs = productImgRepository.findByProduct_ProductID(productId);
        if (productImgs.isEmpty()) {
            throw new Exception("沒有找到該商品的圖片");
        }
        productImgRepository.deleteAll(productImgs);
    }
}
