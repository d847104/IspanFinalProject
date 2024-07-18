package com.ispan.warashibe.dao;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.ispan.warashibe.model.ProductPayMethod;


public interface ProductPayMethodDAO {
	public abstract List<ProductPayMethod> find(JSONObject obj) throws JSONException;
	public abstract long count(JSONObject obj) throws JSONException;
}
