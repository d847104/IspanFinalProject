package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {
	default List<Orders> findByBuyerId(Integer buyerId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buyer").get("memberID"), buyerId));
	}
	
	default List<Orders> findBySellerId(Integer sellerId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("seller").get("memberID"), sellerId));
	}
	
	default Page<Orders> findByPage(Pageable pgb) {
		Specification<Orders> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
		return findAll(spec, pgb);
	}
}