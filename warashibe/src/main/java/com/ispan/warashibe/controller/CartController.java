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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Cart;
import com.ispan.warashibe.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {
	@Autowired
	private CartService cartServ;
	@Autowired
	private ObjectMapper objMapper;
	
	@PostMapping("/create")	// 新增一筆
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		Cart product = cartServ.create(body);
		return product!=null ?
				responseBody.put("success", true).put("message", "新增成功").toString() :
					responseBody.put("success", false).put("message", "新增失敗").toString();
	}
	
	@PutMapping("/modify")	// 修改一筆
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		Cart product = cartServ.modify(body);
		if(product!=null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		} return responseBody.put("success", false).put("message", "修改失敗").toString();
	}
	
	@GetMapping("/{id}")	// 查詢一筆
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		Cart product = cartServ.findById(id);
		if(product!=null) {
			try {array.put(new JSONObject(objMapper.writeValueAsString(product)));
			} catch (Exception e) {e.printStackTrace();}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
	
	@DeleteMapping("/delete/{id}")	// 刪除一筆
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if(cartServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		} return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}
	
	@GetMapping("/member/{id}")	// 以買家ID查詢多筆
	public String findByBuyerId(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<Cart> result = cartServ.findByMemberId(id);
		for(Cart product : result) {
			if(product!=null) {
				try {array.put(new JSONObject(objMapper.writeValueAsString(product)));
				} catch (Exception e) {e.printStackTrace();}
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
}
