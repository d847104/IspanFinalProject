package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.Favorite;
import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.Recepient;
import com.ispan.warashibe.repository.FavoriteRepository;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.repository.ProductRepository;
import com.ispan.warashibe.repository.RecepientRepository;

@Service
public class FavoriteService {

//	@Autowired
//	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private MembersRepository membersRepo;
	@Autowired
	private FavoriteRepository favoriteRepo;
	
	//查詢單筆
	public  Favorite findById(Integer id) {
		Optional< Favorite> optional = favoriteRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;	
	}
	
	//查詢全部
	public List<Favorite> findAll() {
		return favoriteRepo.findAll();
	}
	
	public boolean exists(Integer id) {
		if (id != null) {
			return favoriteRepo.existsById(id);
		}
		return false;
	}
	// 刪除單筆
	public Boolean deleteOne(Integer id) {
		if (id != null) {
			Optional<Favorite> optional = favoriteRepo.findById(id);
			if (optional.isPresent()) {
				favoriteRepo.deleteById(id);
				return true;
			}
		}
		return false;
	} // end of deleteOne
	
	// 新增單筆
	public Favorite insert(String json) {
//		Favorite favorite = null;
//		try {
//			favorite = objectMapper.readValue(json, Favorite.class);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return favoriteRepo.save(favorite);
		
        JSONObject obj = new JSONObject(json);
		Integer favID = obj.isNull("favID") ? null : obj.getInt("favID");
		Integer memberID = obj.isNull("memberID") ? null : obj.getInt("memberID");
		Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");
		Integer sellerID = obj.isNull("sellerID") ? null : obj.getInt("sellerID");

        Optional<Members> member = membersRepo.findById(memberID);
        Optional<Members> seller = membersRepo.findById(sellerID);
        Optional<Products> product = productRepo.findById(productID);

        if(favID == null) {
        	Favorite favorite = new Favorite();
        	favorite.setMember(member.get());
        	favorite.setProduct(product.get());
        	favorite.setSeller(seller.get());

        	return favoriteRepo.save(favorite);
        }
        return null;
		
		
		
	}
	
// #######收藏商品需要修改嗎？
	
//	// 修改單筆
//	public Favorite modify(String json) {
//		JSONObject obj = new JSONObject(json);
//		Integer favID = obj.isNull("id") ? null : obj.getInt("id");
//		Optional<Favorite> optional = favoriteRepo.findById(favID);
//		
//		String name = obj.isNull("name") ? optional.get().getName() : obj.getString("name");
//		String mobile = obj.isNull("mobile") ? optional.get().getMobile() : obj.getString("mobile");
//		String address = obj.isNull("address") ? optional.get().getAddress() : obj.getString("address");
//
//		if(optional.isPresent()) {
//			Recepient update = optional.get();
//			update.setName(name);
//			update.setMobile(mobile);
//			update.setAddress(address);
//			return recepientRepo.save(update);
//		}
//		return null;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
}
