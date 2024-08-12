package com.ispan.warashibe.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.Members;
import com.ispan.warashibe.model.Notification;
import com.ispan.warashibe.model.Orders;
import com.ispan.warashibe.repository.MembersRepository;
import com.ispan.warashibe.repository.NotificationRepository;
import com.ispan.warashibe.repository.OrdersRepository;
import com.ispan.warashibe.util.DatetimeConverter;

@Service
public class NotificationService {
	
	@Autowired
	private MembersRepository membersRepo;
	@Autowired
	private NotificationRepository notificationRepo;
	@Autowired
	private OrdersRepository ordersRepo;
	
	//查詢會員通知
	public List<Notification> findByReceiverID(Integer receiverID) {
        return notificationRepo.findByReceiverID_MemberID(receiverID);
    }
	
	//查詢單筆
	public Notification findById(Integer id) {
		Optional<Notification> optional = notificationRepo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;	
	}
	
	//查詢全部
	public List<Notification> findAll() {
		return notificationRepo.findAll();
	}
	
	public boolean exists(Integer id) {
		if (id != null) {
			return notificationRepo.existsById(id);
		}
		return false;
	}
	
	// 刪除單筆
	public Boolean deleteOne(Integer id) {
		if (id != null) {
			Optional<Notification> optional = notificationRepo.findById(id);
			if (optional.isPresent()) {
				notificationRepo.deleteById(id);
				return true;
			}
		}
		return false;
	} // end of deleteOne
	
	// 新增單筆
	// 新增单笔
	public Notification insert(String json) {
	    JSONObject obj = new JSONObject(json);
	    Integer receiverID = obj.optInt("receiverID");
	    Integer senderID = obj.optInt("senderID");
	    Integer orderID = obj.optInt("orderID");  // 可能为null
	    String content = obj.optString("content", null);
	    String notifyDate = obj.optString("notifyDate", null);
	    Boolean isRead = obj.optBoolean("isRead");

	    Optional<Members> receiver = membersRepo.findById(receiverID);
	    Optional<Members> sender = membersRepo.findById(senderID);
	    Optional<Orders> order = orderID != null ? ordersRepo.findById(orderID) : Optional.empty();

	    if(receiver.isPresent() && sender.isPresent()) {
	        Notification notification = new Notification();
	        notification.setReceiverID(receiver.get());
	        notification.setSenderID(sender.get());
	        notification.setOrderID(order.orElse(null));  // 如果 order 不存在，则设置为 null
	        notification.setContent(content);
	        notification.setNotifyDate(DatetimeConverter.parse(notifyDate, "yyyy-MM-dd"));
	        notification.setIsRead(isRead);
	        return notificationRepo.save(notification);
	    }
	    return null;
	}

	
	// 修改單筆
	public Notification modify(String json) {
		JSONObject obj = new JSONObject(json);
		Integer recepientID = obj.isNull("id") ? null : obj.getInt("id");
		Optional<Notification> optional = notificationRepo.findById(recepientID);
        String content = obj.isNull("content") ? optional.get().getContent() : obj.getString("content");
		String notifyDate = obj.isNull("notifyDate") ? optional.get().getNotifyDate().toString() : obj.getString("notifyDate");
		Boolean isRead = obj.isNull("isRead") ? optional.get().getIsRead() : obj.getBoolean("isRead");

		if(optional.isPresent()) {
			Notification update = optional.get();
			update.setContent(content);
			update.setNotifyDate(DatetimeConverter.parse(notifyDate, "yyyy-MM-dd"));
			update.setIsRead(isRead);
			return notificationRepo.save(update);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
}
