package com.adpanshi.cashloan.business.core.enjoysign.beans;

import java.util.List;

/***
 ** @category 签署方（比如:甲乙丙丁方）...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午7:47:51
 **/
public class Signatory {
	
	/**
	 * 签署方（比如:甲方）
	 * */
	private String party;
	
	/**
	 * 签署顺序(下标从0开始，值越小签名越靠前)
	 * */
    private Integer sigerIndex;
    
    /**
     * 签署方待签名的数据（变量集）
     * */
    private List<SignData> signDatas;
    
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public Integer getSigerIndex() {
		return sigerIndex;
	}
	public void setSigerIndex(Integer sigerIndex) {
		this.sigerIndex = sigerIndex;
	}
	public List<SignData> getSignDatas() {
		return signDatas;
	}
	public void setSignDatas(List<SignData> signDatas) {
		this.signDatas = signDatas;
	}
    

}
