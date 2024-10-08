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
import com.ispan.warashibe.model.OrderProducts;
import com.ispan.warashibe.service.OrderProductsService;

@RestController
@RequestMapping("/private/pages")
@CrossOrigin
public class OrderProductsController {
	@Autowired
	private OrderProductsService orderProductsServ;
	@Autowired
	private ObjectMapper objMapper;
	
	@PostMapping("/orderProducts/create")	// 新增一筆
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		OrderProducts product = orderProductsServ.create(body);
		return product!=null ?
				responseBody.put("success", true).put("message", "新增成功").put("productName", product.getProductName()).toString() :
					responseBody.put("success", false).put("message", "新增失敗").toString();
	}
	
	@PutMapping("/orderProducts/modify")	// 修改一筆
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		OrderProducts product = orderProductsServ.modify(body);
		if(product!=null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		} return responseBody.put("success", false).put("message", "修改失敗").toString();
	}
	
	@GetMapping("/orderProducts/{id}")	// 查詢一筆
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		OrderProducts product = orderProductsServ.findById(id);
		if(product!=null) {
			try {array.put(new JSONObject(objMapper.writeValueAsString(product)));
			} catch (Exception e) {e.printStackTrace();}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
	
	@DeleteMapping("/orderProducts/delete/{id}")	// 刪除一筆
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if(orderProductsServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		} return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}
	
	@GetMapping("/orderProducts/order/{id}")	// 以訂單ID查詢多筆
	public String findByBuyerId(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<OrderProducts> result = orderProductsServ.findByOrderId(id);
		for(OrderProducts product : result) {
			if(product!=null) {
				try {array.put(new JSONObject(objMapper.writeValueAsString(product)));
				} catch (Exception e) {e.printStackTrace();}
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
}
