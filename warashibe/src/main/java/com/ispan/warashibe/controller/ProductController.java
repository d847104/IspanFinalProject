package com.ispan.warashibe.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.service.CategoryService;
import com.ispan.warashibe.service.ProductService;
import com.ispan.warashibe.util.JsonUtil;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @PostMapping("/search")
    public String searchProductsByName(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        JSONObject jsonObject = new JSONObject(request);
        String name = jsonObject.optString("name", ""); // 默認值為空字符串
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getProductsByName(name, pageable);
        return createResponse(products);
    }

    @PostMapping("/random")
    public String getRandomProducts(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getRandomProducts(pageable);
        return createResponse(products);
    }

    @PostMapping("/second-hand")
    public String getSecondHandProducts(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getSecondHandProducts(pageable);
        return createResponse(products);
    }

    @PostMapping("/popular")
    public String getPopularProducts(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        JSONObject jsonObject = new JSONObject(request);
        Pageable pageable = createPageRequest(jsonObject);
        List<Products> products = productService.getPopularProducts(pageable);
        return createResponse(products);
    }

    @PostMapping("/recommended")
    public String getRecommendedProducts(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
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

    @PostMapping("/category/{id}")
    public String getProductsByCategory(@PathVariable int id, @RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        SubCategory subCategory = categoryService.getSubCategoryById(id);
        Pageable pageable = createPageRequest(new JSONObject(request));
        List<Products> products = productService.getProductsBySubCategory(subCategory, pageable);
        return createResponse(products);
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id) throws JsonProcessingException, JSONException {
    	Products product = productService.getProductById(id);
    	String item = JsonUtil.toJson(product);
        return item;
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
    @Transactional
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
    @Transactional
    public String deleteProduct(@PathVariable int id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            Products productById = productService.getProductById(id);
            if (productById != null) {
                productService.deleteProduct(id);
                responseBody.put("success", true);
                responseBody.put("message", "刪除成功");
            } else {
                responseBody.put("success", false);
                responseBody.put("message", "查無產品");
            }
        } catch (DataIntegrityViolationException e) {
            responseBody.put("success", false);
            responseBody.put("message", "無法刪除此商品，因為它與其他數據存在關聯。");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "刪除失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }
    
    @GetMapping("/member/{memberID}")
    public String getProductsByMemberID(@PathVariable int memberID) throws JSONException, JsonProcessingException, ParseException {
        List<Products> products = productService.getProductsByMemberID(memberID);
        return createResponse(products);
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

    private String createResponse(List<Products> products) throws JSONException, JsonProcessingException, ParseException {
        JSONObject responseBody = new JSONObject();
        responseBody.put("count", products.size());  // 添加產品數量
        
        JSONArray productList = new JSONArray();  // 創建 JSON 陣列來存放產品資料
        for (Products product : products) {
            // 將 Products 對象轉換為 JSON 字符串
            String item = JsonUtil.toJson(product);
            // 將 JSON 字符串轉換為 JSONObject
            JSONObject jsonObject = JsonUtil.toJSONObject(item);
            productList.put(jsonObject);  // 將產品 JSON 對象添加到陣列中
        }
        
        responseBody.put("list", productList);  // 將產品陣列添加到主體 JSON 對象中
        return responseBody.toString()
        		.replace("\\", "")
        		.replace("\"[\"", "[")
        		.replace("}\",\"{", "},{")
        		.replace("\"]\"", "]"); // 防呆
    }
}
