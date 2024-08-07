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
import com.ispan.warashibe.model.SpecOnes;
import com.ispan.warashibe.service.SpecOneService;

@RestController
@RequestMapping("/api/spec")
@CrossOrigin
public class SpecOneController {
	@Autowired
	private SpecOneService specOneServ;
	@Autowired
	private ObjectMapper objMapper;
	
	@PostMapping("/one/create")	// 建立一筆
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecOnes specOne = specOneServ.create(body);
		return specOne != null ? responseBody
				.put("success", true)
				.put("message", "新增成功")
				.put("specOne", specOne.getSpecOneID())
				.toString()
				: responseBody.put("success", false).put("message", "新增失敗").toString();
	}

	@PutMapping("/one/modify")	// 修改一筆
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecOnes specOne = specOneServ.modify(body);
		if (specOne != null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		}
		return responseBody.put("success", false).put("message", "修改失敗").toString();
	}

	@GetMapping("/one/{id}")	// 查詢一筆
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		SpecOnes specOne = specOneServ.findById(id);
		if (specOne != null) {
			try {
				array.put(new JSONObject(objMapper.writeValueAsString(specOne)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}

	@DeleteMapping("/one/delete/{id}")	// 刪除一筆
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if (specOneServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		}
		return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}
}
