package com.adpanshi.cashloan.cl.model;

import com.adpanshi.cashloan.cl.domain.Opinion;

/**
 * 意见反馈model

 * @version 1.0.0
 * @date 2017年4月6日 下午6:43:38
 * Copyright 粉团网路 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 * @author
 */
public class OpinionModel extends Opinion {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态_	10待确认
	 */
	public static final String STATE_WAITE_CONFIRM = "10";
	
	/**
	 * 状态_20已确认
	 */
	public static final String STATE_CONFIRMED = "20";
	
	/**
	 * 用户手机号码
	 */
	private String phone;
	
	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 身份证号码
	 */
	private String idNo;
	
	/**
	 * 管理员真实姓名
	 */
	private String sysUserRealName;
	
	/**
	 * 状态的中文描述
	 */
	private String stateStr;

	/**
	 * 获取用户手机号码
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置用户手机号码
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取用户真实姓名
	 * @return realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置用户真实姓名
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取管理员真实姓名
	 * @return sysUserRealName
	 */
	public String getSysUserRealName() {
		return sysUserRealName;
	}

	/**
	 * 设置管理员真实姓名
	 * @param sysUserRealName
	 */
	public void setSysUserRealName(String sysUserRealName) {
		this.sysUserRealName = sysUserRealName;
	}

	/**
	 * 获取状态的中文描述
	 * @return stateStr
	 */
	public String getStateStr() {
		String state = getState();
		if(STATE_WAITE_CONFIRM.equals(state)){
			return "待确认";
		} else if(STATE_CONFIRMED.equals(state)){
			return "已确认";
		} 
		return stateStr;
	}

	/**
	 * 设置状态的中文描述
	 * @param stateStr
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
