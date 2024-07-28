package com.ispan.warashibe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.ProductDelivery;
import com.ispan.warashibe.model.ProductPayMethod;
import com.ispan.warashibe.repository.ProductDeliveryRepository;
@Service
public class ProductDeliveryService {
	
	@Autowired
	private ProductDeliveryRepository productDeliveryRepo;
	
	public ProductDelivery create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
			Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");
			
//			Optional<PayMethod> optaional1=payMethodRepo.findById(payMethodID);
			
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(id);
			if (optional.isEmpty()) {
				ProductDelivery insert = new ProductDelivery();
				insert.setId(id);
				insert.setDeliveryID(deliveryID);
				insert.setProductID(productID);
						
				return productDeliveryRepo.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductDelivery insert(ProductDelivery bean) {
		if (bean != null && bean.getId() != null) {
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(bean.getId());
			if (optional.isEmpty()) {
				return productDeliveryRepo.save(bean);
			}
		}
		return null;
	}
	
	public ProductDelivery modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
			Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");

			Optional<ProductDelivery> optional = productDeliveryRepo.findById(id);
			if (optional.isPresent()) {
				ProductDelivery update = optional.get();
				update.setId(id);
				update.setDeliveryID(deliveryID);
				update.setProductID(productID);
				return productDeliveryRepo.save(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductDelivery update(ProductDelivery bean) {
		if(bean != null && bean.getId()!=null) {
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(bean.getId());
			if(optional.isPresent()) {
				return productDeliveryRepo.save(bean);
			}
		}return null;
	}
	
	public boolean remove(Integer id) {
		if (id != null) {
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(id);
			if (optional.isPresent()) {
				productDeliveryRepo.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
	public boolean delete(ProductDelivery bean) {
		if(bean != null && bean.getId()!=null) {
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(bean.getId());
			if(optional.isPresent()) {
				productDeliveryRepo.deleteById(bean.getId());
				return true;
			}
		}return false;
	}
	
	public List<ProductDelivery> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productDeliveryRepo.find(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ProductDelivery> select(ProductPayMethod bean){
		List<ProductDelivery> result= null;
		if(bean!=null && bean.getId()!=null && !bean.getId().equals(0)) {
			Optional<ProductDelivery> optional = productDeliveryRepo.findById(bean.getId());
			if(optional.isPresent()) {
				result= new ArrayList<ProductDelivery>();
				result.add(optional.get());
			}
		}else {
			result=productDeliveryRepo.findAll();
		}return result;
	}
	
	public boolean exists(Integer id) {
		if(id!=null) {
			return productDeliveryRepo.existsById(id);
		}return false;
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productDeliveryRepo.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ProductDelivery findById(Integer id) {
		return productDeliveryRepo.findById(id).orElse(null);
	}
}
