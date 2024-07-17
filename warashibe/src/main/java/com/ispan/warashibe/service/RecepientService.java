package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ispan.warashibe.model.Recepient;
import com.ispan.warashibe.repository.RecepientRepository;

@Service
public class RecepientService {

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
	@DeleteMapping
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
	
	//新增單筆(註冊)
//	public Recepient insert(String json) {
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
