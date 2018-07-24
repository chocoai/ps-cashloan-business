package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;

/**
 * 用户基本信息model

 * @version 1.0.0
 * @date 2017年3月22日 下午7:20:27
 * Copyright 粉团网路 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 *
 *
 */
public class UserBaseInfoModel extends UserBaseInfo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户状态-黑名单 10
	 */
	public static final String USER_STATE_BLACK = "10";
	
	/**
	 * 用户状态-不是黑名单 20
	 */
	public static final String USER_STATE_NOBLACK = "20";

}
