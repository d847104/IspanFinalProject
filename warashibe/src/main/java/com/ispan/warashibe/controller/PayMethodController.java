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
import com.ispan.warashibe.model.PayMethod;
import com.ispan.warashibe.service.PayMethodService;

@RestController
@CrossOrigin
public class PayMethodController {

	@Autowired
	private PayMethodService payMethodService;
	
	@Autowired
	private ObjectMapper objMapper;

	@PostMapping("/payMethod")
	public String create(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONObject obj = new JSONObject(body);
		Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
		if (payMethodID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (payMethodService.exists(payMethodID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID已存在");
			} else {
				PayMethod payMethod = payMethodService.create(body);
				if (payMethod == null) {
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

	@DeleteMapping("/payMethod/{payMethodID}")
	public String remove(@PathVariable Integer payMethodID) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (payMethodID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!payMethodService.exists(payMethodID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				if (!payMethodService.remove(payMethodID)) {
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

	@PutMapping("/payMethod/{payMethodID}")
	public String modify(@PathVariable Integer payMethodID, @RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (payMethodID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!payMethodService.exists(payMethodID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				PayMethod payMethod = payMethodService.modify(body);
				if (payMethod == null) {
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

	@PostMapping("/payMethod/find")
	public String find(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<PayMethod> payMethods = payMethodService.find(body);
		if (payMethods != null && !payMethods.isEmpty()) {
			for (PayMethod payMethod : payMethods) {
				JSONObject item = new JSONObject().put("payMethodID", payMethod.getPayMethodID()).put("payMethod",
						payMethod.getPayMethod());
				array = array.put(item);
			}
		}
		long count = payMethodService.count(body);
		responseBody.put("count", count);
		responseBody.put("list", array);

		return responseBody.toString();
	}
	
	@GetMapping("/payMethod/{payMethodID}")
	public String findById(@PathVariable Integer payMethodID) throws JSONException {
	    JSONObject responseBody = new JSONObject();
	    PayMethod payMethod = payMethodService.findById(payMethodID);

	    if (payMethod != null) {
	        JSONObject item = new JSONObject()
	                .put("payMethodID", payMethod.getPayMethodID())
	                .put("payMethod", payMethod.getPayMethod());
	        responseBody.put("payMethod", item);
	    } else {
	        responseBody.put("error", "PayMethod not found");
	    }
	    return responseBody.toString();
	}
	
	@GetMapping("/payMethod/all")
	public String findAll() throws JsonProcessingException, JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<PayMethod> payMethods = payMethodService.findAll();
		for(PayMethod payMethod : payMethods) {
			if(payMethod!=null) {
				array.put(new JSONObject(objMapper.writeValueAsString(payMethod)));
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
}
