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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Notification;
import com.ispan.warashibe.model.Recepient;
import com.ispan.warashibe.service.NotificationService;

@RestController
@RequestMapping("/ajax/")
@CrossOrigin
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private ObjectMapper objectMapper;
	
	// 查詢單筆
	@GetMapping("/notification/{id}")
	public String findById(@PathVariable(name = "id")Integer id) throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Notification notification = notificationService.findById(id);
		
        if(notification != null) {
    			array = array.put(new JSONObject(objectMapper.writeValueAsString(notification)));
//    								.put("memberID", notification.getMemberID().getMemberID()));
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 查詢全部
	@GetMapping("/notification/findAll")
	public String findAll() throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Notification> allNotification = notificationService.findAll();
        if(allNotification != null) {
        	for(Notification notification : allNotification) {
        		JSONObject item = new JSONObject(objectMapper.writeValueAsString(notification));
     
        		array.put(item);
        	}
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}
	
	// 根據 receiverID 查詢通知
    @GetMapping("/notification/receiver/{receiverID}")
    public String findByReceiverID(@PathVariable(name = "receiverID") Integer receiverID) throws JsonProcessingException {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Notification> notifications = notificationService.findByReceiverID(receiverID);
        
        for (Notification notification : notifications) {
            JSONObject item = new JSONObject(objectMapper.writeValueAsString(notification));
            array.put(item);
        }

        responseBody.put("list", array);
        return responseBody.toString();
    }
	
	// 刪除單筆
    @DeleteMapping("/notification/delete/{id}")
    public String delete(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!notificationService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!notificationService.deleteOne(id)) {
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
	
	//新增
	@PostMapping("/notification/insert")
	public String insert(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Integer id = obj.isNull("id") ? null : obj.getInt("id");

        if (notificationService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id已存在");
        } else {
        	Notification notification = notificationService.insert(body);
            if (notification == null) {
                responseBody.put("success", false);
                responseBody.put("message", "新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "新增成功");
            }
        }
        return responseBody.toString();
	} // end of insert
	
	// 通知小鈴鐺需要修改嗎？？
	//修改單筆資料 
    @PutMapping("/notification/update/{id}")
    public String modify(@PathVariable(name = "id") Integer id, @RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!notificationService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
            	Notification notification = notificationService.modify(body);
                if (notification == null) {
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
	
	
	
	
	
	
}
