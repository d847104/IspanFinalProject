package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.util.DatetimeConverter;

@Service
public class MemberService {

	@Autowired
	private MembersRepository membersRepo;
	
	//查詢單筆
	public Members findById(Integer id) {
		Optional<Members> optional = membersRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;	
	}
	
	//查詢多筆
	public List<Members> findAllMem() {
		return membersRepo.findAll();
	}
	
	//新增單筆
	public Members insert(String json) {
		JSONObject obj = new JSONObject(json);
		Integer memberID = obj.isNull("memberID") ? null : obj.getInt("memberID");
		String account = obj.isNull("account") ? null : obj.getString("account");
		String password = obj.isNull("password") ? null : obj.getString("password");
		String username = obj.isNull("username") ? null : obj.getString("username");
		String mobile = obj.isNull("mobile") ? null : obj.getString("mobile");
		String gender = obj.isNull("gender") ? null : obj.getString("gender");
		String profileImg = obj.isNull("profileImg") ? "./images/default-person.png" : obj.getString("profileImg");
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
			newMember.setProfileImg(profileImg.getBytes());
			newMember.setIntro(intro);
//			newMember.setCreateTime(null);
//			newMember.setLastLogin(DatetimeConverter.parse(lastLogin, "yyyy-MM-dd"));
			newMember.setStatus(status);
			return membersRepo.save(newMember);
		}
		return null;
	} // end of insert
	
	// 修改單筆
	public Members modify(String json) {
		JSONObject obj = new JSONObject(json);

		Integer memberID = obj.isNull("id") ? null : obj.getInt("id");
		Optional<Members> optional = membersRepo.findById(memberID);
		
		String account = obj.isNull("account") ? optional.get().getAccount() : obj.getString("account");
		String password = obj.isNull("password") ? optional.get().getPassword() : obj.getString("password");
		String username = obj.isNull("username") ? optional.get().getUsername() : obj.getString("username");
		String mobile = obj.isNull("mobile") ? optional.get().getMobile() : obj.getString("mobile");
		String gender = obj.isNull("gender") ? optional.get().getGender() : obj.getString("gender");
		String profileImg = obj.isNull("profileImg") ? "./images/default-person.png" : obj.getString("profileImg");
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
			update.setProfileImg(profileImg.getBytes());
			update.setIntro(intro);
			update.setCreateTime(DatetimeConverter.parse(createTime, "yyyy-MM-dd"));
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
	@DeleteMapping
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

	// 刪除全部[選填]	
	// 一鍵填入
	
	
}
