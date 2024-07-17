package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {
	List<Orders> findAll(Specification<Orders> spec);
	
	public static Specification<Orders> buyerIdEqualTo(Integer buyerID) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buyer").get("memberID"), buyerID);
	}
	
	public static Specification<Orders> sellerIdEqualTo(Integer sellerID) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("seller").get("memberID"), sellerID);
	}
	
//	public static Specification<Orders> find(JSONObject obj) {
//		return (root, query, criteriaBuilder) -> criteriaBuilder.
//	}
}