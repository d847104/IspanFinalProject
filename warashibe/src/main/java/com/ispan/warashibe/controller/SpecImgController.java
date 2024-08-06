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
import com.ispan.warashibe.model.SpecImg;
import com.ispan.warashibe.service.SpecImgService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/specImg")
@CrossOrigin
public class SpecImgController {

    @Autowired
    private SpecImgService specImgService;

    @Autowired
    private ObjectMapper objMapper;

    @PostMapping
    public String saveSpecImg(@RequestParam("jsonSpecImg") String jsonSpecImg,
                              @RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            SpecImg specImg = objMapper.readValue(jsonSpecImg, SpecImg.class);
            specImgService.saveSpecImg(specImg, image);
            responseBody.put("success", true);
            responseBody.put("message", "新增成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "新增失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @PutMapping("/{id}")
    public String updateSpecImg(@PathVariable Integer id, @RequestParam("jsonSpecImg") String jsonSpecImg,
                                @RequestParam(value = "image", required = false) MultipartFile image) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            SpecImg specImg = objMapper.readValue(jsonSpecImg, SpecImg.class);
            specImgService.updateSpecImg(id, specImg, image);
            responseBody.put("success", true);
            responseBody.put("message", "更新成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "更新失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteSpecImg(@PathVariable Integer id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            specImgService.deleteSpecImg(id);
            responseBody.put("success", true);
            responseBody.put("message", "刪除成功");
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "刪除失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @GetMapping("/{id}")
    public String getSpecImgById(@PathVariable Integer id) throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            SpecImg specImg = specImgService.getSpecImgById(id);
            responseBody.put("success", true);
            responseBody.put("list", new JSONObject(objMapper.writeValueAsString(specImg)));
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "查詢失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @GetMapping("/all")
    public String getAllSpecImgs() throws JSONException {
        JSONObject responseBody = new JSONObject();
        try {
            List<SpecImg> specImgs = specImgService.getAllSpecImgs();
            responseBody.put("success", true);
            responseBody.put("count", specImgs.size());

            JSONArray specImgList = new JSONArray();
            for (SpecImg specImg : specImgs) {
                JSONObject item = new JSONObject(objMapper.writeValueAsString(specImg));
                specImgList.put(item);
            }

            responseBody.put("list", specImgList);
        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "查詢失敗: " + e.getMessage());
        }
        return responseBody.toString();
    }

    @GetMapping(path = "/img/{id}", produces = { MediaType.IMAGE_JPEG_VALUE })
    public @ResponseBody byte[] getImgById(@PathVariable Integer id) throws Exception {
        SpecImg specImg = specImgService.getSpecImgById(id);
        byte[] result = this.img;
        if (specImg != null) {
            result = specImg.getSpecImg();
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
}
