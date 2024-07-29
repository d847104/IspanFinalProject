package com.ispan.warashibe.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.ispan.warashibe.model.ProductPayMethod;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Repository
public class ProductPayMethodDAOImpl implements ProductPayMethodDAO{
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}
	
	@Override
	public List<ProductPayMethod> find(JSONObject obj) throws JSONException {
		Integer id = obj.isNull("id") ? null : obj.getInt("id");
		Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
		Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");

		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int max = obj.isNull("max") ? 100 : obj.getInt("max");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		String payMethodID1 = obj.isNull("payMethodID") ? "payMethodID" : obj.getString("payMethodID");
		
		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<ProductPayMethod> criteriaQuery = criterBuilder.createQuery(ProductPayMethod.class);
		
//		from		
		Root<ProductPayMethod> table = criteriaQuery.from(ProductPayMethod.class);
		
//		where		
		List<Predicate> predicates = new ArrayList<>();
		if(id!=null) {
			predicates.add(criterBuilder.equal(table.get("id"), id));
		}
		if(payMethodID!=null) {
			predicates.add(criterBuilder.equal(table.get("payMethodID"), payMethodID));
		}
		if(productID!=null) {
			predicates.add(criterBuilder.equal(table.get("productID"), productID));
		}
		
//		order by
		if(dir) {
			criteriaQuery = criteriaQuery.orderBy(criterBuilder.asc(table.get(payMethodID1)));
		}else{
			criteriaQuery =criteriaQuery.orderBy(criterBuilder.desc(table.get(payMethodID1)));
		}
		
		TypedQuery<ProductPayMethod> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(max);
		List<ProductPayMethod> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
			}else {
				return null;
			}
		}
	
	@Override
	public long count(JSONObject obj) throws JSONException {
		Integer id = obj.isNull("id") ? null : obj.getInt("id");
		Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
		Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");

		CriteriaBuilder criterBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);
//		from		
		Root<ProductPayMethod> table = criteriaQuery.from(ProductPayMethod.class);
		
//		select count(*)
		criteriaQuery = criteriaQuery.select(criterBuilder.count(table));
		
//		where		
		List<Predicate> predicates = new ArrayList<>();
		if(id!=null) {
			predicates.add(criterBuilder.equal(table.get("id"), id));
		}
		if(payMethodID!=null) {
			predicates.add(criterBuilder.equal(table.get("payMethodID"), payMethodID));
		}
		if(productID!=null) {
			predicates.add(criterBuilder.equal(table.get("productID"), productID));
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
