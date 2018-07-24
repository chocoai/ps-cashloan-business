package com.adpanshi.cashloan.business.rule.model;

import com.adpanshi.cashloan.business.rule.domain.OperatorTdCallRecord;

/**
 * 通话记录查询实体

 * @version 1.0.0
 * @date 2017-05-24 09:32:41
 * Copyright 粉团网路  cashloan All Rights Reserved
 *
 *
 */
 public class OperatorTdCallModel extends OperatorTdCallRecord {

    private static final long serialVersionUID = 1L;

    /**
     * 授权手机号码
     */
    private String userMobile;
    
    /**
     * 对方姓名
     */
    private String voiceToNumberName;

	/**
	 * 是否存在 小贷信用卡催收电话号码库
	 */
	private Boolean isMatch;


	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getVoiceToNumberName() {
		return voiceToNumberName;
	}

	public void setVoiceToNumberName(String voiceToNumberName) {
		this.voiceToNumberName = voiceToNumberName;
	}

	public Boolean getMatch() {
		return isMatch;
	}

	public void setMatch(Boolean match) {
		isMatch = match;
	}
}
