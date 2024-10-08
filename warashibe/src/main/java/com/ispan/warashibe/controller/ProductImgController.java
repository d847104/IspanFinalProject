package com.ispan.warashibe.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.warashibe.model.ProductImg;
import com.ispan.warashibe.service.ProductImgService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/productImg")
@CrossOrigin
public class ProductImgController {

	@Autowired
	private ProductImgService productImgService;

	@Autowired
	private ObjectMapper objMapper;

	@PostMapping
	public String saveProductImg(@RequestParam("jsonProduct") String jsonProduct,
			@RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
		JSONObject responseBody = new JSONObject();
		try {
			productImgService.saveProductImgFromJson(jsonProduct, image);
			responseBody.put("success", true);
			responseBody.put("message", "新增成功");
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "新增失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}

	@PutMapping("/{id}")
	public String updateProductImg(@PathVariable Integer id, @RequestParam("jsonProduct") String jsonProduct,
			@RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
		JSONObject responseBody = new JSONObject();
		try {
			productImgService.updateProductImgFromJson(id, jsonProduct, image);
			responseBody.put("success", true);
			responseBody.put("message", "更新成功");
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "更新失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}

	@DeleteMapping("/{id}")
	public String deleteProductImg(@PathVariable Integer id) throws JSONException {
		JSONObject responseBody = new JSONObject();
		try {
			productImgService.deleteProductImg(id);
			responseBody.put("success", true);
			responseBody.put("message", "刪除成功");
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "刪除失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}

	@GetMapping("/{id}")
	public String getProductImgById(@PathVariable Integer id) throws JSONException {
		JSONObject responseBody = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ProductImg productImg = productImgService.getProductImgById(id);
			responseBody.put("success", true);
			responseBody.put("list", new JSONObject(objectMapper.writeValueAsString(productImg)));
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "查詢失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}

	@GetMapping("/all")
	public String getAllProductImgs() throws JSONException {
		JSONObject responseBody = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<ProductImg> productImgs = productImgService.getAllProductImgs();
			responseBody.put("success", true);
			responseBody.put("count", productImgs.size());

			JSONArray productImgList = new JSONArray();
			for (ProductImg productImg : productImgs) {
				JSONObject item = new JSONObject(objectMapper.writeValueAsString(productImg));
				productImgList.put(item);
			}

			responseBody.put("list", productImgList);
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "查詢失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}

	@GetMapping(path = "/img/{id}", produces = { MediaType.IMAGE_JPEG_VALUE })
	public @ResponseBody byte[] getImgById(@PathVariable Integer id) throws Exception {
		ProductImg img = productImgService.getProductImgById(id);
		byte[] result = this.img;
		if (img != null) {
			result = img.getImg();
		}
		return result;
	}

	private byte[] img = null;

	@PostConstruct
	public void initialize() throws IOException {
		byte[] buffer = new byte[8192];
		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(
				classLoader.getResourceAsStream("static/images/img-coming-soon.jpg"));
		int len = is.read(buffer);
		while (len != -1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		this.img = os.toByteArray();
	}
	
	@GetMapping(path = "/img/comingsoon", produces = { MediaType.IMAGE_JPEG_VALUE })
	public @ResponseBody byte[] noImg() {
		return this.img;
	}

	@GetMapping("/product/{id}")
	public String getProductImg(@PathVariable Integer id) throws JSONException, JsonProcessingException {
		JSONObject responseBody = new JSONObject();
		JSONArray array = new JSONArray();
		List<ProductImg> result = productImgService.getProductImgByProductId(id);
		for (ProductImg img : result) {
			if (img != null) {
				array.put(new JSONObject(objMapper.writeValueAsString(img)));
			}
		}
		responseBody.put("list", array);
		return responseBody.toString();
	}

	@DeleteMapping("/product/{productId}")
	public String deleteAllProductImgsByProductId(@PathVariable Integer productId) throws JSONException {
		JSONObject responseBody = new JSONObject();
		try {
			productImgService.deleteAllProductImgsByProductId(productId);
			responseBody.put("success", true);
			responseBody.put("message", "刪除所有圖片成功");
		} catch (Exception e) {
			responseBody.put("success", false);
			responseBody.put("message", "刪除所有圖片失敗: " + e.getMessage());
		}
		return responseBody.toString();
	}
}
