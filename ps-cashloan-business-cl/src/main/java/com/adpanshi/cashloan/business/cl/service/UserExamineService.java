package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;

public interface UserExamineService {

	/**
	 *查询我的信审订单信息
	 */
	BorrowUserExamine listBorrowUserExamineInfo(Long BorrowId);
	/**
	 *
	 */
	void sysUserToExamineOrderRelation(BorrowMain borrow, String sign);

}
