package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 合同签署状态(这里的合同签署状态是指整份合同的状态,而不是甲乙丙三方中的其中一方)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月30日下午18:00:19
 **/
public enum SingStatusEnum implements ICommonEnum{
	
	//合同签署状态取值范围：0=草稿、1=编辑完成（待签署）、2=作废、3=拒签、4=已成功、5=已过期。
	
	/**草稿*/
	ROUGH_DRAFT(0,"草稿"),
	/**编辑完成*/
	EDIT_FINISH(1,"编辑完成"),
	/**作废*/
	ABOLISH(2,"作废"),
	/**拒签*/
	REFUSED(3,"拒签"),
	/**已成功*/
	SUCCESS(4,"已成功"),
	/**已过期*/
	EXPIRE(5,"已过期");
	
	private Integer key;
	
	private String name;
	
	private SingStatusEnum(Integer key,String name){
		this.key=key;
		this.name=name;
	}

	@Override
	public String getCode() {
		return key.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getKey() {
		return key;
	}
	
}
