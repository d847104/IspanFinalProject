package com.ispan.warashibe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.SpecTwoNames;
import com.ispan.warashibe.repository.SpecTwoNameRepository;

@Service
public class SpecTwoNameService {
	@Autowired
	private SpecTwoNameRepository specTwoNameRepo;
	@Autowired
	private ObjectMapper objMapper;
	
	public SpecTwoNames create(String json) {	// 建立一筆
		try {
			SpecTwoNames specTwoName = objMapper.readValue(json, SpecTwoNames.class);
			if (specTwoName.getSpecTwoName() != null) {
				if (specTwoName.getSpecTwoNameID() != null
						&& specTwoNameRepo.findById(specTwoName.getSpecTwoNameID()).isPresent()) {
					return null;
				}
				return specTwoNameRepo.save(specTwoName);
			}
			return null;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public SpecTwoNames modify(String json) {	// 修改一筆
		try {
			SpecTwoNames specTwoName = objMapper.readValue(json, SpecTwoNames.class);
			if(specTwoName.getSpecTwoNameID() != null && specTwoName.getSpecTwoName()!=null) {
				if(specTwoNameRepo.findById(specTwoName.getSpecTwoNameID()).isPresent()) {
					return specTwoNameRepo.save(specTwoName);
				}
			} return null;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
	}
	
	public SpecTwoNames findById(Integer id) {	// 尋找一筆
		if(id!=null) {
			Optional<SpecTwoNames> specTwoName = specTwoNameRepo.findById(id);
			return specTwoName.isPresent()? specTwoName.get() : null;
		} return null;
	}
	
	public boolean deleteById(Integer id) {	// 刪除一筆
		if(id!=null) {
			Optional<SpecTwoNames> specTwoName = specTwoNameRepo.findById(id);
			if(specTwoName.isPresent()) {
				specTwoNameRepo.deleteById(id);
				return true;
			}
		} return false;
	}
}
