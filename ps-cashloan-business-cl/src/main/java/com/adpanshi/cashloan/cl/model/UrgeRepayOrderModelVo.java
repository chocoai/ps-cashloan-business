package com.adpanshi.cashloan.cl.model;

import com.adpanshi.cashloan.cl.domain.UrgeRepayOrder;

import java.util.Date;

/**
 * 催款记录表实体
 *
 * @version 1.0.0
 * @date 2017-03-07 14:28:22 Copyright 粉团网路 arc All Rights Reserved
 * @author 8452
 */
public class UrgeRepayOrderModelVo extends UrgeRepayOrder {

	private static final long serialVersionUID = 1L;

	/**
	 * 10未分配;11待催收;20催收中;30承诺还款;40催收成功;50坏账
	 */
	
	/** 未分配催收人员*/
	public static final String STATE_ORDER_PRE = "10";
	/** 待催收 */
	public static final String STATE_ORDER_WAIT= "11";
	/** 催收中*/
	public static final String STATE_ORDER_URGE = "20";
	/** 承诺还款*/
	public static final String STATE_ORDER_PROMISE= "30";
	/** 催收成功 */
	public static final String STATE_ORDER_SUCCESS = "40";
	/** 坏账*/
	public static final String STATE_ORDER_BAD = "50";
	
	public static String change(String state){
		String stateStr = "";
		switch (state) {
		case STATE_ORDER_PRE:
			stateStr = "未分配催收人员";
			break;
		case STATE_ORDER_WAIT:
			stateStr = "待催收";
			break;
		case STATE_ORDER_URGE:
			stateStr = "催收中";
			break;
		case STATE_ORDER_PROMISE:
			stateStr = "承诺还款";
			break;
		case STATE_ORDER_SUCCESS:
			stateStr = "催收成功";
			break;
		case STATE_ORDER_BAD:
			stateStr = "坏账";
			break;
		default:
			break;
		}
		return stateStr;
	}
	
	/** 
	 * 根据逾期天数计算逾期等级
	 * @return level  
	 */
	public static String getLevelByDay(Long penaltyDay) {
		String level = "M1";
		if (penaltyDay != null) {
			if (0 <= penaltyDay && penaltyDay <= 30) {
				level = ("M1");
			} else if (31 <= penaltyDay && penaltyDay <= 60) {
				level = ("M2");
			} else if (61 <= penaltyDay) {
				level = ("M3");
			}
		}
		return level;
	}
	/**
	 * 催收时间
	 */
	private Date createTime;

	/**
	 * 备注说明
	 */
	private String remark;

	/**
	 * 承诺还款时间
	 */
	private Date promiseTime;
	
	/**
	 * 催款方式   10 电话；20 邮件 ；30 短信；40现场沟通；50 其他
	 */
	private String way;
	
	/**
	 * 最后催记时间
	 */
	private Date recentlyUrgeTime;

	/**
	 * 借款用户id
	 */
	private Long  borrowUserId;

	/**
	 * 期数
	 * */
	private Integer phase;

	public Date getPromiseTime() {
		return promiseTime;
	}

	public void setPromiseTime(Date promiseTime) {
		this.promiseTime = promiseTime;
	}

	/**
	 * 获取催收时间
	 * 
	 * @return 催收时间
	 */
	@Override
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置催收时间
	 * 
	 * @param createTime
	 *            要设置的催收时间
	 */
	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取备注说明
	 * 
	 * @return 备注说明
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注说明
	 * 
	 * @param remark
	 *            要设置的备注说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public Date getRecentlyUrgeTime() {
		return recentlyUrgeTime;
	}

	public void setRecentlyUrgeTime(Date recentlyUrgeTime) {
		this.recentlyUrgeTime = recentlyUrgeTime;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	/**
	 * 待还金额
	 * @return
	 */
	public Double getShouldAmout() {
		if("40".equals(getState())){
			return new Double(0);
		}
		return getAmount() + getPenaltyAmout();
	}

	public Long getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(Long borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

}