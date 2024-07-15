package com.ispan.warashibe.controller;

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
import com.ispan.warashibe.model.Orders;
import com.ispan.warashibe.service.OrdersService;

@RestController
@RequestMapping("/private/pages")
@CrossOrigin
public class OrdersController {
	@Autowired
	private OrdersService ordersServ;
	@Autowired
	private ObjectMapper objMapper;
	
	@GetMapping("/orders/{id}")
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		Orders order = ordersServ.findById(id);
		if(order!=null) {
			try {
				array = array.put(objMapper.writeValueAsString(order));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
	
	@PostMapping("/orders/create")
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		Orders order = ordersServ.create(body);
		if(order!=null) {
			return responseBody.put("success", true).put("message", "新增成功").toString();
		}
		return responseBody.put("success", false).put("message", "新增失敗").toString();
	}
	
	@PutMapping("/orders/modify")
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		Orders order = ordersServ.modify(body);
		if(order!=null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		}
		return responseBody.put("success", false).put("message", "修改失敗").toString();
	}
	
	@DeleteMapping("/orders/delete/{id}")
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if(ordersServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		}
		return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}
}
