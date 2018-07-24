package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.Billing;
import com.adpanshi.cashloan.business.rule.mapper.BillingMapper;
import com.adpanshi.cashloan.business.rule.service.BillingService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

//import com.fans.api.charging.ImmeChargingClient;

@Service("billingService")
public class BillingServiceImpl extends BaseServiceImpl<Billing, Long> implements BillingService {

	private static final Logger logger = LoggerFactory.getLogger(BillingServiceImpl.class);
	
    @Resource
    private BillingMapper billingMapper;
    
	@Override
	public BaseMapper<Billing, Long> getMapper() {
		return billingMapper;
	}


	@Override
	public int saveBillingInfoAndImmeCharging2Sass(long userId, String type) {
		// 到saas平台扣费
		String saaSTransId = immeCharging2Sass();
		
		Billing billing = new Billing();
		billing.setUserId(userId);
		billing.setType(type);
		billing.setCount(1);
		billing.setTransId(saaSTransId);
		billing.setTransactionTime(new Date());
		return billingMapper.save(billing);
	}
	
	/**
	 * 立即到saas平台进行扣费
	 * @return 流水单号
	 */
	private String immeCharging2Sass() {
		String account = Global.getValue("sms_account");// 用户名（必填）
		String secret = Global.getValue("sms_secret");// 密码（必填）
		String chargingTime = ""; // 扣费时间  默认不传设置为当前时间
		String masterUrl = Global.getValue("sms_masterUrl");
		String type = "2"; //2、风控  3、黑名单
		String count = "1"; //计费数量
		
		try {
//			ImmeChargingClient immeChargingClient = new ImmeChargingClient(masterUrl);
//			String reg = immeChargingClient.immeCharging(account, secret, immeChargingClient.getTransactionId(), type, count, chargingTime);
			String reg = "";
			logger.info("提交到saas风控扣费响应：" + reg);
			JSONObject jsonObject = JSONObject.parseObject(reg);
			if (jsonObject.getInteger("result_code") == 0) {
				// 0 默认0为success
				return jsonObject.getString("transId"); // 流水单号
			} else {
				logger.warn("提交到saas风控扣费状态响应异常 ：" + reg);
			}
		} catch (Exception e) {
			logger.error("提交到saas风控扣费出错!!" + e);
		}
		return "";
	}

}
