package com.ispan.warashibe.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.util.DatetimeConverter;

import jakarta.annotation.PostConstruct;

@Service
public class MemberService {

	@Autowired
	private MembersRepository membersRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	public boolean checkIfAccountExist(String account) {
		Members result = membersRepo.findByAccount(account);
		if(result != null) {
			return true;
		}
		return false;
	}
	
	//查詢單筆
	public Members findById(Integer id) {
		Optional<Members> optional = membersRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;	
	}
	
	//查詢全部
	public List<Members> findAllMem() {
		return membersRepo.findAll();
	}
	
	private byte[] img = null;
	@PostConstruct
	public void initialize() throws IOException {
		byte[] buffer = new byte[8192];
		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(
				classLoader.getResourceAsStream("static/images/default-person.png"));
		int len = is.read(buffer);
		while (len != -1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		this.img = os.toByteArray();
	}
	
	
	
	
	//新增單筆(註冊)
	public Members insert(String json) {
		JSONObject obj = new JSONObject(json);
		Integer memberID = obj.isNull("memberID") ? null : obj.getInt("memberID");
		String account = obj.isNull("account") ? null : obj.getString("account");
		String password = obj.isNull("password") ? null : obj.getString("password");
		password = pwdEncoder.encode(password);
		String username = obj.isNull("username") ? null : obj.getString("username");
		String mobile = obj.isNull("mobile") ? null : obj.getString("mobile");
		String gender = obj.isNull("gender") ? null : obj.getString("gender");
		byte[] profileImg = obj.isNull("profileImg") ? 
			    img : obj.getString("profileImg").getBytes();
		String intro = obj.isNull("intro") ? null : obj.getString("intro");
//		String createTime = obj.isNull("createTime") ? null : obj.getString("createTime");
//		String lastLogin = obj.isNull("lastLogin") ? null : obj.getString("lastLogin");
		String status = obj.isNull("status") ? null : obj.getString("status");
		
//		Optional<Members> optional = membersRepo.findById(memberID);
		if(memberID == null) {
			Members newMember = new Members();
			newMember.setAccount(account);
			newMember.setPassword(password);
			newMember.setUsername(username);
			newMember.setMobile(mobile);
			newMember.setGender(gender);
			newMember.setProfileImg(profileImg);
			newMember.setIntro(intro);
//			newMember.setCreateTime(null);
//			newMember.setLastLogin(DatetimeConverter.parse(lastLogin, "yyyy-MM-dd"));
			newMember.setStatus(status);
			return membersRepo.save(newMember);
		}
		return null;
	} // end of insert
	
	// 修改單筆
	public Members modify(String json, MultipartFile image) throws IOException {
		JSONObject obj = new JSONObject(json);

		Integer memberID = obj.isNull("id") ? null : obj.getInt("id");
		Optional<Members> optional = membersRepo.findById(memberID);
		
		String account = obj.isNull("account") ? optional.get().getAccount() : obj.getString("account");
		String password = obj.isNull("password") ? optional.get().getPassword() : obj.getString("password");
		String username = obj.isNull("username") ? optional.get().getUsername() : obj.getString("username");
		String mobile = obj.isNull("mobile") ? optional.get().getMobile() : obj.getString("mobile");
		String gender = obj.isNull("gender") ? optional.get().getGender() : obj.getString("gender");
//		byte[] profileImg = obj.isNull("profileImg") ? img : obj.getString("profileImg").getBytes();
		String intro = obj.isNull("intro") ? optional.get().getIntro() : obj.getString("intro");
		String createTime = obj.isNull("createTime") ? optional.get().getCreateTime().toString() : obj.getString("createTime");
		String lastLogin = obj.isNull("lastLogin") ? optional.get().getLastLogin().toString() : obj.getString("lastLogin");
		String status = obj.isNull("status") ? optional.get().getStatus() : obj.getString("status");

		if(optional.isPresent()) {
			Members update = optional.get();
			update.setAccount(account);
			update.setPassword(password);
			update.setUsername(username);
			update.setMobile(mobile);
			update.setGender(gender);
			if(image.isEmpty() && image !=null) {
				update.setProfileImg(img);
			}else {
				update.setProfileImg(image.getBytes());	
			}
			update.setIntro(intro);
//			update.setCreateTime(DatetimeConverter.parse(createTime, "yyyy-MM-dd"));
			update.setLastLogin(DatetimeConverter.parse(lastLogin, "yyyy-MM-dd"));
			update.setStatus(status);
			return membersRepo.save(update);
		}
		return null;
	}
	
	public boolean exists(Integer id) {
		if (id != null) {
			return membersRepo.existsById(id);
		}
		return false;
	}
	
	// 刪除單筆
	public Boolean deleteOne(Integer id) {
		if (id != null) {
			Optional<Members> optional = membersRepo.findById(id);
			if (optional.isPresent()) {
				membersRepo.deleteById(id);
				return true;
			}
		}
		return false;
	} // end of deleteOne

	
	// 登入
	public Members login(String account, String password) {	
		Members dbMem = membersRepo.findByAccount(account);
		if(dbMem == null) {
			return null;
		}
		boolean result  = pwdEncoder.matches(password, dbMem.getPassword());
		if(result) {
			return dbMem;
		}
		return null;
	}
	
	// 分頁功能
	public Page<Members> findByPage(Pageable pageable) {
		return membersRepo.findAll(pageable);
	}
	
	// 刪除全部[選填]	
	// 一鍵填入
	
	
}
