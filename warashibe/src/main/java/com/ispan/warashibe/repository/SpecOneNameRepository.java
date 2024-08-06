package com.ispan.warashibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.SpecOneNames;

public interface SpecOneNameRepository
		extends JpaRepository<SpecOneNames, Integer>, JpaSpecificationExecutor<SpecOneNames> {
	default Optional<SpecOneNames> findByProductId(Integer productId) {
		return findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("productID"), productId));
	}
}
