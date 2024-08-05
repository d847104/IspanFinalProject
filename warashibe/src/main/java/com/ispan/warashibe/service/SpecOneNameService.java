package com.ispan.warashibe.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.SpecOneNames;
import com.ispan.warashibe.repository.SpecOneNameRepository;

@Service
public class SpecOneNameService {
	@Autowired
	private SpecOneNameRepository specOneNameRepo;
	@Autowired
	private ObjectMapper objMapper;

	public SpecOneNames create(String json) {	// 建立一筆
		try {
			SpecOneNames specOneName = objMapper.readValue(json, SpecOneNames.class);
			if (specOneName.getSpecOneName() != null) {
				if (specOneName.getSpecOneNameID() != null
						&& specOneNameRepo.findById(specOneName.getSpecOneNameID()).isPresent()) {
					return null;
				}
				return specOneNameRepo.save(specOneName);
			}
			return null;
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public SpecOneNames modify(String json) {	// 修改一筆
		try {
			SpecOneNames specOneName = objMapper.readValue(json, SpecOneNames.class);
			if(specOneName.getSpecOneNameID() != null && specOneName.getSpecOneName()!=null) {
				if(specOneNameRepo.findById(specOneName.getSpecOneNameID()).isPresent()) {
					return specOneNameRepo.save(specOneName);
				}
			} return null;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
	}
	
	public SpecOneNames findById(Integer id) {	// 尋找一筆
		if(id!=null) {
			Optional<SpecOneNames> specOneName = specOneNameRepo.findById(id);
			return specOneName.isPresent()? specOneName.get() : null;
		} return null;
	}
	
	public boolean deleteById(Integer id) {	// 刪除一筆
		if(id!=null) {
			Optional<SpecOneNames> specOneName = specOneNameRepo.findById(id);
			if(specOneName.isPresent()) {
				specOneNameRepo.deleteById(id);
				System.out.println("delete success");
				return true;
			}
		} return false;
	}
	
	public SpecOneNames findByProdductId(Integer id) {	// 以產品ID搜尋一筆
		if(id!=null) {
			return specOneNameRepo.findByProductId(id).isPresent()? specOneNameRepo.findByProductId(id).get() : null;
		} return null;
	}
}
