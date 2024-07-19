package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.OrderProducts;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Integer>, JpaSpecificationExecutor<OrderProducts> {
	default List<OrderProducts> findByOrderId(Integer orderId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("order").get("orderID"), orderId));
	}
}
