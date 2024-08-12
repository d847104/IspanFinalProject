package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.warashibe.dao.ProductDeliveryDAO;
import com.ispan.warashibe.model.ProductDelivery;
import com.ispan.warashibe.model.Products;

public interface ProductDeliveryRepository extends JpaRepository<ProductDelivery, Integer>,ProductDeliveryDAO{
	@Query("SELECT pd FROM ProductDelivery pd WHERE pd.productID = :product")
    List<ProductDelivery> findByProduct(@Param("product") Products product);
}
