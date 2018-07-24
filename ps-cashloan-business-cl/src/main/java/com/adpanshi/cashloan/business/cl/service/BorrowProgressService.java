package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.business.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.business.cl.model.ManageBorrowProgressModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 借款进度表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:31:04
 *
 *
 * 
 *
 */
public interface BorrowProgressService extends BaseService<BorrowProgress, Long>{

	/**
	 * 进度查询
	 * @return
	 */
	Map<String,Object> result(Borrow borrow);

	/**
	 * 后台还款进度列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<ManageBorrowProgressModel> listModel(Map<String, Object> params,
                                              int currentPage, int pageSize);

	/**
	 * 保存借款进度
	 * @param borrowProgress
	 * @return
	 */
	boolean save(BorrowProgress borrowProgress);


	/**
	 * 借款进度显示
	 *
	 * @param borrow
	 * @param pageFlag
	 *            detail代表详情页，index首页，首页不显示审核不通过和放款成功的进度，显示可以借款的信息
	 * @return
	 */
	List<BorrowProgressModel> borrowProgress(Borrow borrow, String pageFlag);

	/**
	 * 批量保存
	 *
	 * @param processList
	 * @return
	 * @author yecy 20171224
	 */
	int saveAll(List<BorrowProgress> processList);

}
