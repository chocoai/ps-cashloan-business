package com.adpanshi.cashloan.business.core.enjoysign.beans;
/***
 *@category 甲方(借款人)...
 *@author qing.yunhui
 *@email: qingyunhui@smoney.cc
 *@createTime: 2017年9月26日下午8:47:43
 **/
public class PartyA {
	
	private String borName;//借款人:
	private String borIDCard;//借款人身份证号:
	private String borPhone;//借款人手机号:
	private String borEmail;//借款人邮箱:
	private String borSignatureDate;//甲方签章日期(YYYY年MM月dd日)：
	private String borSignature;//甲签章(姓名):
	public String getBorName() {
		return borName;
	}
	public void setBorName(String borName) {
		this.borName = borName;
	}
	public String getBorIDCard() {
		return borIDCard;
	}
	public void setBorIDCard(String borIDCard) {
		this.borIDCard = borIDCard;
	}
	public String getBorPhone() {
		return borPhone;
	}
	public void setBorPhone(String borPhone) {
		this.borPhone = borPhone;
	}
	public String getBorEmail() {
		return borEmail;
	}
	public void setBorEmail(String borEmail) {
		this.borEmail = borEmail;
	}
	
	public String getBorSignatureDate() {
		return borSignatureDate;
	}
	public void setBorSignatureDate(String borSignatureDate) {
		this.borSignatureDate = borSignatureDate;
	}
	public String getBorSignature() {
		return borSignature;
	}
	public void setBorSignature(String borSignature) {
		this.borSignature = borSignature;
	}
	
	
	
}
