package com.ispan.warashibe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.warashibe.model.ProductPayMethod;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.repository.ProductPayMethodRepository;
import com.ispan.warashibe.repository.ProductRepository;
@Service

public class ProductPayMethodService {

	@Autowired
	private ProductPayMethodRepository productPayMethodRepo;
	
	@Autowired
    private ProductRepository productsRepo;  // 添加对 ProductsRepository 的注入

    public List<ProductPayMethod> findByProductID(Integer productID) {
        Optional<Products> productOptional = productsRepo.findById(productID);
        if (productOptional.isPresent()) {
            return productPayMethodRepo.findByProduct(productOptional.get());
        } else {
            return new ArrayList<>(); // 如果未找到产品，返回空列表
        }
    }
	
	public ProductPayMethod create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
			Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");
			
//			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(id);
			if (id == null) {
				ProductPayMethod insert = new ProductPayMethod();
				insert.setId(id);
				insert.setPayMethodID(payMethodID);
				insert.setProductID(productID);
						
				return productPayMethodRepo.save(insert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ProductPayMethod insert(ProductPayMethod bean) {
		if (bean != null && bean.getId() != null) {
			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(bean.getId());
			if (optional.isEmpty()) {
				return productPayMethodRepo.save(bean);
			}
		}
		return null;
	}
	
	public ProductPayMethod modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			Integer id = obj.isNull("id") ? null : obj.getInt("id");
			Integer payMethodID = obj.isNull("payMethodID") ? null : obj.getInt("payMethodID");
			Integer productID = obj.isNull("productID") ? null : obj.getInt("productID");

			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(id);
			if (optional.isPresent()) {
				ProductPayMethod update = optional.get();
				update.setId(id);
				update.setPayMethodID(payMethodID);
				update.setProductID(productID);
				return productPayMethodRepo.save(update);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductPayMethod update(ProductPayMethod bean) {
		if(bean != null && bean.getId()!=null) {
			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(bean.getId());
			if(optional.isPresent()) {
				return productPayMethodRepo.save(bean);
			}
		}return null;
	}
	
	public boolean remove(Integer id) {
		if (id != null) {
			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(id);
			if (optional.isPresent()) {
				productPayMethodRepo.deleteById(id);
				return true;
			}
		}
		return false;
	}

	public boolean delete(ProductPayMethod bean) {
		if(bean != null && bean.getId()!=null) {
			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(bean.getId());
			if(optional.isPresent()) {
				productPayMethodRepo.deleteById(bean.getId());
				return true;
			}
		}return false;
	}
	
	public List<ProductPayMethod> find(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productPayMethodRepo.find(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ProductPayMethod> select(ProductPayMethod bean){
		List<ProductPayMethod> result= null;
		if(bean!=null && bean.getId()!=null && !bean.getId().equals(0)) {
			Optional<ProductPayMethod> optional = productPayMethodRepo.findById(bean.getId());
			if(optional.isPresent()) {
				result= new ArrayList<ProductPayMethod>();
				result.add(optional.get());
			}
		}else {
			result=productPayMethodRepo.findAll();
		}return result;
	}
	
	public boolean exists(Integer id) {
		if(id!=null) {
			return productPayMethodRepo.existsById(id);
		}return false;
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return productPayMethodRepo.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ProductPayMethod findById(Integer id) {
		return productPayMethodRepo.findById(id).orElse(null);
	}

}
