package com.adpanshi.cashloan.core.domain;

import com.adpanshi.cashloan.core.model.BorrowModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款主信息表实体
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 15:32:54
 */
public class BorrowMain extends BaseBorrow implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String bank;
    private String cardNo;

    
    /**
     * 模板信息
     */
    private String templateInfo;

    /**
     * 实际放款时间
     */
    private Date loanTime;

    /**
     * 最后一期应还款时间
     */
    private Date repayTime;

    /**
     * 分配人Id
     */
    private Long sysUserId;

    /**
     * 分配人名称
     */
    private String sysUserName;

    /**
     * 分配时间
     */
    private Date sysCreateTime;

    /**
     * 风控相关-
     * */
    private Object borRuleResultExtra;

    /**
     * 借款用途(1.租房贷款、5.个体工商周转、6、消费贷款、7.住房家装)
     * */
    private Integer borrowType;
    /**
     * 渠道id
     * */
    private Long channelId;




    public String getStateStr() {
        this.stateStr = BorrowModel.manageConvertBorrowState(this.getState());
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    private String stateStr;

    public BorrowMain() {
        super();
    }

    public BorrowMain(Long userId, Double amount, String timeLimit, Long cardId, String client, String address, String coordinate, String ip) {
        super();
        super.userId = userId;
        super.amount = amount;
        super.timeLimit = timeLimit;
        super.cardId = cardId;
        super.client = client;
        super.address = address;
        super.coordinate = coordinate;
        super.ip = ip;
    }
    
    public BorrowMain(Long userId, Double amount, String timeLimit, Long cardId, String client, String address, String coordinate, String ip, Integer borrowType){
    	this(userId, amount, timeLimit, cardId, client, address, coordinate, ip);
    	this.borrowType=borrowType;
    }
    public BorrowMain(Long userId, Double amount, String timeLimit, Long cardId, String client, String address, String coordinate, String ip, Integer borrowType, Long channelId){
    	this(userId, amount, timeLimit, cardId, client, address, coordinate, ip, borrowType);
    	this.channelId=channelId;
    }

    /**
     * 获取模板信息
     *
     * @return 模板信息
     */
    public String getTemplateInfo() {
        return templateInfo;
    }

    /**
     * 设置模板信息
     *
     * @param templateInfo 要设置的模板信息
     */
    public void setTemplateInfo(String templateInfo) {
        this.templateInfo = templateInfo;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
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

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
    }

	public Object getBorRuleResultExtra() {
		return borRuleResultExtra;
	}

	public void setBorRuleResultExtra(Object borRuleResultExtra) {
		this.borRuleResultExtra = borRuleResultExtra;
	}

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
