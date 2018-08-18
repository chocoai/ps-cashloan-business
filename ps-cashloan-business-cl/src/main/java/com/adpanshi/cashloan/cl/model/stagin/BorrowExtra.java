package com.adpanshi.cashloan.cl.model.stagin;

/***
 ** @category 订单扩展统计(多个订单统计)...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月29日下午8:29:06
 **/
public class BorrowExtra {

	/**实际到账金额*/
	private double realAmounts;
	
	/**服务费*/
	private double serviceFees;
	
	/**信息认证费*/
	private double infoAuthFees;
	
	/**综合费用(借款利息)*/
	private double fees;

	/**逾期费*/
	private double penaltyAmounts;
	
	/**提前还款-手续费*/
	private double earlyFee;
	
	public double getRealAmounts() {
		return realAmounts;
	}

	public void setRealAmounts(double realAmounts) {
		this.realAmounts = realAmounts;
	}

	public double getServiceFees() {
		return serviceFees;
	}

	public void setServiceFees(double serviceFees) {
		this.serviceFees = serviceFees;
	}

	public double getInfoAuthFees() {
		return infoAuthFees;
	}

	public void setInfoAuthFees(double infoAuthFees) {
		this.infoAuthFees = infoAuthFees;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public double getPenaltyAmounts() {
		return penaltyAmounts;
	}

	public void setPenaltyAmounts(double penaltyAmounts) {
		this.penaltyAmounts = penaltyAmounts;
	}

	public double getEarlyFee() {
		return earlyFee;
	}

	public void setEarlyFee(double earlyFee) {
		this.earlyFee = earlyFee;
	}
	
}
