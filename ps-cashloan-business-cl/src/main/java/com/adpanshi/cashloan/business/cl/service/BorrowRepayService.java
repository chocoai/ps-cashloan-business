package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.business.cl.model.BorrowRepayModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 还款计划Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 *
 *
 * 
 *
 */
public interface BorrowRepayService extends BaseService<BorrowRepay, Long>{

	/**
	 * 保存还款计划
	 * @param borrowRepay
	 * @return
	 */
	int save(BorrowRepay borrowRepay);

	/**
	 * 根据分期账单生成还款计划(模拟环境调用)
	 * @param borrowList
	 * @param borrowMain
	 * @param isMockData 是否为模拟数据
	 * @return
	 */
	void genRepayPlans(List<Borrow> borrowList, BorrowMain borrowMain, Boolean isMockData);
	/**
	 * 还款计划 放款 成功之后 银行卡授权
	 * 
	 * @param userId
	 */
	void authSignApply(Long userId);
	
	
	 /**
	  * 后台列表
	  * @param params
	  * @param currentPage
	  * @param pageSize
	  * @return
	  */
	Page<BorrowRepayModel> listModel(Map<String, Object> params, int currentPage,
                                     int pageSize);

	/**
	 * by cc 17-08-08
	 * 确认还款生产还款记录(给确认还款用)
	 * @param param
	 * @return
	 */
	void confirmRepayNew(Map<String, Object> param)throws Exception;

	/**
	 * 查询所有
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> listSelective(Map<String, Object> paramMap);

	/**
	 * 条件更新还款计划数据
	 * @param paramMap
	 * @return
	 */
	int updateSelective(Map<String, Object> paramMap);


	/**
	 * 查询还款计划
	 *
	 * @param paramMap
	 * @return
	 */
	BorrowRepay findSelective(Map<String, Object> paramMap);

}
