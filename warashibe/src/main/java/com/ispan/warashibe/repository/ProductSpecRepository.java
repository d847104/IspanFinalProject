package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.warashibe.model.ProductSpec;

public interface ProductSpecRepository extends JpaRepository<ProductSpec, Integer> {
}
