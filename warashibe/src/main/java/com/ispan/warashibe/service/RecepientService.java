package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Recepient;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.repository.RecepientRepository;

@Service
public class RecepientService {

	@Autowired
	private MembersRepository membersRepo;
	@Autowired
	private RecepientRepository recepientRepo;
	
	//查詢單筆
	public Recepient findById(Integer id) {
		Optional<Recepient> optional = recepientRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;	
	}
	
	//查詢全部
	public List<Recepient> findAll() {
		return recepientRepo.findAll();
	}
	
	public boolean exists(Integer id) {
		if (id != null) {
			return recepientRepo.existsById(id);
		}
		return false;
	}
	// 刪除單筆
	public Boolean deleteOne(Integer id) {
		if (id != null) {
			Optional<Recepient> optional = recepientRepo.findById(id);
			if (optional.isPresent()) {
				recepientRepo.deleteById(id);
				return true;
			}
		}
		return false;
	} // end of deleteOne
	
	// 新增單筆
	public Recepient insert(String json) {
        JSONObject obj = new JSONObject(json);
		Integer recepientID = obj.isNull("recepientID") ? null : obj.getInt("recepientID");
		Integer memberID = obj.isNull("memberID") ? null : obj.getInt("memberID");
        String name = obj.isNull("name") ? null : obj.getString("name");
        String mobile = obj.isNull("mobile") ? null : obj.getString("mobile");
        String address = obj.isNull("address") ? null : obj.getString("address");
        
        Optional<Members> member = membersRepo.findById(memberID);
        
        if(recepientID == null) {
        	Recepient recepient = new Recepient();
        	recepient.setMemberID(member.get());
        	recepient.setName(name);
        	recepient.setMobile(mobile);
        	recepient.setAddress(address);
        	return recepientRepo.save(recepient);
        }
        return null;
        
	}
	
	// 修改單筆
	public Recepient modify(String json) {
		JSONObject obj = new JSONObject(json);
		Integer recepientID = obj.isNull("id") ? null : obj.getInt("id");
		Optional<Recepient> optional = recepientRepo.findById(recepientID);
		
		String name = obj.isNull("name") ? optional.get().getName() : obj.getString("name");
		String mobile = obj.isNull("mobile") ? optional.get().getMobile() : obj.getString("mobile");
		String address = obj.isNull("address") ? optional.get().getAddress() : obj.getString("address");

		if(optional.isPresent()) {
			Recepient update = optional.get();
			update.setName(name);
			update.setMobile(mobile);
			update.setAddress(address);
			return recepientRepo.save(update);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
