package com.adpanshi.cashloan.business.core.enjoysign.beans;
/***
 *@category 乙方(出借人)
 *@author qing.yunhui
 *@email: qingyunhui@smoney.cc
 *@createTime: 2017年9月26日下午8:48:07
 **/
public class PartyB {
	
	private String lenderName;//出借人:
	private String lenderIDCard;//出借人身份证号:
	private String lenderPartySignatureDate;//乙方签章日期(YYYY年MM月dd日)
	private String lenderPartySign;//乙方签名:
	
	//column ----end
	
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getLenderIDCard() {
		return lenderIDCard;
	}
	public void setLenderIDCard(String lenderIDCard) {
		this.lenderIDCard = lenderIDCard;
	}
	public String getLenderPartySign() {
		return lenderPartySign;
	}
	public void setLenderPartySign(String lenderPartySign) {
		this.lenderPartySign = lenderPartySign;
	}
	public String getLenderPartySignatureDate() {
		return lenderPartySignatureDate;
	}
	public void setLenderPartySignatureDate(String lenderPartySignatureDate) {
		this.lenderPartySignatureDate = lenderPartySignatureDate;
	}
	
}
