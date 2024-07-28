package com.ispan.warashibe.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.PayMethod;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PayMethodDAOImpl implements PayMethodDAO {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	@Override
	public List<PayMethod> find(JSONObject obj) throws JSONException {
		Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
		String payMethod = obj.isNull("payMethod") ? null : obj.getString("payMethod");

		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int max = obj.isNull("max") ? 100 : obj.getInt("max");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		String payMethod1 = obj.isNull("payMethod") ? "payMethod" : obj.getString("payMethod");

		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<PayMethod> criteriaQuery = criterBuilder.createQuery(PayMethod.class);
//		from		
		Root<PayMethod> table = criteriaQuery.from(PayMethod.class);

//		where		
		List<Predicate> predicates = new ArrayList<>();
		if (payMethodID != null) {
			predicates.add(criterBuilder.equal(table.get("payMethodID"), payMethodID));
		}
		if (payMethod != null) {
			predicates.add(criterBuilder.like(table.get("payMethod"), "%" + payMethod + "%"));
		}

//		order by
		if (dir) {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.asc(table.get(payMethod1)));
		} else {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.desc(table.get(payMethod1)));
		}

		TypedQuery<PayMethod> typedQuery = this.getSession().createQuery(criteriaQuery).setFirstResult(start)
				.setMaxResults(max);
		List<PayMethod> result = typedQuery.getResultList();
		if (result != null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}

	}

	@Override
	public long count(JSONObject obj) throws JSONException {
		Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
		String payMethod = obj.isNull("payMethod") ? null : obj.getString("payMethod");

		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);
//		from		
		Root<PayMethod> table = criteriaQuery.from(PayMethod.class);

//		select count(*)
		criteriaQuery = criteriaQuery.select(criterBuilder.count(table));

//		where		
		List<Predicate> predicates = new ArrayList<>();
		if (payMethodID != null) {
			predicates.add(criterBuilder.equal(table.get("payMethodID"), payMethodID));
		}
		if (payMethod != null) {
			predicates.add(criterBuilder.like(table.get("payMethod"), "%" + payMethod + "%"));
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
}
