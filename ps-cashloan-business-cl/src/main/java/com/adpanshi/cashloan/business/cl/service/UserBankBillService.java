package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.UserBankBill;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

/**
 * 用户银行账单 Service
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2018-01-10 22:12
 *
 */
public interface UserBankBillService extends BaseService<UserBankBill, Long>{

	/**
	 * 用户银行账单详情
	 * @date 20180110
	 * @author nmnl
	 * @param userId
	 * @return
	 */
	Page<UserBankBill> page(Long userId, int current, int pageSize);


}
