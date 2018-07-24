package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 连连支付类型（1.分期付(代扣-银行卡还款)、2.实时付）...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日下午2:46:10
 **/
public enum LianlianPayTypeEnum implements ICommonEnum{

	/**分期付（是用户还款）*/
	STAGES_PAY(1,"分期付"),
	/**实时付（是转账给用户）*/
	INSTANT_PAY(2,"实时付");
	/**银行卡还款(代扣) */
//	BANK_CARD_REPAYMENT(3,"代扣");
	
	
	private Integer key;
	
	private String name;
	
	private LianlianPayTypeEnum(Integer key, String name){
		this.key = key;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String getCode() {
		return String.valueOf(key);
	}

	@Override
	public Integer getKey() {
		return key;
	}
	
}
