package com.adpanshi.cashloan.cl.model;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.util.MathUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.rule.model.BorrowTemplateModel;

/***
 ** @category 借款模板...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月23日上午11:13:11
 **/
public class TemplateInfoModel {
	
	/**总期数*/
	private Long stages;
	
	/**每期还款本金*/
	private Double amount;
	
	/**每期利息*/
	private Double interest; 
	
	/**还款方式*/
	private String payType;
	
	/**日利率*/
	private Double dayRate;
	
	/**
	 * 借款期限
	 * */
	private String timeLimit; 
	
	/**分期周期(天)*/
	private Long cycle;
	
	public TemplateInfoModel(){}
	
	public TemplateInfoModel(Long stages, Double amount, Double interest, String payType, String timeLimit){
		this.stages=stages;
		this.amount=amount;
		this.interest=interest;
		this.payType=payType;
		this.timeLimit=timeLimit;
	}
	
	public TemplateInfoModel(Long stages, Double amount, Double interest, String payType, String timeLimit, Double dayRate, Long cycle){
		this(stages, amount, interest, payType, timeLimit);
		this.dayRate=dayRate;
		this.cycle=cycle;
	}
	
	/**
	 * <p>根据给定模板进行解释</p>
	 * @param borrowAmount
	 * @param templateInfo
	 * @return 
	 * */
	public TemplateInfoModel parseTemplateInfo(Double borrowAmount, String templateInfo){
		if(StringUtil.isBlank(templateInfo)) {return null;}
		BorrowTemplateModel template = JSONObject.parseObject(templateInfo, BorrowTemplateModel.class);
		Long cycle = Long.parseLong(template.getCycle());
		Long timeLimit = Long.parseLong(template.getTimeLimit());
		Long stages = timeLimit / cycle;
		Double amount=MathUtil.div(borrowAmount, stages.doubleValue(),2);
		Double interest=MathUtil.div(template.getFee(), stages.doubleValue(),2);
		return new TemplateInfoModel(stages, amount, interest,null==template.getPayType()?"":template.getPayType().getName(),template.getTimeLimit(),template.getDayRate(),cycle);
	}

	public Long getStages() {
		return stages;
	}

	public void setStages(Long stages) {
		this.stages = stages;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Double getDayRate() {
		return dayRate;
	}

	public void setDayRate(Double dayRate) {
		this.dayRate = dayRate;
	}

	public Long getCycle() {
		return cycle;
	}

	public void setCycle(Long cycle) {
		this.cycle = cycle;
	} 
	
}
