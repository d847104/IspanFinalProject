package com.ispan.warashibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.warashibe.model.ProductSpec;
import com.ispan.warashibe.model.Products;

public interface ProductSpecRepository extends JpaRepository<ProductSpec, Integer> {

	Optional<ProductSpec> findBySpecOneAndSpecOneNameAndSpecTwoAndSpecTwoNameAndProduct(String specOne,
			String specOneName, String specTwo, String specTwoName, Products product);
}
