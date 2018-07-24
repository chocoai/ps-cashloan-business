package com.adpanshi.cashloan.business.cl.model;

import java.io.Serializable;

/***
 ** @category 分期详情...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月25日上午11:30:49
 **/
public class StaginModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 分期金额
	 * */
	private Double amount;
	
	/**
	 * 银行名称
	 * */
    private String bank;
    
    /**
     * 银行卡号
     * */
    private String cardNo;
    
    /**
     * 分期总利息
     * */
    private Double fee;
    
    /**
     * 主订单id
     * */
    private Long borMainId;
    
    /**
     * 主订单号 
     * */
    private String borMainOrderNo;
    
    /**合同期限*/
    private String contractLimit;
	
    /**还款方式*/
    private String payType;
    
    /**
     * 分期状态
     * */
    private String state;
    
    /**借款期限*/
    private String timeLimit;
    
    public StaginModel(Double amount,String bank,String cardNo,Double fee,Long borMainId,String borMainOrderNo){
    	this.amount=amount;
    	this.bank=bank;
    	this.cardNo=cardNo;
    	this.fee=fee;
    	this.borMainId=borMainId;
    	this.borMainOrderNo=borMainOrderNo;
    }
    
    public StaginModel(Double amount,String bank,String cardNo,Double fee,Long borMainId,String borMainOrderNo,String contractLimit,String payType){
    	this(amount, bank, cardNo, fee, borMainId, borMainOrderNo);
    	this.contractLimit=contractLimit;
    	this.payType=payType;
    }
    
    public StaginModel(){}
    

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Long getBorMainId() {
		return borMainId;
	}

	public void setBorMainId(Long borMainId) {
		this.borMainId = borMainId;
	}

	public String getBorMainOrderNo() {
		return borMainOrderNo;
	}

	public void setBorMainOrderNo(String borMainOrderNo) {
		this.borMainOrderNo = borMainOrderNo;
	}

	public String getContractLimit() {
		return contractLimit;
	}

	public void setContractLimit(String contractLimit) {
		this.contractLimit = contractLimit;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	
}
