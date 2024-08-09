package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.warashibe.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
