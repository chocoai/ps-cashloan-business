package com.adpanshi.cashloan.business.rc.mapper;


import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.model.OperatorCountModel;

import java.util.Date;

/**
 * 运营商信息统计

 * @version 1.0
 * @date 2017年4月18日上午10:34:26
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
@RDBatisDao
public interface OperatorCountMapper extends BaseMapper<OperatorCountModel,String> {
	/**
	 * 通话信息
	 * @param phone
	 * @return
	 */
	OperatorCountModel operatorVoicesInfo(String phone);
	
	/**
	 * 月费账单
	 * @param phone
	 * @return
	 */
	Double operatorMonthAmt(String phone);
	
	/**
	 * 入网信息
	 * @param phone
	 * @return
	 */
	Date operatorJoinDate(String phone);
	
	/**
	 * 联系次数多的号码个数
	 * @param phone
	 * @return
	 */
	OperatorCountModel operatorVoicesPhone(String phone);
	
	/**
	 * 联系人借款信息
	 * @param phone
	 * @return
	 */
	OperatorCountModel concatsBorrowInfo(String phone);
	
	/**
	 * 紧急联系人最小联系次数
	 * @param userId
	 * @return
	 */
	Integer emerConcatTimes(Long userId);
}
