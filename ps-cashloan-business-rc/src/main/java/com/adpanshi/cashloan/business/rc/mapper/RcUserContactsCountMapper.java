package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

/**
 * 紧急联系人借款信息统计

 * @version 1.0
 * @date 2017年4月21日下午2:47:03
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
@RDBatisDao
public interface RcUserContactsCountMapper  {

	/**
	 * 通讯录总条数
	 * @param id
	 * @return
	 */
	Integer count(Long id);

	/**
	 * 通讯录借款未逾期人数
	 * @param id
	 * @return
	 */
	Integer countSucceed(Long id);

	/**
	 * 通讯录借款逾期人数
	 * @param id
	 * @return
	 */
	Integer countFail(Long id);

	/**
	 * 通讯录借款逾期大于90天人数
	 * @param id
	 * @return
	 */
	Integer countNinety(Long id);

	/**
	 * 通讯录借款逾期大于30天小于90天人数
	 * @param id
	 * @return
	 */
	Integer countThirty(Long id);

	/**
	 * 通讯录借款逾期小于30天人数
	 * @param id
	 * @return
	 */
	Integer countWithinThirty(Long id);
}
