package com.ispan.warashibe.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.HelpCenterRequest;
import com.ispan.warashibe.repository.HelpCenterRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HelpCenterRequestService {

    @Autowired
    private HelpCenterRequestRepository helpCenterRequestRepository;

    public HelpCenterRequest saveRequest(HelpCenterRequest request) {
        return helpCenterRequestRepository.save(request);
    }

    public HelpCenterRequest updateRequest(HelpCenterRequest request) {
        return helpCenterRequestRepository.save(request); // Save 方法用于更新和新增
    }

    public void deleteRequest(Long id) {
        helpCenterRequestRepository.deleteById(id);
    }

    public List<HelpCenterRequest> findAll() {
        return helpCenterRequestRepository.findAll();
    }

    public HelpCenterRequest findById(Long id) {
        Optional<HelpCenterRequest> optional = helpCenterRequestRepository.findById(id);
        return optional.orElse(null);
    }
}

