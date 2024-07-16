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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.ProductImg;
import com.ispan.warashibe.service.ProductImgService;

@RestController
@RequestMapping("/api/product-imgs")
public class ProductImgController {
    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String getAllProductImgs() throws JsonProcessingException {
        List<ProductImg> productImgs = productImgService.getAllProductImgs();
        return objectMapper.writeValueAsString(productImgs);
    }

    @GetMapping("/{id}")
    public String getProductImgById(@PathVariable int id) throws JsonProcessingException {
        ProductImg productImg = productImgService.getProductImgById(id);
        return objectMapper.writeValueAsString(productImg);
    }

    @PostMapping
    public String saveProductImg(@RequestBody ProductImg productImg) throws JsonProcessingException {
        ProductImg savedProductImg = productImgService.saveProductImg(productImg);
        return createResponse(savedProductImg != null, "新增成功", "新增失敗");
    }

    @PutMapping("/{id}")
    public String updateProductImg(@PathVariable int id, @RequestBody ProductImg productImg) throws JsonProcessingException {
        ProductImg updatedProductImg = productImgService.updateProductImg(id, productImg);
        return createResponse(updatedProductImg != null, "修改成功", "修改失敗");
    }

    @DeleteMapping("/{id}")
    public String deleteProductImg(@PathVariable int id) throws JsonProcessingException {
        productImgService.deleteProductImg(id);
        return createResponse(true, "刪除成功", "刪除失敗");
    }

    @PostMapping("/{id}/upload-image")
    public String uploadProductImage(@PathVariable int id, @RequestParam("file") MultipartFile file) throws Exception {
        productImgService.saveProductImage(id, file);
        return createResponse(true, "上傳成功", "上傳失敗");
    }

    @DeleteMapping("/{id}/delete-image")
    public String deleteProductImage(@PathVariable int id) throws JsonProcessingException {
        productImgService.deleteProductImage(id);
        return createResponse(true, "刪除成功", "刪除失敗");
    }

    private String createResponse(boolean success, String successMessage, String failureMessage) throws JsonProcessingException {
        return objectMapper.writeValueAsString(new Response(success, success ? successMessage : failureMessage));
    }

    static class Response {
        private boolean success;
        private String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
