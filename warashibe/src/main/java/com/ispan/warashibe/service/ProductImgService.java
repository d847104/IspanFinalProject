package com.ispan.warashibe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.ProductImg;
import com.ispan.warashibe.repository.ProductImgRepository;

@Service
public class ProductImgService {
    @Autowired
    private ProductImgRepository productImgRepository;

    public List<ProductImg> getAllProductImgs() {
        return productImgRepository.findAll();
    }

    public ProductImg getProductImgById(int id) {
        return productImgRepository.findById(id).orElse(null);
    }

    public ProductImg saveProductImg(ProductImg productImg) {
        return productImgRepository.save(productImg);
    }

    public ProductImg updateProductImg(int id, ProductImg updatedProductImg) {
        ProductImg existingProductImg = getProductImgById(id);
        if (existingProductImg != null) {
            updatedProductImg.setImgID(id);
            return productImgRepository.save(updatedProductImg);
        }
        return null;
    }

    public void deleteProductImg(int id) {
        productImgRepository.deleteById(id);
    }

    public void saveProductImage(int id, MultipartFile file) throws Exception {
        ProductImg productImg = getProductImgById(id);
        if (productImg != null && !file.isEmpty()) {
            productImg.setImg(file.getBytes());
            productImgRepository.save(productImg);
        }
    }

    public void deleteProductImage(int id) {
        ProductImg productImg = getProductImgById(id);
        if (productImg != null) {
            productImg.setImg(null);
            productImgRepository.save(productImg);
        }
    }
}
