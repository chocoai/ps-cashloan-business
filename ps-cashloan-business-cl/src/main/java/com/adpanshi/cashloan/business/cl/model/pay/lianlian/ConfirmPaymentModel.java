package com.adpanshi.cashloan.business.cl.model.pay.lianlian;

import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.PaySecurity;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.SignUtil;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 连连支付 实时付款 - 确认付款
 * 

 * @version 1.0.0
 * @date 2017年3月6日 上午11:27:36 Copyright 粉团网路 All Rights Reserved
 *
 */
public class ConfirmPaymentModel extends BasePaymentModel {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfirmPaymentModel.class);

	/**
	 * 版本号
	 */
	private String api_version;

	/**
	 * 验证码
	 */
	private String confirm_code;

	/**
	 * 加密数据
	 */
	private String pay_load;

	/**
	 * 连连支付支付单
	 */
	private String oid_paybill;

	public ConfirmPaymentModel() {
		super();
	}

	public ConfirmPaymentModel(String orderNo) {
		super();
		this.setOrderNo(orderNo);
		this.setService("confirmPayment");
		this.setApi_version(LianLianConstant.API_VERSION);
		//测试环境，增加代理，如果不配置，则依旧走生产环境 @author yecy 20180328
		String subUrl = "https://instantpay.lianlianpay.com/paymentapi/confirmPayment.htm";
		if ("dev".equals(Global.getValue("app_environment"))) {
			String proxyUrl = Global.getValue("proxy_url");
			if (StringUtils.isNotEmpty(proxyUrl)) {
				subUrl = proxyUrl + "paymentapi/confirmPayment.htm";
			}
		}
		this.setSubUrl(subUrl);
	}

	@Override
	public String[] signParamNames() {
		return new String[] { "oid_partner", "sign_type", "sign", "platform",
				"api_version", "no_order", "confirm_code", "notify_url" };
	}

	@Override
	public String[] reqParamNames() {
		return new String[] { "oid_partner", "pay_load" };
	}

	@Override
	public String[] respParamNames() {
		return new String[] { "ret_code", "ret_msg", "sign_type", "sign",
				"oid_partner", "no_order", "oid_paybill" };
	}

	@Override
	public void sign() {
		Map<String, Object> map = paramToMap(this.signParamNames());
		this.setSign(SignUtil.genRSASign(JSON.toJSONString(map)));

		logger.info("使用连连银通公钥加密");
		Map<String, Object> pubMap = paramToMap(this.signParamNames());
		String encryptStr = PaySecurity.encrypt(JSON.toJSONString(pubMap),
				Global.getValue(LianLianConstant.PUBLIC_KEY));//TODO

		this.setPay_load(encryptStr);
	}

    @Override
    public void newSign() {
        Map<String, Object> map = paramToMap(this.signParamNames());
        this.setSign(SignUtil.newGenRSASign(JSON.toJSONString(map)));

        Map<String, Object> pubMap = paramToMap(this.signParamNames());
        String encryptStr = PaySecurity.encrypt(JSON.toJSONString(pubMap),
                Global.getValue(LianLianConstant.PUBLIC_KEY_R));
        this.setPay_load(encryptStr);
    }

	/**
	 * 获取版本号
	 * 
	 * @return api_version
	 */
	public String getApi_version() {
		return api_version;
	}

	/**
	 * 设置版本号
	 * 
	 * @param api_version
	 */
	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	/**
	 * 获取验证码
	 * 
	 * @return confirm_code
	 */
	public String getConfirm_code() {
		return confirm_code;
	}

	/**
	 * 设置验证码
	 * 
	 * @param confirm_code
	 */
	public void setConfirm_code(String confirm_code) {
		this.confirm_code = confirm_code;
	}

	/**
	 * 获取加密数据
	 * 
	 * @return pay_load
	 */
	public String getPay_load() {
		return pay_load;
	}

	/**
	 * 设置加密数据
	 * 
	 * @param pay_load
	 */
	public void setPay_load(String pay_load) {
		this.pay_load = pay_load;
	}

	/**
	 * 获取连连支付支付单
	 * 
	 * @return oid_paybill
	 */
	public String getOid_paybill() {
		return oid_paybill;
	}

	/**
	 * 设置连连支付支付单
	 * 
	 * @param oid_paybill
	 */
	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

}
