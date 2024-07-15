package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;

public interface ProductRepository extends JpaRepository<Products, Integer> {
	@Query("SELECT p FROM Products p ORDER BY RAND()")
    List<Products> findRandomProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.isSecondHand = true")
    List<Products> findSecondHandProducts();

    @Query("SELECT p FROM Products p JOIN Rank r ON p.productID = r.product.productID " +
            "ORDER BY COUNT(r.rankID) DESC")
    List<Products> findPopularProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.subCategory IN :categories ORDER BY RAND()")
    List<Products> findRecommendedProducts(List<SubCategory> categories, Pageable pageable);

    List<Products> findBySubCategory(SubCategory subCategory);
    
    List<Products> findByMember(Members member);

    List<Products> findByProductStatusAndMember(Boolean status, Members member);
}
