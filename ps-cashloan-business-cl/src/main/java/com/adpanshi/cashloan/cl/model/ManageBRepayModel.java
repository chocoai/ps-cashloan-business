package com.adpanshi.cashloan.cl.model;

import com.adpanshi.cashloan.cl.domain.BorrowRepay;
import tool.util.BigDecimalUtil;

import java.util.Date;

/**
 * @author 8452
 */
public class ManageBRepayModel extends BorrowRepay {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 真实姓名
	 */
	private String realName;
 
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 借款金额
	 */
	private Double borrowAmount;

	/**
	 * 还款金额
	 */
	private Double repayAmount;

	/**
	 * 还款总额
	 */
	private Double repayTotal;
	
	/**
	 * 借款时间
	 */
	private Date borrowTime;
	/**
	 * 应还款日期 zy
	 *
	 */
	private  Date  repayPlanTime;

	/**
	 * 身份证
	 */
	private String idNo;

	/**
	 * 复审人姓名
	 * @return
	 */
	private  String auditName;/*zy*/
	
	/**
	 * 审核生成时间
	 * @return
	 */
	private  Date auditTime;
	
	/**借款期限*/
	private String timeLimit;

	/**
	 * 实际还款金额
	 */
	private Double realAmout;

	/**
	 * 实际还款罚金
	 */
	private Double realPenaltyAmout;

	/**订单状态*/
	private String borrowState;

	/**还款方式*/
	private String repayWay;

	/**还款帐号*/
	private String repayAccount;

	/**还款流水号*/
	private String serialNumber;

	/**期数*/
	private Integer phase;
	

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	
	public Double getRepayTotal() {
		this.repayTotal =  BigDecimalUtil.add(this.getRepayAmount(),this.getPenaltyAmout());
		return repayTotal;
	}

	public void setRepayTotal(Double repayTotal) {
		this.repayTotal = repayTotal;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getIdNo() {return idNo;}

	public void setIdNo(String idNo) {this.idNo = idNo;}

	public String getAuditName() {return auditName;}/*zy 2017.7.21*/

	public void setAuditName(String auditName) {this.auditName = auditName;}

	public Date getRepayPlanTime() {return repayPlanTime;}
	public void setRepayPlanTime(Date repayPlanTime) {this.repayPlanTime = repayPlanTime;}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Double getRealAmout() {
		return realAmout;
	}

	public void setRealAmout(Double realAmout) {
		this.realAmout = realAmout;
	}

	public Double getRealPenaltyAmout() {
		return realPenaltyAmout;
	}

	public void setRealPenaltyAmout(Double realPenaltyAmout) {
		this.realPenaltyAmout = realPenaltyAmout;
	}

	public String getBorrowState() {
		return borrowState;
	}

	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}

	public String getRepayWay() {
		return repayWay;
	}

	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}

	public String getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(String repayAccount) {
		this.repayAccount = repayAccount;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}
}
