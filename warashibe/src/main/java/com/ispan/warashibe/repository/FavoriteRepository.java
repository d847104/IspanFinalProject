package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ispan.warashibe.model.Favorite;
import com.ispan.warashibe.model.FavoriteID;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteID>, JpaSpecificationExecutor<Favorite> {
	
	default List<Favorite> findByMemberId(Integer memberId) {
		return findAll((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("member").get("memberID"), memberId));
	}
}
