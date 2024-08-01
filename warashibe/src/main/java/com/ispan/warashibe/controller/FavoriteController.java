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
import com.ispan.warashibe.model.FavoriteID;
import com.ispan.warashibe.service.FavoriteService;

@RestController
@RequestMapping("/ajax/")
@CrossOrigin
public class FavoriteController {
	
	@Autowired
	FavoriteService favoriteService;	

	@Autowired
	private ObjectMapper objectMapper;
	
	// 查詢單筆是否已收藏
	@GetMapping("/favorite/match")
	public boolean findById(@RequestBody String body) throws JsonProcessingException {
        JSONObject obj = new JSONObject(body);
        FavoriteID favId = obj.isNull("memberID") || obj.isNull("productID") ? 
        		null : new FavoriteID(obj.getInt("memberID"),obj.getInt("productID")); 
        if(favId != null) {
        	return favoriteService.exists(favId);
        }
        return false;
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
    @DeleteMapping("/favorite/delete")
    public String delete(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        FavoriteID favId = obj.isNull("memberID") || obj.isNull("productID") ? 
        		null : new FavoriteID(obj.getInt("memberID"),obj.getInt("productID"));
        if (favId == null) {
            responseBody.put("success", false);
            responseBody.put("message", "memberID 及 productID 是必要欄位");
        } else {
            if (!favoriteService.exists(favId)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!favoriteService.deleteOne(favId)) {
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
        FavoriteID favId = obj.isNull("memberID") || obj.isNull("productID") ? 
        		null : new FavoriteID(obj.getInt("memberID"),obj.getInt("productID"));

        if (favoriteService.exists(favId)) {
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
	
	
	// 以會員ID查詢多筆
	@GetMapping("/favorite/member/{id}")
	public String getMethodName(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<Favorite> result = favoriteService.findByMemberId(id);
		for(Favorite favorite : result) {
			if(favorite!=null) {
				try {array.put(new JSONObject(objectMapper.writeValueAsString(favorite)));}
				catch (Exception e) {e.printStackTrace();}
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
	
	// #######收藏商品需要修改嗎？
}
