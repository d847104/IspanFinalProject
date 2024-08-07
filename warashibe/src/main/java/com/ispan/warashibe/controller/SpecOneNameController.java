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
import com.ispan.warashibe.model.SpecOneNames;
import com.ispan.warashibe.service.SpecOneNameService;

@RestController
@RequestMapping("/api/spec")
@CrossOrigin
public class SpecOneNameController {
	@Autowired
	private SpecOneNameService specOneNameServ;
	@Autowired
	private ObjectMapper objMapper;

	@PostMapping("/onename/create")	// 建立一筆
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecOneNames specOneName = specOneNameServ.create(body);
		return specOneName != null ? responseBody
				.put("success", true)
				.put("message", "新增成功")
				.put("specOneNameID", specOneName.getSpecOneNameID())
				.toString()
				: responseBody.put("success", false).put("message", "新增失敗").toString();
	}

	@PutMapping("/onename/modify")	// 修改一筆
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecOneNames specOneName = specOneNameServ.modify(body);
		if (specOneName != null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		}
		return responseBody.put("success", false).put("message", "修改失敗").toString();
	}

	@GetMapping("/onename/{id}")	// 查詢一筆
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		SpecOneNames specOneName = specOneNameServ.findById(id);
		if (specOneName != null) {
			try {
				array.put(new JSONObject(objMapper.writeValueAsString(specOneName)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}

	@DeleteMapping("/onename/delete/{id}")	// 刪除一筆
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if (specOneNameServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		}
		return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}

	@GetMapping("onename/product/{id}")	// 以產品ID查詢一筆
	public String findByBuyerId(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		SpecOneNames result = specOneNameServ.findByProdductId(id);
		if (result != null) {
			try {
				array.put(new JSONObject(objMapper.writeValueAsString(result)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}
}
