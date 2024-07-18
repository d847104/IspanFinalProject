package com.ispan.warashibe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.warashibe.dao.DeliveryDAO;
import com.ispan.warashibe.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer>, DeliveryDAO{

	@Query(value = "select count(*) from Delivery where delivery = :delivery")
	public long countByDelivery(String delivery);

}
