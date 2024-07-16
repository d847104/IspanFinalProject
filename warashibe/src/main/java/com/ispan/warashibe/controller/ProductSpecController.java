package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.ProductSpec;
import com.ispan.warashibe.service.ProductSpecService;

@RestController
@RequestMapping("/api/product-specs")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String getAllProductSpecs() throws JsonProcessingException {
        List<ProductSpec> productSpecs = productSpecService.getAllProductSpecs();
        return objectMapper.writeValueAsString(productSpecs);
    }

    @GetMapping("/{id}")
    public String getProductSpecById(@PathVariable int id) throws JsonProcessingException {
        ProductSpec productSpec = productSpecService.getProductSpecById(id);
        return objectMapper.writeValueAsString(productSpec);
    }

    @PostMapping
    public String saveProductSpec(@RequestBody ProductSpec productSpec) throws JsonProcessingException {
        ProductSpec savedProductSpec = productSpecService.saveProductSpec(productSpec);
        return createResponse(savedProductSpec != null, "新增成功", "新增失敗");
    }

    @PutMapping("/{id}")
    public String updateProductSpec(@PathVariable int id, @RequestBody ProductSpec productSpec) throws JsonProcessingException {
        ProductSpec updatedProductSpec = productSpecService.updateProductSpec(id, productSpec);
        return createResponse(updatedProductSpec != null, "修改成功", "修改失敗");
    }

    @DeleteMapping("/{id}")
    public String deleteProductSpec(@PathVariable int id) throws JsonProcessingException {
        productSpecService.deleteProductSpec(id);
        return createResponse(true, "刪除成功", "刪除失敗");
    }

    @PostMapping("/{id}/upload-image")
    public String uploadSpecImage(@PathVariable int id, @RequestParam("file") MultipartFile file) throws Exception {
        productSpecService.saveSpecImage(id, file);
        return createResponse(true, "上傳成功", "上傳失敗");
    }

    @DeleteMapping("/{id}/delete-image")
    public String deleteSpecImage(@PathVariable int id) throws JsonProcessingException {
        productSpecService.deleteSpecImage(id);
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
