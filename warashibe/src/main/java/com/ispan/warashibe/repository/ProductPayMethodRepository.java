package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.warashibe.dao.ProductPayMethodDAO;
import com.ispan.warashibe.model.ProductPayMethod;
import com.ispan.warashibe.model.Products;

public interface ProductPayMethodRepository extends JpaRepository<ProductPayMethod, Integer> ,ProductPayMethodDAO{
	@Query(value = "select count(*) from ProductPayMethod where productID = :productID")
	public long countByPayMethodID(Integer productID);

	@Query("SELECT p FROM ProductPayMethod p WHERE p.productID = :product")
    List<ProductPayMethod> findByProduct(@Param("product") Products product);
}
