package com.ispan.warashibe.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.ProductSpec;
import com.ispan.warashibe.service.ProductSpecService;

@RestController
@RequestMapping("/api/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @PostMapping
    public String saveProductSpec(@RequestParam("jsonProduct") String jsonProduct, @RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productSpecService.saveProductSpecFromJson(jsonProduct, image);
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @PutMapping("/{id}")
    public String updateProductSpec(@PathVariable int id, @RequestParam("jsonProduct") String jsonProduct, @RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productSpecService.updateProductSpecFromJson(id, jsonProduct, image);
            responseBody.put("success", true);
            responseBody.put("message", "更新成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "更新失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteProductSpec(@PathVariable int id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productSpecService.deleteProductSpec(id);
            responseBody.put("success", true);
            responseBody.put("message", "刪除成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "刪除失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @GetMapping("/{id}")
    public String getProductSpecById(@PathVariable int id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductSpec productSpec = productSpecService.getProductSpecById(id);
            responseBody.put("success", true);
            responseBody.put("list", new JSONObject(objectMapper.writeValueAsString(productSpec)));
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "查詢失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @GetMapping("/all")
    public String getAllProductSpecs() throws JSONException {
        JSONObject responseBody = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper(); // 用來解析 JSON 字符串
        try {
            List<ProductSpec> productSpecs = productSpecService.getAllProductSpecs();
            responseBody.put("success", true);
            responseBody.put("count", productSpecs.size());  // 添加產品數量
            
            JSONArray productSpecList = new JSONArray();  // 創建 JSON 陣列來存放產品資料
            for (ProductSpec productSpec : productSpecs) {
            	// 將 Products 對象轉換為 JSONObject
                JSONObject item = new JSONObject(objectMapper.writeValueAsString(productSpec));
                productSpecList.put(item);  // 將產品 JSON 對象添加到陣列中
            }
            
            responseBody.put("list", productSpecList);  // 將產品陣列添加到主體 JSON 對象中
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "查詢失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @DeleteMapping("/image/{id}")
    public String deleteProductSpecImage(@PathVariable int id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productSpecService.deleteProductSpecImage(id);
            responseBody.put("success", true);
            responseBody.put("message", "圖片刪除成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "圖片刪除失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }
}
