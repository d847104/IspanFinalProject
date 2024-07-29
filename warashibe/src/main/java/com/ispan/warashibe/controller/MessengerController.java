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

import com.ispan.warashibe.model.Messenger;
import com.ispan.warashibe.service.MessengerService;

@RestController
@CrossOrigin
public class MessengerController {

	@Autowired
	private MessengerService messengerService;

	@PostMapping("/messenger")
	public String create(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONObject obj = new JSONObject(body);
		String msg = obj.isNull("msg") ? null : obj.getString("msg");
		if (msg == null || msg.length() == 0) {
			responseBody.put("success", false);
			responseBody.put("message", "未輸入留言內容");
		} else {
			Messenger messengers = messengerService.create(body);
			responseBody.put("success", true);
			responseBody.put("message", "留言成功");
		}
		return responseBody.toString();
	}

	@DeleteMapping("/messenger/{msgID}")
	public String remove(@PathVariable Integer msgID) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (msgID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!messengerService.exists(msgID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				if (!messengerService.remove(msgID)) {
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
	
	@PutMapping("/messenger/{msgID}")
	public String modify(@PathVariable Integer msgID, @RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (msgID == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!messengerService.exists(msgID)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				Messenger messenger = messengerService.modify(body);
				if (messenger==null) {
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
	
	@PostMapping("/messenger/find")
	public String find(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<Messenger> messengers = messengerService.find(body);
		if(messengers !=null && !messengers.isEmpty()) {
			for(Messenger messenger:messengers) {
				JSONObject item = new JSONObject()
						.put("msgID", messenger.getMsgID())
						.put("senderID", messenger.getSenderID())
						.put("receiverID", messenger.getReceiverID())
						.put("msg", messenger.getMsg())
						.put("msgTime", messenger.getMsgTime());
				array = array.put(item);
			}
		}
		long count = messengerService.count(body);
		responseBody.put("count", count);
        responseBody.put("list", array);

        return responseBody.toString();
	}	
	
	@GetMapping("/messenger/{msgID}")
	public String findById(@PathVariable Integer msgID) throws JSONException {
	    JSONObject responseBody = new JSONObject();
	    Messenger messenger = messengerService.findById(msgID);

	    if (messenger != null) {
	        JSONObject item = new JSONObject()
	                .put("msgID", messenger.getMsgID())
	                .put("senderID", messenger.getSenderID())
	                .put("receiverID", messenger.getReceiverID())
	                .put("msg", messenger.getMsg())
	                .put("msgTime", messenger.getMsgTime());
	        responseBody.put("messenger", item);
	    } else {
	        responseBody.put("error", "messenger not found");
	    }
	    return responseBody.toString();
	}
	
}
