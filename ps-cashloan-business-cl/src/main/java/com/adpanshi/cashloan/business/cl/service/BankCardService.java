package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.rule.domain.BankCard;

import java.util.Map;

/**
 * 银行卡Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-15 14:37:14
 *
 *
 * 
 *
 */
public interface BankCardService extends BaseService<BankCard, Long>{

	/**
	 * 保存记录
	 * @param bankCard
	 * @return
	 */
	boolean save(BankCard bankCard);
	
	/**
	 * 据userId查询银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	BankCard getBankCardByUserId(Long userId);

	/**
	 * 据条件查询银行卡信息
	 * 
	 * @param paramMap
	 * @return
	 */
	BankCard findSelective(Map<String, Object> paramMap);

	/**
	 * 修改数据解约银行卡绑定
	 * @param userId
	 * @return
	 */
	int unBindBankCard(Long userId);
	
	/**
	 * 修改银行卡信息
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);

}
