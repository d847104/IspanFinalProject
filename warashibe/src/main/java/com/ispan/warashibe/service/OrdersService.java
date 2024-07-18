package com.ispan.warashibe.service;

import java.lang.reflect.Method;
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

	public Orders create(String json) {	// 新增一筆
		try {
			Orders newOrder = objMapper.readValue(json, Orders.class);
			if (newOrder.getSeller()!=null
					&& newOrder.getBuyer()!=null
					&& newOrder.getDelivery()!=null
					&& newOrder.getDeliveryFee()!=null
					&& newOrder.getPayMethod()!=null) {	// 檢查JSON資料是否符合NOT NULL
				if (newOrder.getOrderID()!=null) {	// 檢查JSON是否含ID資料
					Optional<Orders> opt = ordersRepo.findById(newOrder.getOrderID());	// 若有ID則檢查DB是否已存在該ID
					return opt.isEmpty() ? ordersRepo.save(newOrder) : null;	// ID不存在則新增一筆,否則回傳NULL
				} return ordersRepo.save(newOrder);	// 若JSON未提供ID則視為自動產生ID新增一筆
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public Orders modify(String json) {	// 修改一筆
		try {
			Orders newOrder = objMapper.readValue(json, Orders.class);
			for(Method m : newOrder.getClass().getDeclaredMethods()) {	// 檢查JSON資料是否全部符合NOT NULL
				if(m.getName().startsWith("get") || m.getName().startsWith("is")) {if(m.invoke(newOrder)==null) return null;}
			}
			Optional<Orders> opt = ordersRepo.findById(newOrder.getOrderID());	// 檢查DB是否已有該ID對應資料
			return opt.isPresent() ? ordersRepo.save(newOrder) : null;	// 若該ID存在對應資料則修改資料,否則回傳NULL
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	public Orders findById(Integer id) {	// 以ID查詢一筆
		if (id != null) {
			Optional<Orders> opt = ordersRepo.findById(id);
			return opt.isPresent() ? opt.get() : null;
		} return null;
	}

	public boolean deleteById(Integer id) {	// 以ID刪除一筆
		if (id != null) {
			Optional<Orders> opt = ordersRepo.findById(id);
			if (opt.isPresent()) {
				ordersRepo.deleteById(id);
				return true;
			}
		} return false;
	}

	public List<Orders> findByBuyerId(Integer buyerID) {	// 以買家ID查詢多筆
		return ordersRepo.findAll(OrdersRepository.buyerIdEqualTo(buyerID));
	}

	public List<Orders> findBySellerId(Integer buyerID) {	// 以賣家ID查詢多筆
		return ordersRepo.findAll(OrdersRepository.sellerIdEqualTo(buyerID));
	}
}
