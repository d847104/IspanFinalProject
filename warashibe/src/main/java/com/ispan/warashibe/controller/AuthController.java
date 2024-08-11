package com.ispan.warashibe.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.service.MailService;
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
    @Autowired
    private MailService mailService;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
	private MembersRepository membersRepo;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
	
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
            responseJson.put("username", member.getUsername());
            
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
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestBody String body, HttpSession session) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        String account = obj.getString("account");
        if(memberService.checkIfAccountExist(account)) {
            // 生成驗證連結		
            String verificationLink = UUID.randomUUID().toString();
            // 生成驗證碼
            String verificationCode = generateVerificationCode();
            // 儲存驗證碼到內存中
            verificationCodes.put(account, verificationCode);
            // 發送郵件
            String resetLink = "http://localhost:5173/secure/resetPwd?account=" + account + "&Link=" + verificationLink;
        	mailService.sendPlainText(account, "您的驗證碼是："+ verificationCode, "請點擊連結以重設密碼： " + resetLink);
        	session.setAttribute("account", account);
        	session.setAttribute("verificationCode", verificationCode);
            responseBody.put("success", true);
            responseBody.put("message", "已發送重設密碼連結信");
        }else {
            responseBody.put("success", false);
            responseBody.put("message", "帳號未註冊，請前往註冊");
        }
        return responseBody.toString();
	}
	
    private String generateVerificationCode() {
        // 生成六位數驗證碼的邏輯
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        String account = obj.getString("account");
        String code = obj.getString("code");
        String newPassword = obj.getString("newPassword");

        if(verificationCodes.containsKey(account) && verificationCodes.get(account).equals(code)) {
        	Members member = memberService.findByAccount(account);
        	newPassword = pwdEncoder.encode(newPassword);
        	member.setPassword(newPassword);
        	membersRepo.save(member);
        	
        	verificationCodes.remove(account);
        	responseBody.put("success", true);
        	responseBody.put("message", "密碼成功更新");
        } else {
        	responseBody.put("success", false);
        	responseBody.put("message", "密碼更新失敗");
        }
        return responseBody.toString();
	}
	
	@PostMapping("/modifyPwd")
	public String modifyPwd(@RequestBody String body) {
        JSONObject responseBody = new JSONObject();
        JSONObject obj = new JSONObject(body);
        String account = obj.getString("account");
        String oldPassword = obj.getString("oldPassword");
        String newPassword = obj.getString("newPassword");
        // 驗證使用者輸入的資料
        if (account == null || account.length() == 0 || oldPassword == null || oldPassword.length() == 0) {
        	responseBody.put("message", "請輸入帳號密碼");
        	responseBody.put("success", false);
            return responseBody.toString();
        }else {
            // 呼叫企業邏輯
            Members member = memberService.login(account, oldPassword);
            if(member != null) {
            	newPassword = pwdEncoder.encode(newPassword);
            	member.setPassword(newPassword);
            	membersRepo.save(member);
            	responseBody.put("success", true);
            	responseBody.put("message", "密碼成功更新");
            } else {
            	responseBody.put("success", false);
            	responseBody.put("message", "密碼更新失敗");
            }
        }

        
        return responseBody.toString();
	}
	
	
	
	

}
