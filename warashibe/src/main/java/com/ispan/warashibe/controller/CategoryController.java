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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

//	  下面這功能ProductController有一樣的,我就不重覆放一樣的功能了,後續有特別需要Category做的事情再更新
//    @GetMapping("/category/{id}")
//    public String getProductsByCategory(@PathVariable int id, @RequestBody String request) throws JSONException, JsonProcessingException {
//    	SubCategory subCategory = categoryService.getSubCategoryById(id);
//        Pageable pageable = createPageRequest(new JSONObject(request));
//        List<Products> products = productService.getProductsBySubCategory(subCategory, pageable);
//        return createResponse(products);
//    }
//
    private Pageable createPageRequest(JSONObject request) throws JSONException {
        int start = request.optInt("start", 0);
        int max = request.optInt("max", 30);
        boolean dir = request.optBoolean("dir", false);
        String order = request.optString("order", "productID");

        // 根據 dir 參數選擇升序或降序排序
        Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC;

        // 創建 Pageable 對象
        return PageRequest.of(start, max, Sort.by(direction, order));
    }

    private String createResponse(List<Products> products) throws JSONException, JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        responseBody.put("count", products.size());  // 添加產品數量
        
        JSONArray productList = new JSONArray();  // 創建 JSON 陣列來存放產品資料
        for (Products product : products) {
            JSONObject item = new JSONObject(objectMapper.writeValueAsString(product));     
            productList.put(item);  // 將產品 JSON 對象添加到陣列中
        }
        
        responseBody.put("list", productList);  // 將產品陣列添加到主體 JSON 對象中
        
        return responseBody.toString();
    }
}
