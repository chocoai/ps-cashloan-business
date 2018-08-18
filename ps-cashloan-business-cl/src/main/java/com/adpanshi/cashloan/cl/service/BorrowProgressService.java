package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.cl.model.ManageBorrowProgressModel;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.github.pagehelper.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 借款进度表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:31:04
 *
 */
public interface BorrowProgressService extends BaseService<BorrowProgress, Long>{

	/**
	 * 进度查询
	 * @param borrow
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
	 * 查询列表
	 * @param map
	 * @return
	 */
	List<BorrowProgress> listSeletetiv(Map<String, Object> map);

	/**
	 * 添加借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	void savePressState(Borrow borrow, String state);

	/**
	 * 更新最近的借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	void updateNearestPressState(Borrow borrow, String state);

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
	 * 查找借款进度中存在过借款类型为'$state'的流程
	 * @param stateList
	 * @return
	 */
	List<BorrowProgress> findProcessByState(Long borrowId, Collection<String> stateList);

	/**
	 * 批量保存
	 *
	 * @param processList
	 * @return
	 * @author yecy 20171224
	 */
	int saveAll(List<BorrowProgress> processList);

}
