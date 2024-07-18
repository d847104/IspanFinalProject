package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.OrderProducts;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Integer>, JpaSpecificationExecutor<OrderProducts> {
	List<OrderProducts> findAll(Specification<OrderProducts> spec);
	
	public static Specification<OrderProducts> orderIdEqualTo(Integer orderId) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order").get("orderID"), orderId);
	}
}
