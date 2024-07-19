package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.OrderProducts;
import com.ispan.warashibe.repository.OrderProductsRepository;

@Service
@Transactional
public class OrderProductsService {
	@Autowired
	private OrderProductsRepository orderProductsRepo;
	@Autowired
	private ObjectMapper objMapper;
	
	public OrderProducts create(String json) {	// 新增一筆
		try {
			OrderProducts newOrder = objMapper.readValue(json, OrderProducts.class);
			if (newOrder.getOrder()!=null
					&& newOrder.getProduct()!=null
					&& newOrder.getProductName()!=null
					&& newOrder.getPrice()!=null
					&& newOrder.getQuantity()!=null) {	// 檢查JSON資料是否符合NOT NULL
				if (newOrder.getId()!=null) {	// 檢查JSON是否含ID資料
					Optional<OrderProducts> opt = orderProductsRepo.findById(newOrder.getId());	// 若有ID則檢查DB是否已存在該ID
					// 應要增加檢查ProductID是否存在(OneToOne)
					return opt.isEmpty() ? orderProductsRepo.save(newOrder) : null;	// ID不存在則新增一筆,否則回傳NULL
				} return orderProductsRepo.save(newOrder);	// 若JSON未提供ID則視為自動產生ID新增一筆
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public OrderProducts modify(String json) {	// 修改一筆
		try {
			OrderProducts newOrder = objMapper.readValue(json, OrderProducts.class);
			if (newOrder.getId()!=null
					&& newOrder.getOrder()!=null
					&& newOrder.getProduct()!=null
					&& newOrder.getProductName()!=null
					&& newOrder.getPrice()!=null
					&& newOrder.getQuantity()!=null) {
				Optional<OrderProducts> opt = orderProductsRepo.findById(newOrder.getId());	// 檢查DB是否已有該ID對應資料
				return opt.isPresent() ? orderProductsRepo.save(newOrder) : null;	// 若該ID存在對應資料則修改資料,否則回傳NULL				
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	public OrderProducts findById(Integer id) {	// 以ID查詢一筆
		if (id != null) {
			Optional<OrderProducts> opt = orderProductsRepo.findById(id);
			return opt.isPresent() ? opt.get() : null;
		} return null;
	}

	public boolean deleteById(Integer id) {	// 以ID刪除一筆
		if (id != null) {
			Optional<OrderProducts> opt = orderProductsRepo.findById(id);
			if (opt.isPresent()) {
				orderProductsRepo.deleteById(id);
				return true;
			}
		} return false;
	}
	
	public List<OrderProducts> findByOrderId(Integer id) {	// 以訂單ID查詢多筆產品
		return orderProductsRepo.findByOrderId(id);
	}
}
