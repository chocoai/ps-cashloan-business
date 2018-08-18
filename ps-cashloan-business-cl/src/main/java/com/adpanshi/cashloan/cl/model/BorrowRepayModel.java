package com.adpanshi.cashloan.cl.model;

import com.adpanshi.cashloan.cl.domain.BorrowRepay;

import java.util.Date;

/**
 * 还款计划Model
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 *
 */
public class BorrowRepayModel extends BorrowRepay{

	private static final long serialVersionUID = 1L;
	
	/** 还款方式 - 正常还款 */
	public static final String NORMAL_REPAYMENT = "10";

	/** 还款方式 - 逾期减免 */
	public static final String OVERDUE_RELIEF = "20";

	/** 还款方式 - 逾期正常还款 */
	public static final String OVERDUE_REPAYMENT = "30";
	
	
	/** 还款状态 -已还款 */
	public static final String STATE_REPAY_YES = "10";
	
	/** 还款状态 - 未还款 */
	public static final String STATE_REPAY_NO = "20";
	
	/**
	 * 借款时间
	 */
	private String createTime;
	/**
	 * 借款人手机号
	 */
	private String phone;
	/**
	 * 还款时间格式化 (yyyy-MM-dd HH:mm)
	 */
	private String repayTimeStr;
	/**
	 * 实际还款时间
	 */
	private String realRepayTime;
	
	/**
	 * 实际还款金额
	 */
	private String realRepayAmount;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 应还金额
	 */
	private String repayAmount;
	/**
	 * 应还款日期
	 *
	 */
private  Date  repayPlanTime;


	public String getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	/**
	 * 借款金额
	 */

	private String borrowAmount;

	/**
	 * 应还总额
	 */

	private String repayTotal;

	/**
	 *确认还款时间
	 */
	private Date confirmTime;

	/**
	 *确认还款人
	 */
	private String confirmName;

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 *确认还款人
	 */
	private String idNo;

	/**
	 * 获取还款时间格式化(yyyy-MM-ddHH:mm)
	 * 
	 * @return repayTimeStr
	 */
	public String getRepayTimeStr() {
		return repayTimeStr;
	}

	/**
	 * 设置还款时间格式化(yyyy-MM-ddHH:mm)
	 * 
	 * @param repayTimeStr
	 */
	public void setRepayTimeStr(String repayTimeStr) {
		this.repayTimeStr = repayTimeStr;
	}

	public String getRealRepayTime() {
		return realRepayTime;
	}

	public void setRealRepayTime(String realRepayTime) {
		this.realRepayTime = realRepayTime;
	}

	public String getRealRepayAmount() {
		return realRepayAmount;
	}

	public void setRealRepayAmount(String realRepayAmount) {
		this.realRepayAmount = realRepayAmount;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {this.realName = realName;}
	public String getOrderNo() {return orderNo;}

	public void setOrderNo(String orderNo) {this.orderNo = orderNo;}

	public String getRepayAmount() {return repayAmount;}
	public void setRepayAmount(String repayAmount) {this.repayAmount = repayAmount;}

	public String getRepayTotal() {return repayTotal;}
	public void setRepayTotal(String repayTotal) {this.repayTotal = repayTotal;}

	public Date getRepayPlanTime() {return repayPlanTime;}
	public void setRepayPlanTime(Date repayPlanTime) {this.repayPlanTime = repayPlanTime;}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmName() {
		return confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}
}

