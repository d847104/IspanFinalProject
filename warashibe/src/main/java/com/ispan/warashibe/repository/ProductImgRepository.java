package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
	List<ProductImg> findByProduct_ProductID(int productId);
}