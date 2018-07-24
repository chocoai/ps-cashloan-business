package com.adpanshi.cashloan.business.core.model;

import com.adpanshi.cashloan.business.core.domain.User;

/**
 * 用户基本信息model

 * @version 1.0.0
 * @date 2017年3月22日 下午7:20:27
 * Copyright 粉团网路 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 *
 *
 */
public class UserModel extends User {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户状态-有效  1
	 */
	public static final int USER_STATE_VALID = 1;
	
	/**
	 * 用户状态-失效 0
	 */
	public static final int USER_STATE_UNVALID = 0;

	/**
	 * 用户状态-删除 -1
	 */
	public static final int USER_STATE_DELETED = -1;

}
