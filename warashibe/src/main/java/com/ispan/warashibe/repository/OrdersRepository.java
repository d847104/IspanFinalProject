package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	List<Orders> findOne(Specification<Orders> spec);
	
	public static Specification<Orders> buyerIdEqualTo(Integer buyerID) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buyerID"), buyerID);
	}
	
	public static Specification<Orders> sellerIdEqualTo(Integer sellerID) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sellerID"), sellerID);
	}
}