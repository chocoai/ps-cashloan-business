package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.rule.model.ClBorrowModel;
import com.adpanshi.cashloan.business.rule.model.IndexModel;
import com.adpanshi.cashloan.business.rule.model.ManageBorrowModel;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 借款信息表Service
 */
public interface ClBorrowService extends BaseService<Borrow, Long> {
	
	/**
	 * 查询最新10条借款信息
	 */
	List<IndexModel> listIndex();

	/**
	 * 借款记录查看
	 */
	List<BorrowMain> findBorrow(Map<String, Object> searchMap);

	/**
	 * 分页查询
	 */
	Page<ClBorrowModel> page(Map<String, Object> searchMap, int current,
                             int pageSize);

	/**
	 * 关联用户的借款分页查询后台列表显示
	 */
	Page<ManageBorrowModel> listModel(Map<String, Object> params,
                                      int currentPage, int pageSize);

	/**
	 * 修改数据
	 */
	int updateSelective(Map<String, Object> data);

	/**
	 * 首页信息查询
	 */
	Map<String,Object> findIndex(String userId);

	/**
	 * 额度评估
	 */
	Map<String,Object> assessment(String userId);

	/**
	 * 批量保存
	 */
	int saveAll(List<Borrow> borrowList);

	Borrow findBorrowByMainId(long borrowId);
}
