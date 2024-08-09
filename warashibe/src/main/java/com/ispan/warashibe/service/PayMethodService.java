package com.ispan.warashibe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.PayMethod;
import com.ispan.warashibe.repository.PayMethodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PayMethodService {

	@Autowired
	private PayMethodRepository payMethodRepo;

	public PayMethod create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
			String payMethod = obj.isNull("payMethod") ? null : obj.getString("payMethod");

			Optional<PayMethod> optional = payMethodRepo.findById(payMethodID);
			if (optional.isEmpty()) {
				PayMethod insert = new PayMethod();
				insert.setPayMethodID(payMethodID);
				insert.setPayMethod(payMethod);
				return payMethodRepo.save(insert);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PayMethod insert(PayMethod bean) {
		if (bean != null && bean.getPayMethodID() != null) {
			Optional<PayMethod> optional = payMethodRepo.findById(bean.getPayMethodID());
			if (optional.isEmpty()) {
				return payMethodRepo.save(bean);
			}
		}
		return null;
	}

	public PayMethod modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
			String payMethod = obj.isNull("payMethod") ? null : obj.getString("payMethod");

			Optional<PayMethod> optional = payMethodRepo.findById(payMethodID);
			if (optional.isPresent()) {
				PayMethod update = new PayMethod();
				update.setPayMethodID(payMethodID);
				update.setPayMethod(payMethod);
				return payMethodRepo.save(update);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	public PayMethod update(PayMethod bean) {
		if(bean != null && bean.getPayMethodID()!=null) {
			Optional<PayMethod> optional = payMethodRepo.findById(bean.getPayMethodID());
			if(optional.isPresent()) {
				return payMethodRepo.save(bean);
			}
		}return null;
	}
	
	public boolean remove(Integer payMethodID) {
		if (payMethodID != null) {
			Optional<PayMethod> optional = payMethodRepo.findById(payMethodID);
			if (optional.isPresent()) {
				payMethodRepo.deleteById(payMethodID);
				return true;
			}
		}
		return false;
	}
	
	public boolean delete(PayMethod bean) {
		if(bean != null && bean.getPayMethodID()!=null) {
			Optional<PayMethod> optional = payMethodRepo.findById(bean.getPayMethodID());
			if(optional.isPresent()) {
				payMethodRepo.deleteById(bean.getPayMethodID());
				return true;
			}
		}return false;
	}
	
	public List<PayMethod> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return payMethodRepo.find(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PayMethod> select(PayMethod bean){
		List<PayMethod> result= null;
		if(bean!=null && bean.getPayMethodID()!=null && !bean.getPayMethodID().equals(0)) {
			Optional<PayMethod> optional = payMethodRepo.findById(bean.getPayMethodID());
			if(optional.isPresent()) {
				result= new ArrayList<PayMethod>();
				result.add(optional.get());
			}
		}else {
			result=payMethodRepo.findAll();
		}return result;
	}
	
	public boolean exists(Integer payMethodID) {
		if(payMethodID!=null) {
			return payMethodRepo.existsById(payMethodID);
		}return false;
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return payMethodRepo.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public PayMethod findById(Integer payMethodID) {
		return payMethodRepo.findById(payMethodID).orElse(null);
	}
	
	public List<PayMethod> findAll(){
		return payMethodRepo.findAll();
	}
}
