package com.ispan.warashibe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.SpecTwos;
import com.ispan.warashibe.repository.SpecTwoRepository;

@Service
public class SpecTwoService {
	@Autowired
	private SpecTwoRepository specTwoRepo;
	@Autowired
	private ObjectMapper objMapper;
	
	public SpecTwos create(String json) {	// 建立一筆
		try {
			SpecTwos specTwo = objMapper.readValue(json, SpecTwos.class);
			if (specTwo.getSpecTwo() != null) {
				if (specTwo.getSpecTwoID() != null
						&& specTwoRepo.findById(specTwo.getSpecTwoID()).isPresent()) {
					return null;
				}
				return specTwoRepo.save(specTwo);
			}
			return null;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public SpecTwos modify(String json) {	// 修改一筆
		try {
			SpecTwos specTwo = objMapper.readValue(json, SpecTwos.class);
			if(specTwo.getSpecTwoID() != null && specTwo.getSpecTwo()!=null) {
				if(specTwoRepo.findById(specTwo.getSpecTwoID()).isPresent()) {
					return specTwoRepo.save(specTwo);
				}
			} return null;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
	}
	
	public SpecTwos findById(Integer id) {	// 尋找一筆
		if(id!=null) {
			Optional<SpecTwos> specTwo = specTwoRepo.findById(id);
			return specTwo.isPresent()? specTwo.get() : null;
		} return null;
	}
	
	public boolean deleteById(Integer id) {	// 刪除一筆
		if(id!=null) {
			Optional<SpecTwos> specTwo = specTwoRepo.findById(id);
			if(specTwo.isPresent()) {
				specTwoRepo.deleteById(id);
				return true;
			}
		} return false;
	}
}
