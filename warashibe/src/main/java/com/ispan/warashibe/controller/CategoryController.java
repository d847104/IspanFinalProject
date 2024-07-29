package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.MainCategory;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.service.CategoryService;
import com.ispan.warashibe.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objMapper;

//	  下面這功能ProductController有一樣的,我就不重覆放一樣的功能了,後續有特別需要Category做的事情再更新
//    @GetMapping("/category/{id}")
//    public String getProductsByCategory(@PathVariable int id, @RequestBody String request) throws JSONException, JsonProcessingException {
//    	SubCategory subCategory = categoryService.getSubCategoryById(id);
//        Pageable pageable = createPageRequest(new JSONObject(request));
//        List<Products> products = productService.getProductsBySubCategory(subCategory, pageable);
//        return createResponse(products);
//    }
//
    @GetMapping("/mainCategory/all")
    public String getMainCategory() {
    	JSONObject responseBody = new JSONObject();
    	List<MainCategory> mainCategories = categoryService.getAllMainCategories();
    	try {responseBody.put("list", new JSONArray(objMapper.writeValueAsString(mainCategories)));}
    	catch (Exception e) {e.printStackTrace();}
        return responseBody.toString();
    }
    
    @GetMapping("/subCategory/all")
    public String getSubCategory() {
    	JSONObject responseBody = new JSONObject();
    	List<SubCategory> subCategories = categoryService.getAllSubCategories();
    	try {responseBody.put("list", new JSONArray(objMapper.writeValueAsString(subCategories)));}
    	catch (Exception e) {e.printStackTrace();}
        return responseBody.toString();
    }
    
    
    @GetMapping("/mainCategory/{id}")
    public String getSubCategoryByMain(@PathVariable Integer id) {
    	JSONObject responseBody = new JSONObject();
    	JSONArray array = new JSONArray();
    	List<SubCategory> subCategories = categoryService.getSubCategoriesByMain(id);
    	for(SubCategory category : subCategories) {
    		if(category!=null) {
    			try {array.put(new JSONObject(objMapper.writeValueAsString(category)));}
    			catch (Exception e) {e.printStackTrace();}
    		}
    	}
    	try {responseBody.put("list", array);} catch (Exception e) {e.printStackTrace();}
        return responseBody.toString();
    }
    
    @PostMapping("/mainCategory/create")
    public String createMainCategory(@RequestBody String body) throws JSONException {
    	JSONObject responseBody = new JSONObject();
		MainCategory mainCategory = categoryService.createMainCategory(body);
		return mainCategory!=null ?
				responseBody.put("success", true).put("message", "新增成功").toString() :
					responseBody.put("success", false).put("message", "新增失敗").toString();
	}
    
    @PostMapping("/subCategory/create")
    public String subMainCategory(@RequestBody String body) throws JSONException {
    	JSONObject responseBody = new JSONObject();
		SubCategory subCategory = categoryService.createSubCategory(body);
		return subCategory!=null ?
				responseBody.put("success", true).put("message", "新增成功").toString() :
					responseBody.put("success", false).put("message", "新增失敗").toString();
	}
    
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
