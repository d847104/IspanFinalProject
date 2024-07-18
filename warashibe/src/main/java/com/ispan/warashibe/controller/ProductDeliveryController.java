package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.ProductDelivery;
import com.ispan.warashibe.service.ProductDeliveryService;

@RestController
@CrossOrigin
public class ProductDeliveryController {
	@Autowired
	private ProductDeliveryService	productDeliveryService;
	
	@PostMapping("/productDelivery")
	public String create(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONObject obj = new JSONObject(body);
		Integer ID = obj.isNull("id") ? null : obj.getInt("id");
		if (ID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (productDeliveryService.exists(ID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID已存在");
			} else {
				productDeliveryService.create(body);
					responseBody.put("success", true);
					responseBody.put("message", "新增成功");
				}
			}
		return responseBody.toString();
	}
	
	@PutMapping("/productDelivery/{id}")
	public String modify(@PathVariable Integer id, @RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (id == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!productDeliveryService.exists(id)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				ProductDelivery productDelivery = productDeliveryService.modify(body);	
				if (productDelivery == null) {
					responseBody.put("success", false);
					responseBody.put("message", "修改失敗");
				} else {
					responseBody.put("success", true);
					responseBody.put("message", "修改成功");
				}
			}
		}
		return responseBody.toString();
	}
	
	@DeleteMapping("/productDelivery/{id}")
	public String remove(@PathVariable Integer id) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (id == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!productDeliveryService.exists(id)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				if (!productDeliveryService.remove(id)) {
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
	
	
	@PostMapping("/productDelivery/find")
	public String find(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<ProductDelivery> productDeliverys = productDeliveryService.find(body);
		if(productDeliverys !=null && !productDeliverys.isEmpty()) {
			for(ProductDelivery productDelivery:productDeliverys) {
				JSONObject item = new JSONObject()
						.put("id", productDelivery.getId())
						.put("deliveryID", productDelivery.getDelivery().getDeliveryID())
						.put("productID", productDelivery.getProductID());
				array.put(item);
			}
		}
		long count = productDeliveryService.count(body);
		responseBody.put("count", count);
        responseBody.put("list", array);

        return responseBody.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
