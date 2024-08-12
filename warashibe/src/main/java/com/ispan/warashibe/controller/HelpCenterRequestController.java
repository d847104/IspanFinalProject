package com.ispan.warashibe.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.warashibe.model.HelpCenterRequest;
import com.ispan.warashibe.service.HelpCenterRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/help-center")
@CrossOrigin
public class HelpCenterRequestController {

    @Autowired
    private HelpCenterRequestService helpCenterRequestService;

    // 新增帮助中心请求
    @PostMapping("/submit")
    public ResponseEntity<HelpCenterRequest> submitRequest(@RequestBody HelpCenterRequest request) {
        HelpCenterRequest savedRequest = helpCenterRequestService.saveRequest(request);
        return ResponseEntity.ok(savedRequest);
    }

    // 更新帮助中心请求
    @PutMapping("/update/{id}")
    public ResponseEntity<HelpCenterRequest> updateRequest(@PathVariable Long id, @RequestBody HelpCenterRequest request) {
        request.setId(id); // 确保使用路径参数的ID
        HelpCenterRequest updatedRequest = helpCenterRequestService.updateRequest(request);
        return ResponseEntity.ok(updatedRequest);
    }

    // 删除帮助中心请求
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        HelpCenterRequest request = helpCenterRequestService.findById(id);
        if ("已處理".equals(request.getStatus())) {
            return ResponseEntity.badRequest().build(); // 已處理的请求无法删除
        }
        helpCenterRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }

    // 获取所有帮助中心请求
    @GetMapping("/list")
    public ResponseEntity<List<HelpCenterRequest>> getAllRequests() {
        List<HelpCenterRequest> requests = helpCenterRequestService.findAll();
        return ResponseEntity.ok(requests);
    }

    // 根据ID查找帮助中心请求
    @GetMapping("/find/{id}")
    public ResponseEntity<HelpCenterRequest> findRequestById(@PathVariable Long id) {
        HelpCenterRequest request = helpCenterRequestService.findById(id);
        return request != null ? ResponseEntity.ok(request) : ResponseEntity.notFound().build();
    }
}


