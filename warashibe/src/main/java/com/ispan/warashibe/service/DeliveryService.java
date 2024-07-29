package com.ispan.warashibe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Delivery;
import com.ispan.warashibe.repository.DeliveryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepo;

	public Delivery create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
			String delivery = obj.isNull("delivery") ? null : obj.getString("delivery");
			Integer deliveryFee = obj.isNull("deliveryFee") ? null : obj.getInt("deliveryFee");

			Optional<Delivery> optional = deliveryRepo.findById(deliveryID);
			if (optional.isEmpty()) {
				Delivery insert = new Delivery();
				insert.setDeliveryID(deliveryID);
				insert.setDelivery(delivery);
				insert.setDeliveryFee(deliveryFee);

				return deliveryRepo.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Delivery insert(Delivery bean) {
		if (bean != null && bean.getDeliveryID() != null) {
			Optional<Delivery> optional = deliveryRepo.findById(bean.getDeliveryID());
			if (optional.isEmpty()) {
				return deliveryRepo.save(bean);
			}
		}
		return null;
	}

	public Delivery modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
			String delivery = obj.isNull("delivery") ? null : obj.getString("delivery");
			Integer deliveryFee = obj.isNull("deliveryFee") ? null : obj.getInt("deliveryFee");

			Optional<Delivery> optional = deliveryRepo.findById(deliveryID);
			if (optional.isPresent()) {
				Delivery update = optional.get();
				update.setDeliveryID(deliveryID);
				update.setDelivery(delivery);
				update.setDeliveryFee(deliveryFee);
				return deliveryRepo.save(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Delivery update(Delivery bean) {
		if(bean != null && bean.getDeliveryID()!=null) {
			Optional<Delivery> optional = deliveryRepo.findById(bean.getDeliveryID());
			if(optional.isPresent()) {
				return deliveryRepo.save(bean);
			}
		}return null;
	}

	public boolean remove(Integer deliveryID) {
		if (deliveryID != null) {
			Optional<Delivery> optional = deliveryRepo.findById(deliveryID);
			if (optional.isPresent()) {
				deliveryRepo.deleteById(deliveryID);
				return true;
			}
		}
		return false;
	}

	public boolean delete(Delivery bean) {
		if(bean != null && bean.getDeliveryID()!=null) {
			Optional<Delivery> optional = deliveryRepo.findById(bean.getDeliveryID());
			if(optional.isPresent()) {
				deliveryRepo.deleteById(bean.getDeliveryID());
				return true;
			}
		}return false;
	}
	
	
	
	public List<Delivery> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return deliveryRepo.find(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Delivery> select(Delivery bean){
		List<Delivery> result= null;
		if(bean!=null && bean.getDeliveryID()!=null && !bean.getDeliveryID().equals(0)) {
			Optional<Delivery> optional = deliveryRepo.findById(bean.getDeliveryID());
			if(optional.isPresent()) {
				result= new ArrayList<Delivery>();
				result.add(optional.get());
			}
		}else {
			result=deliveryRepo.findAll();
		}return result;
	}
	
	public boolean exists(Integer deliveryID) {
		if(deliveryID!=null) {
			return deliveryRepo.existsById(deliveryID);
		}return false;
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return deliveryRepo.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Delivery findById(Integer deliveryID) {
		return deliveryRepo.findById(deliveryID).orElse(null);
	}
	
	

}
