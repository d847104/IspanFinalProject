package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer>, JpaSpecificationExecutor<ProductImg> {
	List<ProductImg> findByProduct_ProductID(int productId);
	
	default List<ProductImg> findByProductId(Integer productId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("productID"), productId));
	}
}