package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.BrowsingHistory;
import com.ispan.warashibe.service.BrowsingHistoryService;

@RestController
@RequestMapping("/api/browsing-history")
@CrossOrigin
public class BrowsingHistoryController {
    @Autowired
    private BrowsingHistoryService service;

    @GetMapping("/{memberID}")
    public List<BrowsingHistory> getBrowsingHistory(@PathVariable Integer memberID) {
        return service.getBrowsingHistoryByMemberID(memberID);
    }

    @PostMapping
    public void saveBrowsingHistory(@RequestBody BrowsingHistory history) {
        service.saveBrowsingHistory(history);
    }
}
