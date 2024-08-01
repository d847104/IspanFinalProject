package com.ispan.warashibe.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
					&& newOrder.getPayMethod()!=null
					&& newOrder.getTotal()!=null) {	// 檢查JSON資料是否符合NOT NULL
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
				if(m.getName().startsWith("get") || m.getName().startsWith("is")) {
					System.out.println(m.getName()+":"+m.invoke(newOrder));
					if(m.invoke(newOrder)==null) return null;}
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
		return ordersRepo.findByBuyerId(buyerID);
	}

	public List<Orders> findBySellerId(Integer sellerID) {	// 以賣家ID查詢多筆
		return ordersRepo.findBySellerId(sellerID);
	}
	
	public Page<Orders> findAllByPage(String json) {
		JSONObject requestBody = new JSONObject(json);
		int pageNum = requestBody.isNull("pageNum")? 1 : requestBody.getInt("pageNum");		// 若未給予第幾頁則預設第一頁
		int pageRow = requestBody.isNull("pageRow")? 10 : requestBody.getInt("pageRow");	// 若未回傳每頁筆數則預設十筆
		Sort.Direction direction = requestBody.isNull("dir")? 								// 若未給予排序方向則預設降冪排列
				Sort.Direction.DESC : (requestBody.getString("dir").toUpperCase()=="ASC"? Sort.Direction.ASC : Sort.Direction.DESC);
		String dirBy = requestBody.isNull("dirBy")? "orderUpdate" : requestBody.getString("dirBy");	// 若未給予排序的依據欄位則預設以訂單更新時間排序
		Pageable pgb = PageRequest.of(pageNum - 1, pageRow, direction, dirBy);
		return ordersRepo.findByPage(pgb);
	}
}
