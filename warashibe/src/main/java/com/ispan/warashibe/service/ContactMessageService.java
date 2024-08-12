package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.ContactMessage;
import com.ispan.warashibe.repository.ContactMessageRepository;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactMessage saveContactMessage(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

    public List<ContactMessage> findAll() {
        return contactMessageRepository.findAll();
    }

    public ContactMessage updateStatus(Long id, ContactMessage contactMessage) {
        Optional<ContactMessage> optionalMessage = contactMessageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            ContactMessage existingMessage = optionalMessage.get();
            existingMessage.setStatus(contactMessage.getStatus());
            return contactMessageRepository.save(existingMessage);
        } else {
            throw new RuntimeException("Message not found");
        }
    }
}
