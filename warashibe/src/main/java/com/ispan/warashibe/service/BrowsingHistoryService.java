package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.BrowsingHistory;
import com.ispan.warashibe.repository.BrowsingHistoryRepository;

@Service
public class BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryRepository repository;

    public List<BrowsingHistory> getBrowsingHistoryByMemberID(Integer memberID) {
        return repository.findByMemberMemberIDOrderByBrowseTimeDesc(memberID);
    }

    public void saveBrowsingHistory(BrowsingHistory history) {
        Optional<BrowsingHistory> existingHistory = repository.findByMemberMemberIDAndProductProductID(history.getMember().getMemberID(), history.getProduct().getProductID());
        if (existingHistory.isEmpty()) {
            repository.save(history);
        }
    }
}
