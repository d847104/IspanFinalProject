package com.ispan.warashibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.warashibe.model.HelpCenterRequest;
import com.ispan.warashibe.service.HelpCenterRequestService;

@RestController
@RequestMapping("/api/help-center")
@CrossOrigin
public class HelpCenterRequestController {

    @Autowired
    private HelpCenterRequestService helpCenterRequestService;

    @PostMapping("/submit")
    public ResponseEntity<HelpCenterRequest> submitRequest(@RequestBody HelpCenterRequest request) {
        HelpCenterRequest savedRequest = helpCenterRequestService.saveRequest(request);
        return ResponseEntity.ok(savedRequest);
    }
}
