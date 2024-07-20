package com.ispan.warashibe.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.Messenger;
import com.ispan.warashibe.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Repository
public class MessengerDAOImpl implements MessengerDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	@Override
	public long count(JSONObject obj) throws JSONException {
		Integer msgID = obj.isNull("msgID") ? null : obj.getInt("msgID");
		Integer senderID = obj.isNull("senderID") ? null : obj.getInt("senderID");
		Integer receiverID = obj.isNull("receiverID") ? null : obj.getInt("receiverID");
		String msg = obj.isNull("msg") ? null : obj.getString("msg");
		String msgTime = obj.isNull("msgTime") ? null : obj.getString("msgTime");

		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);
//		from		
		Root<Messenger> table = criteriaQuery.from(Messenger.class);

//		select count(*)
		criteriaQuery = criteriaQuery.select(criterBuilder.count(table));

//		where		
		List<Predicate> predicates = new ArrayList<>();
		if (msgID != null) {
			predicates.add(criterBuilder.equal(table.get("msgID"), msgID));
		}
		if (senderID != null) {
			predicates.add(criterBuilder.equal(table.get("senderID"), senderID));
		}

		if (receiverID != null) {
			predicates.add(criterBuilder.equal(table.get("receiverID"), receiverID));
		}

		if (msg != null && msg.length() != 0) {
			predicates.add(criterBuilder.equal(table.get("msg"), "%" + msg + "%"));
		}

		if (msgTime != null && msgTime.length() != 0) {
			Date date = DatetimeConverter.parse(msgTime, "yyyy-MM-dd HH:mm:ss EEEE");
			predicates.add(criterBuilder.greaterThan(table.get("msgTime"), date));
		}

		criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
		Long result = typedQuery.getSingleResult();
		if (result != null) {
			return result;
		} else {
			return 0;
		}
	}

	@Override
	public List<Messenger> find(JSONObject obj) throws JSONException {
		Integer msgID = obj.isNull("msgID") ? null : obj.getInt("msgID");
		Integer senderID = obj.isNull("senderID") ? null : obj.getInt("senderID");
		Integer receiverID = obj.isNull("receiverID") ? null : obj.getInt("receiverID");
		String msg = obj.isNull("msg") ? null : obj.getString("msg");
		String msgTime = obj.isNull("msgTime") ? null : obj.getString("msgTime");
		
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int max = obj.isNull("max") ? 5 : obj.getInt("max");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		String messenger1 = obj.isNull("receiverID") ? "receiverID" : obj.getString("receiverID");
		
		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Messenger> criteriaQuery = criterBuilder.createQuery(Messenger.class);
//		from		
		Root<Messenger> table = criteriaQuery.from(Messenger.class);

//		where		
		List<Predicate> predicates = new ArrayList<>();
		if (msgID != null) {
			predicates.add(criterBuilder.equal(table.get("msgID"), msgID));
		}
		if (senderID != null) {
			predicates.add(criterBuilder.equal(table.get("senderID"), senderID));
		}
		
		if (receiverID != null) {
			predicates.add(criterBuilder.equal(table.get("receiverID"), receiverID));
		}
		
		if (msg != null && msg.length()!=0) {
			predicates.add(criterBuilder.equal(table.get("msg"),"%"+ msg+"%"));
		}
		
		if (msgTime != null && msgTime.length()!=0) {
			Date date = DatetimeConverter.parse(msgTime, "yyyy-MM-dd HH:mm:ss EEEE");
			predicates.add(criterBuilder.greaterThan(table.get("msgTime"), date));
		}

		criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
//		order by
		if(dir) {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.desc(table.get(messenger1)));
		} else {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.asc(table.get(messenger1)));
		}
		
		
		TypedQuery<Messenger> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(max);
		List<Messenger> result = typedQuery.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
		return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
