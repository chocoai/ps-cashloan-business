package com.adpanshi.cashloan.business.cl.model;
/***
 ** @category 分期还款明细...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月24日下午9:59:30
 **/
public class StaginRepayMode {
	
	/**分期还款金额*/
	private Double amount;
	
	/**分期还款利息*/
	private Double interests;
	
	/**分期还款的逾期金额*/
	private Double penaltyAmout;
	
	/**分期还款的手续费*/
	private Double fee ;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterests() {
		return interests;
	}

	public void setInterests(Double interests) {
		this.interests = interests;
	}

	public Double getPenaltyAmout() {
		return penaltyAmout;
	}

	public void setPenaltyAmout(Double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
}
