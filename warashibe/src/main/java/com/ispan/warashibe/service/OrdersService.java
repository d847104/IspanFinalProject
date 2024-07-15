package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Orders;
import com.ispan.warashibe.repository.OrdersRepository;

@Service
@Transactional
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepo;
	@Autowired
	private ObjectMapper objMapper;

	public Orders create(String json) {
		try {
			Orders newOrder = objMapper.readValue(json, Orders.class);
			Optional<Orders> opt = ordersRepo.findById(newOrder.getOrderID());
			if(opt.isEmpty()) {
				return ordersRepo.save(newOrder);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Orders modify(String json) {
		try {
			Orders newOrder = objMapper.readValue(json, Orders.class);
			Optional<Orders> opt = ordersRepo.findById(newOrder.getOrderID());
			if(opt.isPresent()) {
				return ordersRepo.save(newOrder);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Orders findById(Integer id) {
		if (id != null) {
			Optional<Orders> opt = ordersRepo.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}

	public boolean deleteById(Integer id) {
		if (id != null) {
			Optional<Orders> opt = ordersRepo.findById(id);
			if (opt.isPresent()) {
				ordersRepo.deleteById(id);
				return true;
			}
		}
		return false;
	}

	public List<Orders> findByBuyerId(Integer buyerID) {
		return ordersRepo.findOne(OrdersRepository.buyerIdEqualTo(buyerID));
	}

	public List<Orders> findBySellerId(Integer buyerID) {
		return ordersRepo.findOne(OrdersRepository.sellerIdEqualTo(buyerID));
	}
}
