package com.ispan.warashibe.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Favorite;
import com.ispan.warashibe.service.FavoriteService;

@RestController
@RequestMapping("/ajax/")
@CrossOrigin
public class FavoriteController {
	
	@Autowired
	FavoriteService favoriteService;	

	@Autowired
	private ObjectMapper objectMapper;
	
	// 查詢單筆
	@GetMapping("/favorite/{id}")
	public String findById(@PathVariable(name = "id")Integer id) throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Favorite favorite = favoriteService.findById(id);
        if(favorite != null) {
    			array = array.put(new JSONObject(objectMapper.writeValueAsString(favorite)));
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 查詢全部
	@GetMapping("/favorite/findAll")
	public String findAll() throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Favorite> allFavorite = favoriteService.findAll();
        if(allFavorite != null) {
        	for(Favorite favorite : allFavorite) {
        		JSONObject item = new JSONObject(objectMapper.writeValueAsString(favorite));
        		array.put(item);
        	}
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 刪除單筆
    @DeleteMapping("/favorite/delete/{id}")
    public String delete(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!favoriteService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!favoriteService.deleteOne(id)) {
                    responseBody.put("success", false);
                    responseBody.put("message", "刪除失敗");
                } else {
                    responseBody.put("success", true);
                    responseBody.put("message", "刪除成功");
                }
            }
        }
        return responseBody.toString();
    }

	
	//新增
	@PostMapping("/favorite/insert")
	public String insert(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Integer id = obj.isNull("id") ? null : obj.getInt("id");

        if (favoriteService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id已存在");
        } else {
        	Favorite favorite = favoriteService.insert(body);
            if (favorite == null) {
                responseBody.put("success", false);
                responseBody.put("message", "新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "新增成功");
            }
        }
        return responseBody.toString();
	} // end of insert
	
	// #######收藏商品需要修改嗎？
}
