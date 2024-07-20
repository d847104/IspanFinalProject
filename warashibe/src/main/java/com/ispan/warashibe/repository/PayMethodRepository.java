package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.dao.PayMethodDAO;
import com.ispan.warashibe.model.PayMethod;

public interface PayMethodRepository extends JpaRepository<PayMethod, Integer>,PayMethodDAO {
	
	@Query(value = "select count(*) from PayMethod where payMethod = :payMethod")
	public long countByPayMethod(String payMethod);

}
