package com.ispan.warashibe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String getRandomProducts(@RequestBody String request) throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getRandomProducts(pageable);
        return createResponse(products);
    }

    @GetMapping("/second-hand")
    public String getSecondHandProducts(@RequestBody String request) throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getSecondHandProducts(pageable);
        return createResponse(products);
    }

    @GetMapping("/popular")
    public String getPopularProducts(@RequestBody String request) throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getPopularProducts(pageable);
        return createResponse(products);
    }

    @GetMapping("/recommended")
    public String getRecommendedProducts(@RequestBody String request) throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(request);
        JSONArray categoryIdsArray = jsonObject.getJSONArray("categoryIds");
        List<Integer> categoryIds = new ArrayList<>();
        for (int i = 0; i < categoryIdsArray.length(); i++) {
            categoryIds.add(categoryIdsArray.getInt(i));
        }

        List<SubCategory> categories = categoryService.getSubCategoriesByIds(categoryIds);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getRecommendedProducts(categories, pageable);
        return createResponse(products);
    }

    @GetMapping("/category/{id}")
    public String getProductsByCategory(@PathVariable int id, @RequestBody String request) throws JSONException, JsonProcessingException {
        SubCategory subCategory = categoryService.getSubCategoryById(id);
        Pageable pageable = createPageRequest(new JSONObject(request));
        List<Products> products = productService.getProductsBySubCategory(subCategory, pageable);
        return createResponse(products);
    }

    @GetMapping("/{id}")
    public Products getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public String saveProduct(@RequestBody String jsonProduct) throws JsonProcessingException, JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productService.saveProductFromJson(jsonProduct);
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody String jsonProduct) throws JsonProcessingException, JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productService.updateProductFromJson(id, jsonProduct);
            responseBody.put("success", true);
            responseBody.put("message", "修改成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "修改失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            productService.deleteProduct(id);
            responseBody.put("success", true);
            responseBody.put("message", "刪除成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "刪除失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    private Pageable createPageRequest(JSONObject request) throws JSONException {
        int start = request.optInt("start", 0); // 默認值為0
        int max = request.optInt("max", 30); // 默認值為30
        boolean dir = request.optBoolean("dir", false); // 默認值為false
        String order = request.optString("order", "productID"); // 默認排序字段為productID

        // 根據 dir 參數選擇升序或降序排序
        Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC;

        // 創建 Pageable 對象
        return PageRequest.of(start, max, Sort.by(direction, order));
    }

    private String createResponse(List<Products> products) throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(); // 用來解析 JSON 字符串
        JSONObject responseBody = new JSONObject();
        responseBody.put("count", products.size());  // 添加產品數量
        
        JSONArray productList = new JSONArray();  // 創建 JSON 陣列來存放產品資料
        for (Products product : products) {
        	// 將 Products 對象轉換為 JSONObject
            JSONObject item = new JSONObject(objectMapper.writeValueAsString(product));
            
            // 移除多餘屬性，添加更多屬性，根據需要
            item.remove("member");
            item.remove("subCategory");
            item.put("memberID", product.getMember().getMemberID());
            item.put("subCategoryID", product.getSubCategory().getSubCategoryID());
            productList.put(item);  // 將產品 JSON 對象添加到陣列中
        }
        
        responseBody.put("list", productList);  // 將產品陣列添加到主體 JSON 對象中
        
        return responseBody.toString();
    }
}
