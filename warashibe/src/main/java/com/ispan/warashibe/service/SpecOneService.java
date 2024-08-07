package com.ispan.warashibe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.SpecOnes;
import com.ispan.warashibe.repository.SpecOneRepository;

@Service
public class SpecOneService {
	@Autowired
	private SpecOneRepository specOneRepo;
	@Autowired
	private ObjectMapper objMapper;
	
	public SpecOnes create(String json) {	// 建立一筆
		try {
			SpecOnes specOne = objMapper.readValue(json, SpecOnes.class);
			if (specOne.getSpecOne() != null) {
				if (specOne.getSpecOneID() != null
						&& specOneRepo.findById(specOne.getSpecOneID()).isPresent()) {
					return null;
				}
				return specOneRepo.save(specOne);
			}
			return null;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public SpecOnes modify(String json) {	// 修改一筆
		try {
			SpecOnes specOne = objMapper.readValue(json, SpecOnes.class);
			if(specOne.getSpecOneID() != null && specOne.getSpecOne()!=null) {
				if(specOneRepo.findById(specOne.getSpecOneID()).isPresent()) {
					return specOneRepo.save(specOne);
				}
			} return null;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
	}
	
	public SpecOnes findById(Integer id) {	// 尋找一筆
		if(id!=null) {
			Optional<SpecOnes> specOne = specOneRepo.findById(id);
			return specOne.isPresent()? specOne.get() : null;
		} return null;
	}
	
	public boolean deleteById(Integer id) {	// 刪除一筆
		if(id!=null) {
			Optional<SpecOnes> specOne = specOneRepo.findById(id);
			if(specOne.isPresent()) {
				specOneRepo.deleteById(id);
				return true;
			}
		} return false;
	}
}
