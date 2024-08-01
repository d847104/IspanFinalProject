package com.ispan.warashibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.warashibe.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	List<Notification> findByReceiverID_MemberID(Integer receiverID);
}
