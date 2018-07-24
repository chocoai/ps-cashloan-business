package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.rule.domain.SaasRespRecord;
import com.adpanshi.cashloan.business.rule.enums.SaasServiceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//import com.fans.com.adpanshi.com.adpanshi.cashloan.api.socialAndPay.SocialPayClient;
//import com.fans.com.adpanshi.com.adpanshi.cashloan.api.socialAndPay.SocialPayParams;

/***
 ** @category 统一调用saas服务接口...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月3日下午2:24:52
 **/
@Service("callSaasService")
public class CallSaasServiceImpl implements CallSaasService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public SaasRespRecord getSaasRespRecord(Long userId,String reqId, String resId,String taskId, String code, String
			msg, String data,Integer type) {
		SaasRespRecord record=new SaasRespRecord(userId,reqId, resId, taskId,SaasServiceEnum.STATE.SUBMIT.getCode(),
				code, msg, data,type);
		return record;
	}

}
