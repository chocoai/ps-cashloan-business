package com.adpanshi.cashloan.business.cl.domain;

import java.util.Date;

public class UserBankBill {
	private int id;
	//消费金额
	private Double amountMoney;
	//对方卡号
	private String oppositeCardNo;
	//备注
	private String remark;
	//排序
	private String orderIndex;
	//记账日期. 格式为yyyy-MM-dd
	private String postDate;
	//对方持卡人
	private String nameOnOppositeCard;
	//币种. CNY-?民币; USD-美元
	private String currencyType;
	//消费记录ID
	private String consumeId;
	//余额
	private Double balance;
	//交易方式
	private String transMethod;
	//交易时间. 格式为yyyy-MM-dd HH:mm:ss
	private String transDate;
	//交易地点
	private String transAddr;
	//消费类型，详见[数据字典，category(消费类型)取值]
	private String category;
	//对方银行
	private String oppositeBank;
	//描述
	private String description;
	//交易通道
	private String transChannel;
	//卡末4位
	private String cardNum;
	//用户id
	private int userId;
	//创建时间
	private Date gmtCreateTime;
	//更改时间
	private Date gmtUpdateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	
	public Double getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(Double amountMoney) {
		this.amountMoney = amountMoney;
	}
	
	
	public String getOppositeCardNo() {
		return oppositeCardNo;
	}
	public void setOppositeCardNo(String oppositeCardNo) {
		this.oppositeCardNo = oppositeCardNo;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getNameOnOppositeCard() {
		return nameOnOppositeCard;
	}
	public void setNameOnOppositeCard(String nameOnOppositeCard) {
		this.nameOnOppositeCard = nameOnOppositeCard;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getConsumeId() {
		return consumeId;
	}
	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId;
	}
	public String getTransMethod() {
		return transMethod;
	}
	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}
	
	
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransAddr() {
		return transAddr;
	}
	public void setTransAddr(String transAddr) {
		this.transAddr = transAddr;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOppositeBank() {
		return oppositeBank;
	}
	public void setOppositeBank(String oppositeBank) {
		this.oppositeBank = oppositeBank;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransChannel() {
		return transChannel;
	}
	public void setTransChannel(String transChannel) {
		this.transChannel = transChannel;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getGmtCreateTime() {
		return gmtCreateTime;
	}
	public void setGmtCreateTime(Date gmtCreateTime) {
		this.gmtCreateTime = gmtCreateTime;
	}
	public Date getGmtUpdateTime() {
		return gmtUpdateTime;
	}
	public void setGmtUpdateTime(Date gmtUpdateTime) {
		this.gmtUpdateTime = gmtUpdateTime;
	}
	
	
	
	

}
