package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;
import com.adpanshi.cashloan.business.cl.model.BorrowUserExamineModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface BorrowUserExamineMapper extends BaseMapper<BorrowUserExamine,Long>{
	/**
	 * 获取未完成任务的人员统计信息
	 */
	List<BorrowUserExamineModel> listBorrowUserExamineModel(String status);
	
	/**
	 * 获取单条数据
	 */
	BorrowUserExamine findSelective(Map<String, Object> paramMap);
	
	/**
	 * 更新审核人员ID，姓名
	 */
	void updateBorrowUserName(Map<String, Object> paramMap);
	/**
	 * 获取挂起状态的订单信息
	 */
	List<BorrowUserExamine> queryborrowsByIds(Map<String, Object> borrowIdsParam); 
}
