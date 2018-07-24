package com.adpanshi.cashloan.business.cl.model.pay.lianlian;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月15日下午4:51:33
 **/
public class OrderRecodeMode extends BasePaymentModel{

	/**响应结果*/
	private String returnParams;
	
	/**
	 * @Fields resultPay:支付结果(SUCCESS:成功、WAITING:等待支付、PROCESSING:银行支付处理中、REFUND:退款、FAILURE:失败)
	 */
	private String resultPay;
	
	
	
	//-------------column end
	
	/**
	 * 是否需要再次发起请求
	 * */
	private String requestAgain ;
	
	/**
	 * 间隔分钟
	 * **/
	private Integer intervalMinute;

	public String getReturnParams() {
		return returnParams;
	}

	public void setReturnParams(String returnParams) {
		this.returnParams = returnParams;
	}

	public String getResultPay() {
		return resultPay;
	}

	public void setResultPay(String resultPay) {
		this.resultPay = resultPay;
	}

	public String getRequestAgain() {
		return requestAgain;
	}

	public void setRequestAgain(String requestAgain) {
		this.requestAgain = requestAgain;
	}

	public Integer getIntervalMinute() {
		return intervalMinute;
	}

	public void setIntervalMinute(Integer intervalMinute) {
		this.intervalMinute = intervalMinute;
	}

}
