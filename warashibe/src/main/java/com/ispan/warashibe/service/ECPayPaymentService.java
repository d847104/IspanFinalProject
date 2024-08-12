package com.ispan.warashibe.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class ECPayPaymentService {
	
	public String genAioCheckOutALL(String json){
		JSONObject jsonobj = new JSONObject(json);
		String merchantTradeNo = jsonobj.isNull("MerchantTradeNo")? null : jsonobj.getString("MerchantTradeNo");
		String totalAmount = jsonobj.isNull("TotalAmount")? null : jsonobj.getString("TotalAmount");
		String tradeDesc = jsonobj.isNull("TradeDesc")? null : jsonobj.getString("TradeDesc");
		String itemName = jsonobj.isNull("ItemName")? null : jsonobj.getString("ItemName");
		String clientBackURL = jsonobj.isNull("ClientBackURL")? null : jsonobj.getString("ClientBackURL");
		
		AllInOne all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(merchantTradeNo);
		obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		obj.setTotalAmount(totalAmount);
		obj.setTradeDesc(tradeDesc);
		obj.setItemName(itemName);
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setNeedExtraPaidInfo("N");
		obj.setClientBackURL(clientBackURL);
		String form = all.aioCheckOut(obj, null);
		return form;
	}
}
