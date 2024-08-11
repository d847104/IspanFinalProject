package com.ispan.warashibe.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import ecpay.logistics.integration.AllInOne;
import ecpay.logistics.integration.domain.ExpressMapObj;

@Service
public class ECPayLogisticService {
	
	//	超商電子地圖
	public String postExpressMap(String json){
		JSONObject jsonobj = new JSONObject(json);
		String logisticsSubType = jsonobj.isNull("LogisticsSubType")? null : jsonobj.getString("LogisticsSubType");
		String isCollection = jsonobj.isNull("IsCollection")? null : jsonobj.getString("IsCollection");
		AllInOne all = new AllInOne();
		ExpressMapObj obj = new ExpressMapObj();
		obj.setLogisticsSubType(logisticsSubType);
		obj.setIsCollection(isCollection);
		obj.setServerReplyURL("https://www.yahoo.com.tw");
		return all.expressMap(obj);
	}
}
