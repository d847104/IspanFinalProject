package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.dao.MessengerDAO;
import com.ispan.warashibe.model.Messenger;

public interface MessengerRepository extends JpaRepository<Messenger, Integer> , MessengerDAO {

	@Query(value = "select count(*) from Messenger where msg = :msg")
	public long countByMessenger(String msg);
	
}
