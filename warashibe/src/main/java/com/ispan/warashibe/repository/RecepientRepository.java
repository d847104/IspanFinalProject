package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Recepient;

public interface RecepientRepository extends JpaRepository<Recepient, Integer>, JpaSpecificationExecutor<Recepient> {
	default List<Recepient> findByMemberId(Integer memberId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("member").get("memberID"), memberId));
	}
}
