package com.ispan.warashibe.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ispan.warashibe.model.Products;
import com.ispan.warashibe.model.Rank;
import com.ispan.warashibe.service.RankService;
import com.ispan.warashibe.util.JsonUtil;

@RestController
@RequestMapping("/api/ranks")
@CrossOrigin
public class RankController {
    @Autowired
    private RankService rankService;

    @PostMapping("/search")
    public String searchRanks(@RequestBody String request) throws JSONException, JsonProcessingException, ParseException {
        JSONObject jsonObject = new JSONObject(request);
        Integer ranking = jsonObject.optInt("ranking", -1); // 默認值為 -1
        if (ranking == -1) {
            JSONObject response = new JSONObject();
            response.put("success", false);
            response.put("message", "Ranking parameter is required");
            return response.toString();
        }
        Pageable pageable = createPageRequest(jsonObject);
        List<Rank> ranks = rankService.searchRanksByRanking(ranking, pageable);
        
        return createResponse(ranks);
    }

    @GetMapping("/{id}")
    public String getRankById(@PathVariable Integer id) throws JSONException, JsonProcessingException {
        Rank rank = rankService.findById(id);
        if (rank != null) {
            JSONObject jsonRank = new JSONObject(JsonUtil.toJson(rank));
            return jsonRank.toString();
        } else {
            JSONObject response = new JSONObject();
            response.put("success", false);
            response.put("message", "Rank not found");
            return response.toString();
        }
    }

    @PostMapping
    public String createRank(@RequestBody String jsonRank) throws JSONException, JsonProcessingException {
        JSONObject response = new JSONObject();
        try {
            rankService.save(jsonRank);
            response.put("success", true);
            response.put("message", "Rank created successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to create rank: " + e.getMessage());
        }
        return response.toString();
    }

    @PutMapping("/{id}")
    public String updateRank(@PathVariable Integer id, @RequestBody String jsonRank) throws JSONException, JsonProcessingException {
        JSONObject response = new JSONObject();
        try {
            rankService.update(id, jsonRank);
            response.put("success", true);
            response.put("message", "Rank updated successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update rank: " + e.getMessage());
        }
        return response.toString();
    }

    @DeleteMapping("/{id}")
    public String deleteRank(@PathVariable Integer id) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            rankService.delete(id);
            response.put("success", true);
            response.put("message", "Rank deleted successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to delete rank: " + e.getMessage());
        }
        return response.toString();
    }
    
    
    @GetMapping("/member/{memberID}")
    public String getRanksByMemberID(@PathVariable int memberID) throws JSONException, JsonProcessingException, ParseException {
        List<Rank> ranks = rankService.getRanksByMemberID(memberID);
        return createResponse(ranks);
    }
 
    private Pageable createPageRequest(JSONObject request) throws JSONException {
        int start = request.optInt("start", 0); // 默認值為0
        int max = request.optInt("max", 30); // 默認值為30
        boolean dir = request.optBoolean("dir", false); // 默認值為false
        String order = request.optString("order", "rankID"); // 默認排序字段為rankID

        Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(start, max, Sort.by(direction, order));
    }

    private String createResponse(List<Rank> ranks) throws JSONException, JsonProcessingException, ParseException {
        JSONObject responseBody = new JSONObject();
        responseBody.put("count", ranks.size());

        JSONArray rankList = new JSONArray();
        for (Rank rank : ranks) {
        	// 将 Rank 对象转换为 JSONObject
        	String item = JsonUtil.toJson(rank);
        	// 將 JSON 字符串轉換為 JSONObject
            JSONObject jsonObject = JsonUtil.toJSONObject(item);
            rankList.put(jsonObject);
        }
        
        responseBody.put("list", rankList);
        return responseBody.toString()
        		.replace("\\", "")
        		.replace("\"[\"", "[")
        		.replace("}\",\"{", "},{")
        		.replace("\"]\"", "]"); // 防呆
    }

}
