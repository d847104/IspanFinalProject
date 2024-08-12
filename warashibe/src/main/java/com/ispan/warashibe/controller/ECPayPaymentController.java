package com.ispan.warashibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.warashibe.service.ECPayPaymentService;

@RestController
@RequestMapping("/api/ECPayPayment")
@CrossOrigin
public class ECPayPaymentController {
	
	@Autowired
	ECPayPaymentService eCPayPaymentServ;
	
	@PostMapping("/pay")
	public String postMethodName(@RequestBody String body) {
		String payment = eCPayPaymentServ.genAioCheckOutALL(body);
		return payment;
	}
	
}
