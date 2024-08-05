package com.ispan.warashibe.controller;

import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.PageRequestDTO;
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
            // 將二進制圖片數據轉換為Base64編碼
            String base64Image = "";
            if (member.getProfileImg() != null) {
                base64Image = Base64.getEncoder().encodeToString(member.getProfileImg());
                System.out.println(base64Image);
            }
//            String make = DatetimeConverter.toString(product.getMake(), "yyyy-MM-dd");
            JSONObject item = new JSONObject()
                    .put("id", member.getMemberID())
                    .put("account", member.getAccount())
                    .put("password", member.getPassword())
                    .put("username", member.getUsername())
                    .put("mobile", member.getMobile())
                    .put("gender", member.getGender())
                    .put("profileImg", base64Image)
                    .put("intro", member.getIntro())
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
	

	
	
	//修改單筆會員資料 
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
    @DeleteMapping("/members/delete/{id}")
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
    
    @PostMapping("/members/paged")
	public Page<Members> findByPage(@RequestBody PageRequestDTO pageRequestDTO) {
        // 預設排序方向為 DESC
        Sort.Direction direction = Sort.Direction.DESC;
        if ("ASC".equalsIgnoreCase(pageRequestDTO.getSortDirection())) {
            direction = Sort.Direction.ASC;
        }
        // 根據排序屬性和升降冪
        Pageable pgb;
        
        if (pageRequestDTO.getSortBy() != null && !pageRequestDTO.getSortBy().isEmpty()) {
        	pgb = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), direction, pageRequestDTO.getSortBy());
        } else {
        	pgb = PageRequest.of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize());
        	
        }
        Page<Members> byPage = memberService.findByPage(pgb);

        // 總筆數
        long totalElements = byPage.getTotalElements(); 
        // 總頁數
        long totalPages = byPage.getTotalPages();
        
		return byPage;
	}
    
    
	
	
}
