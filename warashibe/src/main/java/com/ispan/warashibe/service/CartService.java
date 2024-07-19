package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Cart;
import com.ispan.warashibe.repository.CartRepository;

@Service
@Transactional
public class CartService {
	@Autowired
	private ObjectMapper objMapper;
	@Autowired
	private CartRepository cartRepo;
	
	public Cart create(String json) {	// 新增一筆
		try {
			Cart item = objMapper.readValue(json, Cart.class);
			if (item.getMember()!=null
					&& item.getProduct()!=null
					&& item.getSeller()!=null
					&& item.getQuantity()!=null) {	// 檢查JSON資料是否符合NOT NULL
				if (item.getCartID()!=null && cartRepo.findById(item.getCartID()).isPresent()) {return null;} // DB存在相同ID則回傳NULL
				List<Cart> listSameMember = cartRepo.findByMemberId(item.getMember().getMemberID());
				if(!listSameMember.isEmpty()) {	// 存在同會員Cart資料
					for(Cart itemSameMember:listSameMember) {	// 若同會員Cart資料存在同產品及規格,視為修改原DB資料的數量
						if(item.getProductSpec()!=null && itemSameMember.getProductSpec()!=null) {
							if(item.getProductSpec().getSpecID() == itemSameMember.getProductSpec().getSpecID()) {
								item.setCartID(itemSameMember.getCartID());
								item.setQuantity(item.getQuantity() + itemSameMember.getQuantity());
							}
						}
						if(itemSameMember.getProduct().getProductID() == item.getProduct().getProductID()
								&& item.getProductSpec() == null 
								&& itemSameMember.getProductSpec() == null) {	// 有(無規格的)同產品ID
							item.setCartID(itemSameMember.getCartID());
							item.setQuantity(item.getQuantity() + itemSameMember.getQuantity());
						}
					}
				} return cartRepo.save(item);
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	public Cart modify(String json) {	// 修改一筆
		try {
			Cart product = objMapper.readValue(json, Cart.class);
			if (product.getMember()!=null
					&& product.getProduct()!=null
					&& product.getSeller()!=null
					&& product.getQuantity()!=null) {
				Optional<Cart> opt = cartRepo.findById(product.getCartID());	// 檢查DB是否已有該ID對應資料
				return opt.isPresent() ? cartRepo.save(product) : null;	// 若該ID存在對應資料則修改資料,否則回傳NULL
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}

	public Cart findById(Integer id) {	// 以ID查詢一筆
		if (id != null) {
			Optional<Cart> opt = cartRepo.findById(id);
			return opt.isPresent() ? opt.get() : null;
		} return null;
	}

	public boolean deleteById(Integer id) {	// 以ID刪除一筆
		if (id != null) {
			Optional<Cart> opt = cartRepo.findById(id);
			if (opt.isPresent()) {
				cartRepo.deleteById(id);
				return true;
			}
		} return false;
	}

	public List<Cart> findByMemberId(Integer buyerID) {	// 以買家ID查詢多筆
		return cartRepo.findByMemberId(buyerID);
	}
}
