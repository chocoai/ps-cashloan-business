package com.adpanshi.cashloan.business.cl.service;

import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/25 19:23:33
 * @desc e-sign电子签接口
 * Copyright 浙江盘石 All Rights Reserved
 */
public interface EsignRecordService {

	/**
	 * 发起电子签章
	 * @param userId
	 * @return int 受影响的行
	 * */
	Map startSignWithAutoSilentSign(Long userId);

	/**
	 * 获取用户的电子签章
	 * @param userId
	 * @return String 返回是否获取文件
	 * */
	Map requestGetEsignFile(Long userId);
}
