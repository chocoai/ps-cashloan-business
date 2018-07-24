package com.adpanshi.cashloan.business.cl.model.pay.lianlian;
/***
 ** @category 分期付-连连订单记录...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日上午10:02:38
 **/
public class StagesOrderRecordModel extends QueryRepaymentModel{
	
	/**响应结果*/
	private String returnParams;
	
	public StagesOrderRecordModel(){}
	
	public StagesOrderRecordModel(String orderNo){
		super(orderNo);
	}
	
	public StagesOrderRecordModel(String orderNo,String noOrder){
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
