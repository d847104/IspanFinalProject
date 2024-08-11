package com.ispan.warashibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.service.ECPayLogisticService;

@RestController
@RequestMapping("/api/ECPayLogistic")
@CrossOrigin
public class ECPayLogisticController {
	
	@Autowired
	ECPayLogisticService eCPayLogisticServ;
	
	@PostMapping("/expressMap")
	public String postMethodName(@RequestBody String body) {
		String expressMap = eCPayLogisticServ.postExpressMap(body);
		return expressMap;
	}
	
}
