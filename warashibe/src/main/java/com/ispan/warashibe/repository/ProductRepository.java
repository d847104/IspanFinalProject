package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.SubCategory;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    @Query("SELECT p FROM Products p ORDER BY RAND()")
    List<Products> findRandomProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.isSecondHand = true")
    List<Products> findSecondHandProducts(Pageable pageable);

    @Query(value = "SELECT p.productID, p.description, p.isSecondHand, p.memberID, " +
            "       p.price, p.productName, p.productStatus, p.stock, p.subCategoryID, " +
            "       p.updateTime, p.uploadDate, p.wishItem " +
            "FROM Products p " +
            "JOIN Rank r ON p.productID = r.productID " +
            "GROUP BY p.productID, p.description, p.isSecondHand, p.memberID, " +
            "         p.price, p.productName, p.productStatus, p.stock, p.subCategoryID, " +
            "         p.updateTime, p.uploadDate, p.wishItem " +
            "ORDER BY COUNT(r.productID) DESC", nativeQuery = true)
    List<Products> findPopularProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.subCategory IN :categories ORDER BY RAND()")
    List<Products> findRecommendedProducts(List<SubCategory> categories, Pageable pageable);
    
    @Query("SELECT p FROM Products p WHERE p.productName LIKE %:name%")
    List<Products> findByNameContaining(@Param("name") String name, Pageable pageable);

    List<Products> findBySubCategory(SubCategory subCategory, Pageable pageable);

    List<Products> findByMember(Members member);

    List<Products> findByProductStatusAndMember(Boolean status, Members member);
    
    List<Products> findByMember_MemberID(Integer memberID);
}
