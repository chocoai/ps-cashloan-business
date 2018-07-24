package com.adpanshi.cashloan.business.cl.model.pay.lianlian;
/***
 ** @category 实时付-连连订单记录...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日下午4:26:02
 **/
public class InstantOrderRecordModel extends QueryPaymentModel{

	/**响应结果*/
	private String returnParams;
	
	public InstantOrderRecordModel(){}
	
	public InstantOrderRecordModel(String orderNo){
		super(orderNo);
	}
	
	public InstantOrderRecordModel(String orderNo,String noOrder){
		this(orderNo);
		setNo_order(noOrder);
	}
	
	public String getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(String returnParams) {
		this.returnParams = returnParams;
	}
	
}
