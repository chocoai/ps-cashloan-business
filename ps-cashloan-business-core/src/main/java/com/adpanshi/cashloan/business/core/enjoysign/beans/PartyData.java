package com.adpanshi.cashloan.business.core.enjoysign.beans;

/***
 ** @category 签署方集...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月28日下午8:05:09
 **/
public class PartyData {

	private PartyA partyA;
	
	private PartyB partyB;
	
	private PartyC partyC;
	
	/**请求状态(PartyDataEnum.FIRST_AGAIN,PartyDataEnum.FAIL)**/
	private Integer againStatus;// 参考 PartyDataEnum
	
	public PartyData(){}
	
	public PartyData(PartyA partyA,PartyB partyB,PartyC partyC){
		this.partyA=partyA;
		this.partyB=partyB;
		this.partyC=partyC;
	}
	
	public PartyData(PartyA partyA,PartyB partyB,PartyC partyC,Integer againStatus){
		this.partyA=partyA;
		this.partyB=partyB;
		this.partyC=partyC;
		this.againStatus=againStatus;
	}

	public PartyA getPartyA() {
		return partyA;
	}

	public void setPartyA(PartyA partyA) {
		this.partyA = partyA;
	}

	public PartyB getPartyB() {
		return partyB;
	}

	public void setPartyB(PartyB partyB) {
		this.partyB = partyB;
	}

	public PartyC getPartyC() {
		return partyC;
	}

	public void setPartyC(PartyC partyC) {
		this.partyC = partyC;
	}

	public Integer getAgainStatus() {
		return againStatus;
	}

	public void setAgainStatus(Integer againStatus) {
		this.againStatus = againStatus;
	}
	
}
