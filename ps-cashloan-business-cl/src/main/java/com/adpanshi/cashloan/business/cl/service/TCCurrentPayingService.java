package com.adpanshi.cashloan.business.cl.service;

import java.util.Map;

public interface TCCurrentPayingService {
	
	 void save(Map<String, String> currentPayingMap, String orderId, Long userId, String phone);

}
