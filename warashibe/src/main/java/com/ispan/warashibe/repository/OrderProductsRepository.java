package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.OrderProducts;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Integer> {

}
