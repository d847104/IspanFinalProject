package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.dao.ProductDeliveryDAO;
import com.ispan.warashibe.model.ProductDelivery;

public interface ProductDeliveryRepository extends JpaRepository<ProductDelivery, Integer>,ProductDeliveryDAO{
	@Query(value = "select count(*) from ProductDelivery where productID= :productID")
	public long countByProductDelivery(Integer productID);
}
