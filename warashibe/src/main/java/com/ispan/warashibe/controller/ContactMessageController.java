package com.ispan.warashibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ispan.warashibe.model.ContactMessage;
import com.ispan.warashibe.service.ContactMessageService;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class ContactMessageController {
    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping("/submit")
    public ResponseEntity<ContactMessage> submitContactMessage(@RequestBody ContactMessage contactMessage) {
        ContactMessage savedMessage = contactMessageService.saveContactMessage(contactMessage);
        return ResponseEntity.ok(savedMessage);
    }
}
