package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {
	default List<Cart> findByMemberId(Integer buyerId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("member").get("memberID"), buyerId));
	}
	
	default List<Cart> findByProductId(Integer productId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("productID"), productId));
	}
	
	default List<Cart> findBySpecId(Integer specId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productSpec").get("specID"), specId));
	}
	
	default Long countByProductId(Integer productId) {
		return count((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("productID"), productId));
	}
	
	default Long countBySpecId(Integer specId) {
		return count((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productSpec").get("specID"), specId));
	}
	
	default Long countByMemberId(Integer memberId) {
		return count((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("member").get("memberID"), memberId));
	}
}