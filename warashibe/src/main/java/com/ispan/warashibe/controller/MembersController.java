package com.ispan.warashibe.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.service.MemberService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/ajax/")
@CrossOrigin
public class MembersController {

	@Autowired
	private MemberService memberService;
	
	// 單筆會員查詢
	@GetMapping("/members/{id}")
	public String findById(@PathVariable(name = "id")Integer id) {
        System.out.println(id);
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        Members member = memberService.findById(id);
        if (member != null) {
//            String make = DatetimeConverter.toString(product.getMake(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", member.getMemberID())
                    .put("account", member.getAccount())
                    .put("password", member.getPassword())
                    .put("username", member.getUsername())
                    .put("mobile", member.getMobile())
                    .put("gender", member.getGender())
                    .put("profileImg", member.getProfileImg())
                    .put("into", member.getIntro())
                    .put("createTime", member.getCreateTime())
                    .put("lastLogin", member.getLastLogin())
                    .put("status", member.getStatus());
            array = array.put(item);
        }
        responseBody.put("list", array);
        return responseBody.toString();
	}// end of findById
	
	//查詢全部會員
	@GetMapping("/members/findAll")
	public String findAll() {
        JSONObject responseBody = new JSONObject();
        JSONArray array = new JSONArray();
        List<Members> allMem = memberService.findAllMem();
		if(allMem != null && !allMem.isEmpty()) {
			for (Members member : allMem) {
	            JSONObject item = new JSONObject()
	                    .put("id", member.getMemberID())
	                    .put("account", member.getAccount())
	                    .put("password", member.getPassword())
	                    .put("username", member.getUsername())
	                    .put("mobile", member.getMobile())
	                    .put("gender", member.getGender())
	                    .put("profileImg", member.getProfileImg())
	                    .put("into", member.getIntro())
	                    .put("createTime", member.getCreateTime())
	                    .put("lastLogin", member.getLastLogin())
	                    .put("status", member.getStatus());
	            array = array.put(item);
			}
		}
        responseBody.put("list", array);
        return responseBody.toString();
	} // end of findAll
	
	//新增單筆
	@PostMapping("/members")
	public String insert(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        Integer id = obj.isNull("id") ? null : obj.getInt("id");

        if (memberService.exists(id)) {
            responseBody.put("success", false);
            responseBody.put("message", "Id已存在");
        } else {
            Members newMember = memberService.insert(body);
            if (newMember == null) {
                responseBody.put("success", false);
                responseBody.put("message", "新增失敗");
            } else {
                responseBody.put("success", true);
                responseBody.put("message", "新增成功");
            }
        }
        
        return responseBody.toString();
	} // end of insert
	
	//修改單筆 
    @PutMapping("/members/update/{id}")
    public String modify(@PathVariable(name = "id") Integer id, @RequestBody String body) {
    	
    	
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!memberService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                Members member = memberService.modify(body);
                if (member == null) {
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
	
	// 刪除單筆
    @DeleteMapping("/members/{id}")
    public String delete(@PathVariable Integer id) {
        JSONObject responseBody = new JSONObject();
        if (id == null) {
            responseBody.put("success", false);
            responseBody.put("message", "Id是必要欄位");
        } else {
            if (!memberService.exists(id)) {
                responseBody.put("success", false);
                responseBody.put("message", "Id不存在");
            } else {
                if (!memberService.deleteOne(id)) {
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
