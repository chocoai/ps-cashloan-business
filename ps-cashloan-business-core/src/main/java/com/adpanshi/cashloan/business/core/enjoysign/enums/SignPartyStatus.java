package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 签署方状态（这里签署方状态指:甲乙丙中的任意一方签署状态）...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月30日下午18:15:44
 **/
public enum SignPartyStatus implements ICommonEnum{

	//签署方状态:取值范围：0=待签署：还没轮到本人签署、1=签署中：该本人签署、2=已签署、3=拒签。
	
	/**待签署*/
	WAITING_SIGN (0,"待签署"),
	/**签署中*/
	SIGN_IN(1,"签署中"),
	/**已签署*/
	SIGNED(2,"已签署"),
	/**拒签*/
	REFUSED(3,"拒签");
	
	private Integer key;
	
	private String name;
	
	private SignPartyStatus(Integer key,String name){
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
