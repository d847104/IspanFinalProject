package com.ispan.warashibe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.model.ProductPayMethod;
import com.ispan.warashibe.service.ProductPayMethodService;

@RestController
@CrossOrigin
public class ProductPayMethodController {
	@Autowired
	private ProductPayMethodService proPayMethodService;

	@PostMapping("/productPayMethod")
	public String create(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONObject obj = new JSONObject(body);
		Integer ID = obj.isNull("id") ? null : obj.getInt("id");

		if (proPayMethodService.exists(ID)) {
			responseBody.put("success", false);
			responseBody.put("message", "ID已存在");
		} else {
			proPayMethodService.create(body);
			responseBody.put("success", true);
			responseBody.put("message", "新增成功");
		}

		return responseBody.toString();
	}

	@PutMapping("/productPayMethod/{id}")
	public String modify(@PathVariable Integer id, @RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (id == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!proPayMethodService.exists(id)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				ProductPayMethod productPayMethod = proPayMethodService.modify(body);
				if (productPayMethod == null) {
					responseBody.put("success", false);
					responseBody.put("message", "修改失敗");
				} else {
					responseBody.put("success", true);
					responseBody.put("message", "修改成功");
				}
			}
		}
		return responseBody.toString();
	}

	@DeleteMapping("/productPayMethod/{id}")
	public String remove(@PathVariable Integer id) throws JSONException {
		JSONObject responseBody = new JSONObject();
		if (id == null) {
			responseBody.put("success", false);
			responseBody.put("message", "ID是必要欄位");
		} else {
			if (!proPayMethodService.exists(id)) {
				responseBody.put("success", false);
				responseBody.put("message", "ID不存在");
			} else {
				if (!proPayMethodService.remove(id)) {
					responseBody.put("success", false);
					responseBody.put("message", "刪除失敗");
				} else {
					responseBody.put("success", true);
					responseBody.put("message", "刪除成功");
				}
			}
		}
		return responseBody.toString();
	}

	@PostMapping("/productPayMethod/find")
	public String find(@RequestBody String body) throws JSONException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<ProductPayMethod> productPayMethods = proPayMethodService.find(body);
		if (productPayMethods != null && !productPayMethods.isEmpty()) {
			for (ProductPayMethod productPayMethod : productPayMethods) {
				JSONObject item = new JSONObject().put("id", productPayMethod.getId())
						.put("payMethodID", productPayMethod.getPayMethodID())
						.put("productID", productPayMethod.getProductID());
				array.put(item);
			}
		}
		long count = proPayMethodService.count(body);
		responseBody.put("count", count);
		responseBody.put("list", array);

		return responseBody.toString();
	}

	@GetMapping("/productPayMethod/{id}")
	public String findById(@PathVariable Integer id) throws JSONException {
		JSONObject responseBody = new JSONObject();
		ProductPayMethod productPayMethod = proPayMethodService.findById(id);

		if (productPayMethod != null) {
			JSONObject item = new JSONObject().put("id", productPayMethod.getId())
					.put("payMethodID", productPayMethod.getPayMethodID())
					.put("productID	", productPayMethod.getProductID());
			responseBody.put("productPayMethod", item);
		} else {
			responseBody.put("error", "ProductPayMethod not found");
		}
		return responseBody.toString();
	}
	
	@GetMapping("/productPayMethod/product/{productID}")
	public String findByProductID(@PathVariable Integer productID) throws JSONException {
	    JSONObject responseBody = new JSONObject();
	    JSONArray array = new JSONArray();

	    List<ProductPayMethod> productPayMethods = proPayMethodService.findByProductID(productID);

	    if (productPayMethods != null && !productPayMethods.isEmpty()) {
	        for (ProductPayMethod productPayMethod : productPayMethods) {
	            JSONObject item = new JSONObject()
	                    .put("id", productPayMethod.getId())
	                    .put("payMethodID", productPayMethod.getPayMethodID().getPayMethodID())
	                    .put("productID", productPayMethod.getProductID().getProductID());
	            array.put(item);
	        }
	        responseBody.put("list", array);
	        responseBody.put("success", true);
	    } else {
	        responseBody.put("message", "No PayMethod found for productID: " + productID);
	        responseBody.put("success", false);
	    }
	    return responseBody.toString();
	}
}
