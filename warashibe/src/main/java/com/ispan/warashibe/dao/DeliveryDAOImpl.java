package com.ispan.warashibe.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.Delivery;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class DeliveryDAOImpl implements DeliveryDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	@Override
	public List<Delivery> find(JSONObject obj) throws JSONException {
		Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
		String delivery = obj.isNull("delivery") ? null : obj.getString("delivery");
		Integer deliveryFee = obj.isNull("deliveryFee") ? null : obj.getInt("deliveryFee");

		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int max = obj.isNull("max") ? 100 : obj.getInt("max");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		String delivery1 = obj.isNull("delivery") ? "deliveryID" : obj.getString("delivery");
		
		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Delivery> criteriaQuery = criterBuilder.createQuery(Delivery.class);
		
//		from		
		Root<Delivery> table = criteriaQuery.from(Delivery.class);
		
//		where		
		List<Predicate> predicates = new ArrayList<>();
		if(deliveryID!=null) {
			predicates.add(criterBuilder.equal(table.get("deliveryID"), deliveryID));
		}
		if(delivery!=null) {
			predicates.add(criterBuilder.like(table.get("delivery"), "%"+delivery+"%"));
		}
		if(deliveryFee!=null) {
			predicates.add(criterBuilder.equal(table.get("deliveryFee"), deliveryFee));
		}
		
//		order by
		if(dir) {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.asc(table.get(delivery1)));
		}else{
			criteriaQuery =criteriaQuery.orderBy(criterBuilder.desc(table.get(delivery1)));
		}
		
		TypedQuery<Delivery> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(max);
		List<Delivery> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
			}else {
				return null;
			}
		}
//		if(deliveryFee!=null) {
//			predicates.add(criterBuilder.)
//		}

	@Override
	public long count(JSONObject obj) throws JSONException {
		Integer deliveryID = obj.isNull("deliveryID") ? null : obj.getInt("deliveryID");
		String delivery = obj.isNull("delivery") ? null : obj.getString("delivery");
		Integer deliveryFee = obj.isNull("deliveryFee") ? null : obj.getInt("deliveryFee");

		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);
//		from		
		Root<Delivery> table = criteriaQuery.from(Delivery.class);
		
//		select count(*)
		criteriaQuery = criteriaQuery.select(criterBuilder.count(table));
		
//		where		
		List<Predicate> predicates = new ArrayList<>();
		if(deliveryID!=null) {
			predicates.add(criterBuilder.equal(table.get("deliveryID"), deliveryID));
		}
		if(delivery!=null) {
			predicates.add(criterBuilder.like(table.get("delivery"), "%"+delivery+"%"));
		}
		if(deliveryFee!=null) {
			predicates.add(criterBuilder.equal(table.get("deliveryFee"), deliveryFee));
		}
		
		criteriaQuery = criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
		Long result = typedQuery.getSingleResult();
		if(result!=null) {
			return result;
			}else {
				return 0;
		}
	}
}
