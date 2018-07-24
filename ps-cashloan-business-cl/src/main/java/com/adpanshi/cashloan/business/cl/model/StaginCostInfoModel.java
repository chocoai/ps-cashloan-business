package com.adpanshi.cashloan.business.cl.model;
/***
 ** @category 分期费用说明...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2018年1月3日下午4:47:23
 **/
public class StaginCostInfoModel {
	
	/**勾选分期总本金*/
	private double amount;

	/**综合费用(借款利息)*/
	private double fees;

	/**逾期费*/
	private double penaltyAmounts;
	
	/**提前还款-手续费*/
	private double earlyFee;
	
	/**合计*/
	private double totalAmount;
	
	/**总期数*/
	private Long stages;
	
	/**勾选分期区间值*/
	private String checkedStageRange;
	
	public StaginCostInfoModel(){}
	
	public StaginCostInfoModel(double amount,double fees,double penaltyAmounts,double earlyFee,double totalAmount,Long stages,String checkedStageRange){
		this.amount=amount;
		this.fees=fees;
		this.penaltyAmounts=penaltyAmounts;
		this.earlyFee=earlyFee;
		this.totalAmount=totalAmount;
		this.stages=stages;
		this.checkedStageRange=checkedStageRange;
	}
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getStages() {
		return stages;
	}

	public void setStages(Long stages) {
		this.stages = stages;
	}

	public String getCheckedStageRange() {
		return checkedStageRange;
	}

	public void setCheckedStageRange(String checkedStageRange) {
		this.checkedStageRange = checkedStageRange;
	}
	
}
