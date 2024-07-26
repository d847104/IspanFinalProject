package com.ispan.warashibe.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.service.MemberService;
import com.ispan.warashibe.util.JsonWebTokenUtility;

import jakarta.servlet.http.HttpSession;

/*
 * 登入功能
 * 
 * 
 * */


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	private MemberService memberService;
	
    @Autowired
    private JsonWebTokenUtility jsonWebTokenUtility;
    
//    @Autowired
//    private TokenBlacklistService tokenBlacklistService;
	
	
	//登入功能
	@PostMapping("/login")
	public String login(HttpSession httpSession, @RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        // 接收使用者輸入的資料
        JSONObject obj = new JSONObject(json);
        String account = obj.isNull("account") ? null : obj.getString("account");
        String password = obj.isNull("password") ? null : obj.getString("password");

        // 驗證使用者輸入的資料
        if (account == null || account.length() == 0 || password == null || password.length() == 0) {
            responseJson.put("message", "請輸入帳號密碼");
            responseJson.put("success", false);
            return responseJson.toString();
        }
        
        // 呼叫企業邏輯
        Members member = memberService.login(account, password);
		
        if (member == null) {
            responseJson.put("message", "登入失敗");
            responseJson.put("success", false);
        } else {
            httpSession.setAttribute("user", member);
            responseJson.put("message", "登入成功");
            responseJson.put("success", true);
            
            JSONObject user = new JSONObject()
            				.put("memberID", member.getMemberID())
            				.put("account", member.getAccount())
            				.put("username", member.getUsername());
            
            String token = jsonWebTokenUtility.createEncryptedToken(user.toString(), null);
            responseJson.put("token", token);
            responseJson.put("memberID", member.getMemberID());
            responseJson.put("account", member.getAccount());
            
        }
        return responseJson.toString();
        
	}
	
	//新增會員(註冊)
	@PostMapping("/insert")
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
	
//	@PostMapping("/logout")
//	public ResponseEntity<?> logout(@RequestHeader("Authorization") String tokenHeader) {
//        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//            String jwtToken = tokenHeader.substring(7);
//            tokenBlacklistService.addToBlacklist(jwtToken);
//        }
//
//        SecurityContextHolder.clearContext();
//        return ResponseEntity.ok("Successfully logged out");
//	}
}
