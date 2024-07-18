package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.dao.ProductPayMethodDAO;
import com.ispan.warashibe.model.ProductPayMethod;

public interface ProductPayMethodRepository extends JpaRepository<ProductPayMethod, Integer> ,ProductPayMethodDAO{
	@Query(value = "select count(*) from ProductPayMethod where productID = :productID")
	public long countByPayMethodID(Integer productID);

	
}
