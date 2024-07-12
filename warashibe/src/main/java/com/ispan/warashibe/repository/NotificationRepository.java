package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
