package com.adpanshi.cashloan.business.rule.service;

import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.rule.domain.TanzhiRecord;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-03-16 17:29:30
 * @history
 */
public interface TanzhiRecordService{
    
	/**
	 * <p>多头借贷报告查询</p>
	 * @param borrowMain
	 * @return TanzhiRecord 
	 * */
	TanzhiRecord multiplebonds(BorrowMain borrowMain);
	
	/**
	 * <p>分析多头借贷报告</p>
	 * @param borrowMain
	 * @return String
	 * */
	String executeByMultiplebonds(BorrowMain borrowMain);
	
}