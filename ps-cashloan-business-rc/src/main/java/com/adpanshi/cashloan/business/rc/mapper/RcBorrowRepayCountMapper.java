package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

/**
 * 还款统计

 * @version 1.0
 * @date 2017年4月21日下午2:59:02
 * Copyright 粉团网路 现金贷  All Rights Reserved
 *
 * 研发中心：rdc@fentuan123.com
 *
 */
@RDBatisDao
public interface RcBorrowRepayCountMapper {
	
	/**
	 * 统计逾期超过90天的借款
	 * @param id
	 * @return
	 */
	Integer countFailNinety(Long id);

	/**
	 * 统计逾期超过30天,小于90天的借款
	 * @param id
	 * @return
	 */
	Integer countFailThirty(Long id);

	/**
	 * 统计逾期小于30天的借款
	 * @param id
	 * @return
	 */
	Integer countFailWithinThirty(Long id);

	/**
	 * 统计紧急联系人逾期超过90天的借款
	 * @param id
	 * @return
	 */
	Integer countRelativeNinety(Long id);

	/**
	 * 统计紧急联系人逾期超过30天,小于90天的借款
	 * @param id
	 * @return
	 */
	Integer countRelativeThirty(Long id);

	/**
	 * 统计紧急联系人逾期小于30天的借款
	 * @param id
	 * @return
	 */
	Integer countRelativeWithinThirty(Long id);
}
