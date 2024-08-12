package com.ispan.warashibe.controller;

import java.util.List;
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

    @GetMapping("/list")
    public ResponseEntity<List<ContactMessage>> listContactMessages() {
        List<ContactMessage> messages = contactMessageService.findAll();
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContactMessage> updateContactMessage(@PathVariable Long id, @RequestBody ContactMessage contactMessage) {
        ContactMessage updatedMessage = contactMessageService.updateStatus(id, contactMessage);
        return ResponseEntity.ok(updatedMessage);
    }
}
