package com.ispan.warashibe.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Recepient;
import com.ispan.warashibe.service.RecepientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/ajax/")
@CrossOrigin
public class RecepientController {

	@Autowired
	private RecepientService recepientService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	// 查詢單筆
	@GetMapping("/recepient/{id}")
	public String findById(@PathVariable(name = "id")Integer id) throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Recepient recepient = recepientService.findById(id);
		
        if(recepient != null) {
    			array = array.put(objectMapper.writeValueAsString(recepient));
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 查詢全部
	@GetMapping("/recepient/findAll")
	public String findAll() throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Recepient> allRecepient = recepientService.findAll();
        if(allRecepient != null) {
    			array = array.put(objectMapper.writeValueAsString(allRecepient));
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 刪除單筆
    @DeleteMapping("/recepient/delete/{id}")
    public String delete(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!recepientService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!recepientService.deleteOne(id)) {
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
}
