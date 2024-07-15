package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<MainCategory> getAllMainCategories() {
        return mainCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryById(int id) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        return subCategory.orElse(null);
    }

    public List<SubCategory> getSubCategoriesByIds(List<Integer> ids) {
        return subCategoryRepository.findAllById(ids);
    }
}
