package com.ispan.warashibe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Messenger;
import com.ispan.warashibe.repository.MessengerRepository;
import com.ispan.warashibe.util.DatetimeConverter;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class MessengerService {

	@Autowired
	private MessengerRepository messengerRepo;

	public Messenger create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer msgID = obj.isNull("msgID") ? null : obj.getInt("msgID");
			Integer senderID = obj.isNull("senderID") ? null : obj.getInt("senderID");
			Integer receiverID = obj.isNull("receiverID") ? null : obj.getInt("receiverID");
			String msg = obj.isNull("msg") ? null : obj.getString("msg");
			String msgTime = obj.isNull("msgTime") ? null : obj.getString("msgTime");

			Optional<Messenger> optional = messengerRepo.findById(msgID);
			if (optional.isEmpty()) {
				Messenger insert = new Messenger();
				insert.setMsgID(msgID);
				insert.setSenderID(senderID);
				insert.setReceiverID(receiverID);
				insert.setMsg(msg);
				insert.setMsgTime(null);
				insert.setMsgTime(DatetimeConverter.parse(msgTime, "yyyy-MM-dd HH:mm:ss EEEE"));

				return messengerRepo.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Messenger insert(Messenger bean) {
		if(bean!=null && bean.getMsgID()!=null) {
			Optional<Messenger> optional = messengerRepo.findById(bean.getMsgID());
			if(optional.isEmpty()) {
				return messengerRepo.save(bean);
			}
		}
		return null;
	}
	
	public Messenger modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer msgID = obj.isNull("msgID") ? null : obj.getInt("msgID");
			Integer senderID = obj.isNull("senderID") ? null : obj.getInt("senderID");
			Integer receiverID = obj.isNull("receiverID") ? null : obj.getInt("receiverID");
			String msg = obj.isNull("msg") ? null : obj.getString("msg");
			String msgTime = obj.isNull("msgTime") ? null : obj.getString("msgTime");

			Optional<Messenger> optional = messengerRepo.findById(msgID);
			if (optional.isPresent()) {
				Messenger update = optional.get();
				update.setMsgID(msgID);
				update.setSenderID(senderID);
				update.setReceiverID(receiverID);
				update.setMsg(msg);
				update.setMsgTime(null);
				update.setMsgTime(DatetimeConverter.parse(msgTime, "yyyy-MM-dd HH:mm:ss EEEE"));
				return messengerRepo.save(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Messenger update(Messenger bean) {
		if(bean != null && bean.getMsgID()!=null) {
			Optional<Messenger> optional = messengerRepo.findById(bean.getMsgID());
			if(optional.isPresent()) {
				return messengerRepo.save(bean);
			}
		}return null;
	}
	
	public boolean remove(Integer msgID) {
		if (msgID != null) {
			Optional<Messenger> optional = messengerRepo.findById(msgID);
			if (optional.isPresent()) {
				messengerRepo.deleteById(msgID);
				return true;
			}
		}
		return false;
	}

	public boolean delete(Messenger bean) {
		if(bean != null && bean.getMsgID()!=null) {
			Optional<Messenger> optional = messengerRepo.findById(bean.getMsgID());
			if(optional.isPresent()) {
				messengerRepo.deleteById(bean.getMsgID());
				return true;
			}
		}return false;
	}
	
	public List<Messenger> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return messengerRepo.find(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Messenger> select(Messenger bean){
		List<Messenger> result= null;
		if(bean!=null && bean.getMsgID()!=null && !bean.getMsgID().equals(0)) {
			Optional<Messenger> optional = messengerRepo.findById(bean.getMsgID());
			if(optional.isPresent()) {
				result= new ArrayList<Messenger>();
				result.add(optional.get());
			}
		}else {
			result=messengerRepo.findAll();
		}return result;
	}
	
	public boolean exists(Integer msgID) {
		if(msgID!=null) {
			return messengerRepo.existsById(msgID);
		}return false;
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return messengerRepo.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Messenger findById(Integer msgID) {
		return messengerRepo.findById(msgID).orElse(null);
	}
}
