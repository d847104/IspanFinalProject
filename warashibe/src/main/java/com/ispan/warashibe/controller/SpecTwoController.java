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
import com.ispan.warashibe.model.SpecTwos;
import com.ispan.warashibe.service.SpecTwoService;

@RestController
@RequestMapping("/api/spec")
@CrossOrigin
public class SpecTwoController {
	@Autowired
	private SpecTwoService specTwoServ;
	@Autowired
	private ObjectMapper objMapper;
	
	@PostMapping("/two/create")	// 建立一筆
	public String create(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecTwos specTwo = specTwoServ.create(body);
		return specTwo != null ? responseBody
				.put("success", true)
				.put("message", "新增成功")
				.put("specTwo", specTwo.getSpecTwoID())
				.toString()
				: responseBody.put("success", false).put("message", "新增失敗").toString();
	}

	@PutMapping("/two/modify")	// 修改一筆
	public String modify(@RequestBody String body) {
		JSONObject responseBody = new JSONObject();
		SpecTwos specTwo = specTwoServ.modify(body);
		if (specTwo != null) {
			return responseBody.put("success", true).put("message", "修改成功").toString();
		}
		return responseBody.put("success", false).put("message", "修改失敗").toString();
	}

	@GetMapping("/two/{id}")	// 查詢一筆
	public String findById(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		SpecTwos specTwo = specTwoServ.findById(id);
		if (specTwo != null) {
			try {
				array.put(new JSONObject(objMapper.writeValueAsString(specTwo)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}

	@DeleteMapping("/two/delete/{id}")	// 刪除一筆
	public String delete(@PathVariable Integer id) {
		JSONObject responseBody = new JSONObject();
		if (specTwoServ.deleteById(id)) {
			return responseBody.put("success", true).put("message", "刪除成功").toString();
		}
		return responseBody.put("success", false).put("message", "刪除失敗").toString();
	}
}
