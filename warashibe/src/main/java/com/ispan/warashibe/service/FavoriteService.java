package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Favorite;
import com.ispan.warashibe.model.FavoriteID;
import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.repository.FavoriteRepository;

@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteRepository favoriteRepo;
	
	// 查詢單筆
	public  Favorite findById(FavoriteID favId) {
		Optional<Favorite> optional = favoriteRepo.findById(favId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	// 查詢全部
	public List<Favorite> findAll() {
		return favoriteRepo.findAll();
	}
	
	// 查詢是否存在
	public boolean exists(FavoriteID favId) {
		if (favId != null) {
			return favoriteRepo.existsById(favId);
		}
		return false;
	}
	
	// 刪除單筆
	public boolean deleteOne(FavoriteID favId) {
		if (favId != null) {
			Optional<Favorite> optional = favoriteRepo.findById(favId);
			if (optional.isPresent()) {
				favoriteRepo.deleteById(favId);
				return true;
			}
		}
		return false;
	}
	
	// 新增單筆
	public Favorite insert(String json) {		
        JSONObject obj = new JSONObject(json);
        
        FavoriteID favID = obj.isNull("memberID") || obj.isNull("productID") ? 
        		null : new FavoriteID(obj.getInt("memberID"),obj.getInt("productID"));
        Members member = obj.isNull("memberID") ? null : new Members();
        Products product = obj.isNull("productID") ? null : new Products();
        Members seller = obj.isNull("sellerID") ? null : new Members();
        
        if(!this.exists(favID)) {
        	member.setMemberID(obj.getInt("memberID"));
        	product.setProductID(obj.getInt("productID"));
        	seller.setMemberID(obj.getInt("sellerID"));
        	
        	Favorite favorite = new Favorite();
        	favorite.setFavID(favID);
        	favorite.setMember(member);
        	favorite.setProduct(product);
        	favorite.setSeller(seller);
        	return favoriteRepo.save(favorite);
        }
        return null;
	}
	
	// 以會員ID查詢多筆
	public List<Favorite> findByMemberId(Integer memberId) {
		return favoriteRepo.findByMemberId(memberId);
	}	

}
