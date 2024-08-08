package com.ispan.warashibe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.HelpCenterRequest;
import com.ispan.warashibe.repository.HelpCenterRequestRepository;

@Service
public class HelpCenterRequestService {

    @Autowired
    private HelpCenterRequestRepository helpCenterRequestRepository;

    public HelpCenterRequest saveRequest(HelpCenterRequest request) {
        return helpCenterRequestRepository.save(request);
    }
}
