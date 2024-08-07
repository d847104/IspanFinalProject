package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.Rank;

@Repository
public interface RankRepository extends JpaRepository<Rank, Integer> {
    List<Rank> findByRanking(Integer ranking, Pageable pageable);
    List<Rank> findByMember_MemberID(Integer memberID);
    List<Rank> findByProduct_ProductID(Integer memberID);
}