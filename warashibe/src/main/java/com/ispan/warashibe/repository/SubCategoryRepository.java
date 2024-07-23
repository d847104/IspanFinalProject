package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer>, JpaSpecificationExecutor<SubCategory> {
	default List<SubCategory> findByMainCategory(Integer mainId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("mainCategory").get("mainCategoryID"), mainId));
	}
}
