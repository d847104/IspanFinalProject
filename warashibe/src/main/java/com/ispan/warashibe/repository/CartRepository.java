package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {
	List<Cart> findAll(Specification<Cart> spec);
	default Long countByProductId(Integer productId) {return count(productIdEqualTo(productId));}
	default Long countBySpecId(Integer specId) {return count(specIdEqualTo(specId));}
	
	public static Specification<Cart> buyerIdEqualTo(Integer buyerId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buyer").get("memberID"), buyerId);
	}
	
	public static Specification<Cart> productIdEqualTo(Integer productId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("product").get("productID"), productId);
	}
	
	public static Specification<Cart> specIdEqualTo(Integer specId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productSpec").get("specID"), specId);
	}
	
	public static Specification<Cart> findBySpecId(Integer specId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("productSpec").get("specID"), specId);
	}
}