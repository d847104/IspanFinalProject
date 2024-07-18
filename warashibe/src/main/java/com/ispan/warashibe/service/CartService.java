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
			Cart product = objMapper.readValue(json, Cart.class);
			if (product.getMember()!=null
					&& product.getProduct()!=null
					&& product.getSeller()!=null
					&& product.getQuantity()!=null) {	// 檢查JSON資料是否符合NOT NULL
				if (cartRepo.countByProductId(product.getProduct().getProductID())!=0) {	// 若購物車中已存在相同產品ID
					if (cartRepo.countBySpecId(product.getProductSpec().getSpecID())!=0) {	// 若購物車中已存在相同產品規格ID
//						product.setCartID(cartRepo.);
					}
				}
				if (product.getCartID()!=null) {	// 檢查JSON是否含ID資料
					Optional<Cart> opt = cartRepo.findById(product.getCartID());	// 若有ID則檢查DB是否已存在該ID
					return opt.isEmpty() ? cartRepo.save(product) : null;	// ID不存在則新增一筆,否則回傳NULL
				}
				return cartRepo.save(product);	// 若JSON未提供ID則視為自動產生ID新增一筆
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

	public List<Cart> findByBuyerId(Integer buyerID) {	// 以買家ID查詢多筆
		return cartRepo.findAll(CartRepository.buyerIdEqualTo(buyerID));
	}
}
