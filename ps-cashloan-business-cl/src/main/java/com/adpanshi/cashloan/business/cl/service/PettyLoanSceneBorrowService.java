package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.PettyLoanSceneBorrow;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-06 09:52:47
 * @history
 */
public interface PettyLoanSceneBorrowService extends BaseService<PettyLoanSceneBorrow,Long>{
    
	/**
	 * <p>根据给定userId、borrowId进行存储</p>
	 * @param userId
	 * @param borrowMainId
	 * @return 受影响的行数
	 * */
	int savePettyLoanSceneBorrow(Long userId, Long borrowMainId);
	
	
	
}