package com.adpanshi.cashloan.business.core.enjoysign.beans;

import java.util.List;

/***
 ** @category 合同签署状态结果集(如果签署成功)
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月27日下午3:30:26
 **/
public class SignstatusResult {
	
	/**1号签分配给平台的appId**/
	private String apId;
	
	/**合同签署状态(取值范围：0=草稿、1=编辑完成（待签署）、2=作废、3=拒签、4=已成功、5=已过期)*/
	private String signStatus;
	
	/**订单号*/
	private String orderId;
	
	/**
	 * <p>签署状态列表包括:</p>
	 * a:account：签署人的account
	 * b.status:   签署状态
	 * 	(取值范围：0=待签署：还没轮到本人签署、1=签署中：该本人签署、2=已签署、3=拒签)
		注意：返回值在将来的版本中可能会输出更多的信息，所以调用端解析返回值时，请注意版本的兼容。
	 * */
	private String signatories;
	
	private List<?> signstatusList;

	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSignatories() {
		return signatories;
	}

	public void setSignatories(String signatories) {
		this.signatories = signatories;
	}

	public List<?> getSignstatusList() {
		return signstatusList;
	}

	public void setSignstatusList(List<?> signstatusList) {
		this.signstatusList = signstatusList;
	}
	
}
