package com.ispan.warashibe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.BrowsingHistory;

public interface BrowsingHistoryRepository extends JpaRepository<BrowsingHistory, Integer> {
    List<BrowsingHistory> findByMemberMemberIDOrderByBrowseTimeDesc(Integer memberID);
    Optional<BrowsingHistory> findByMemberMemberIDAndProductProductID(Integer memberID, Integer productID);
}
