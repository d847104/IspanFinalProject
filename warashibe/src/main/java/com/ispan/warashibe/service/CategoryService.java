package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.MainCategory;
import com.ispan.warashibe.model.SubCategory;
import com.ispan.warashibe.repository.MainCategoryRepository;
import com.ispan.warashibe.repository.SubCategoryRepository;

@Service
public class CategoryService {
	@Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    
    @Autowired
    private ObjectMapper objMapper;

    public List<MainCategory> getAllMainCategories() {	// 取得所有MainCategory
        return mainCategoryRepository.findAll();
    }
    
    public List<SubCategory> getAllSubCategories() {	// 取得所有SubCategory
    	return subCategoryRepository.findAll();
    }
    
    public List<SubCategory> getSubCategoriesByMain(Integer mainId) {	// 依據MainCategoryID取得SubCategories
    	return subCategoryRepository.findByMainCategory(mainId);
    }

    public MainCategory createMainCategory(String json) {	// 建立一筆MainCategory
    	try {
			MainCategory mainCategory = objMapper.readValue(json, MainCategory.class);
			if(mainCategory.getMainCategory()!=null) {
				if(mainCategory.getMainCategoryID()!=null) {
					Optional<MainCategory> opt = mainCategoryRepository.findById(mainCategory.getMainCategoryID());
					return opt.isEmpty()? mainCategoryRepository.save(mainCategory) : null;
				} return mainCategoryRepository.save(mainCategory);
			}
		} catch (Exception e) {e.printStackTrace();}
    	return null;
    }
    
    public SubCategory createSubCategory(String json) {	// 建立一筆SubCategory
    	try {
    		SubCategory subCategory = objMapper.readValue(json, SubCategory.class);
			if(subCategory.getSubCategory()!=null && subCategory.getMainCategory()!=null) {
				if(subCategory.getSubCategoryID()!=null) {
					Optional<SubCategory> opt = subCategoryRepository.findById(subCategory.getSubCategoryID());
					return opt.isEmpty()? subCategoryRepository.save(subCategory) : null;
				} return subCategoryRepository.save(subCategory);
			}
		} catch (Exception e) {e.printStackTrace();}
    	return null;
    }
    
    public SubCategory getSubCategoryById(int id) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        return subCategory.orElse(null);
    }

    public List<SubCategory> getSubCategoriesByIds(List<Integer> ids) {
        return subCategoryRepository.findAllById(ids);
    }
}
