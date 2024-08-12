package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Delivery;
import com.ispan.warashibe.service.DeliveryService;



@RestController
//@RequestMapping("/warashibe")
@CrossOrigin
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping("/delivery")
	public String create(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONObject obj = new JSONObject(body);
		Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
		if (deliveryID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (deliveryService.exists(deliveryID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID已存在");
			} else {
				Delivery delivery = deliveryService.create(body);
				if (delivery == null) {
					responseBody.put("success", false);
					responseBody.put("message", "新增失敗");
				} else {
					responseBody.put("success", true);
					responseBody.put("message", "新增成功");
				}
			}
		}
		return responseBody.toString();
	}

	@DeleteMapping("/delivery/{deliveryID}")
	public String remove(@PathVariable Integer deliveryID) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (deliveryID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!deliveryService.exists(deliveryID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				if (!deliveryService.remove(deliveryID)) {
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
	
	@GetMapping("/delivery/{deliveryID}")
	public String findById(@PathVariable Integer deliveryID) throws JSONException {
	    JSONObject responseBody = new JSONObject();
	    Delivery delivery = deliveryService.findById(deliveryID);

	    if (delivery != null) {
	        JSONObject item = new JSONObject()
	                .put("deliveryID", delivery.getDeliveryID())
	                .put("delivery", delivery.getDelivery())
	                .put("deliveryFee", delivery.getDeliveryFee());
	        responseBody.put("delivery", item);
	    } else {
	        responseBody.put("error", "Delivery not found");
	    }
	    return responseBody.toString();
	}
	
	@PutMapping("/delivery/{deliveryID}")
	public String modify(@PathVariable Integer deliveryID, @RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (deliveryID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!deliveryService.exists(deliveryID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				Delivery delivery = deliveryService.modify(body);
				if (delivery==null) {
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

	@PostMapping("/delivery/find")
	public String find(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<Delivery> deliverys = deliveryService.find(body);
		if(deliverys !=null && !deliverys.isEmpty()) {
			for(Delivery delivery:deliverys) {
				JSONObject item = new JSONObject()
						.put("deliveryID", delivery.getDeliveryID())
						.put("delivery", delivery.getDelivery())
						.put("deliveryFee", delivery.getDeliveryFee());
				array = array.put(item);
			}
		}
		long count = deliveryService.count(body);
		responseBody.put("count", count);
        responseBody.put("list", array);

        return responseBody.toString();
	}
	
	@GetMapping("/delivery/all")
	public String findAll() throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<Delivery> deliveries = deliveryService.findAll();
		if(deliveries!=null && !deliveries.isEmpty()) {
			for(Delivery delivery : deliveries) {
				JSONObject item = new JSONObject()
						.put("deliveryID", delivery.getDeliveryID())
						.put("delivery", delivery.getDelivery())
						.put("deliveryFee", delivery.getDeliveryFee());
				array = array.put(item);
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
	
}
