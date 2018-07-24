package com.adpanshi.cashloan.business.core.model;

import java.util.List;

/***
 ** @category 分期详情...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午3:10:27
 **/
public class StaginDetailModel {
	
	/**
	 * 分期进度
	 * */
	private List<?> staginProgress;
	
	/**
	 * 分期明细
	 * */
	private Object stagin;
	
	/**
	 * 还款明细
	 * */
	private Object repayment ;
	
	/**服务电话*/
	private String serviceCall;

	/**
	 * 是否可以显示验证码提示框（已经支付消贷同城验证码费用）
	 */
	private boolean needCode = false;

	/**
	 * 当前用户剩余可用次数
	 */
	private Integer remainCount;

	/**
	 * 用户可输入的总次数
	 */
	private Integer totalCount;
	
	public StaginDetailModel(){}
	
	public StaginDetailModel(String serviceCall){
		this.serviceCall=serviceCall;
	}

	public List<?> getStaginProgress() {
		return staginProgress;
	}

	public void setStaginProgress(List<?> staginProgress) {
		this.staginProgress = staginProgress;
	}

	public Object getStagin() {
		return stagin;
	}

	public void setStagin(Object stagin) {
		this.stagin = stagin;
	}

	public Object getRepayment() {
		return repayment;
	}

	public void setRepayment(Object repayment) {
		this.repayment = repayment;
	}

	public String getServiceCall() {
		return serviceCall;
	}

	public void setServiceCall(String serviceCall) {
		this.serviceCall = serviceCall;
	}

	public boolean isNeedCode() {
		return needCode;
	}

	public void setNeedCode(boolean needCode) {
		this.needCode = needCode;
	}

	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
