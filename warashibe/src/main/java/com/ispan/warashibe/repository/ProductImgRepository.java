package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.warashibe.model.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
}