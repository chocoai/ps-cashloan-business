package com.adpanshi.cashloan.business.cl.domain;
/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月9日下午5:51:49
 **/
public class StagesDetail {

	private String stagesDate;//第N期还款日
	private String stagesInterest;//第N期还款本息
	
	public StagesDetail(){}

	public StagesDetail(String stagesDate,String stagesInterest){
		this.stagesDate=stagesDate;
		this.stagesInterest=stagesInterest;
	}
	
	public String getStagesDate() {
		return stagesDate;
	}
	public void setStagesDate(String stagesDate) {
		this.stagesDate = stagesDate;
	}
	public String getStagesInterest() {
		return stagesInterest;
	}
	public void setStagesInterest(String stagesInterest) {
		this.stagesInterest = stagesInterest;
	}
	
}
