package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

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

    public void updateProductImgFromJson(int productId, String jsonProduct, MultipartFile image) throws Exception {
        Products product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found");
        }

        // Try to find an existing image for the product
        ProductImg existingProductImg = productImgRepository.findByProduct(product);

        if (existingProductImg == null) {
            // Create a new ProductImg if none exists
            existingProductImg = new ProductImg();
            existingProductImg.setProduct(product);
        }

        // If a new image file is provided, update the image data
        if (image != null && !image.isEmpty()) {
            existingProductImg.setImg(image.getBytes());
        }

        // Save the updated or new ProductImg object
        productImgRepository.save(existingProductImg);
    }

    public void deleteProductImg(Integer id) throws Exception {
        ProductImg productImg = productImgRepository.findById(id)
                .orElseThrow(() -> new Exception("圖片未找到"));
        productImgRepository.delete(productImg);
    }

    public ProductImg getProductImgById(Integer id) throws Exception {
    	Optional<ProductImg> optional = productImgRepository.findById(id);
    	if(optional.isPresent()) {
    		return productImgRepository.findById(id)
    				.orElseThrow(() -> new Exception("圖片未找到"));
    	} return null;
    }

    public List<ProductImg> getAllProductImgs() {
        return productImgRepository.findAll();
    }
    
    public List<ProductImg> getProductImgByProductId(Integer id) {
    	return productImgRepository.findByProductId(id);
    }
    
    public void deleteAllProductImgsByProductId(Integer productId) throws Exception {
        List<ProductImg> productImgs = productImgRepository.findByProduct_ProductID(productId);
        if (productImgs.isEmpty()) {
            throw new Exception("沒有找到該商品的圖片");
        }
        productImgRepository.deleteAll(productImgs);
    }
}
