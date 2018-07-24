package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayCountModel;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayOrderModel;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayOrderModelVo;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 催款计划表Dao
 * 

 * @version 1.0.0
 * @date 2017-03-07 14:21:58
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UrgeRepayOrderMapper extends BaseMapper<UrgeRepayOrder,Long> {

	List<UrgeRepayOrder> listSelective(Map<String, Object> paramMap);

	List<UrgeRepayOrderModel> listUrgeTotalOrder(Map<String, Object> paramMap);

	List<UrgeRepayOrderModel> listModel(Map<String, Object> params);

	List<UrgeRepayCountModel> memberCount(Map<String, Object> params);

	List<UrgeRepayCountModel> orderCount(Map<String, Object> params);

	List<UrgeRepayCountModel> urgeCount(Map<String, Object> params);

	List<UrgeRepayCountModel> memberDayCount(Map<String, Object> params);
	
	/**
	 * borrowId删除催收订单
	 * @param borrowId
	 * @return
	 */
	int deleteByBorrowId(@Param("borrowId") Long borrowId);

	/**
	 * 变更未催收完成(带催收，催收中，承诺还款)催收订单所有人
	 * @param params
	 * @return
	 */
	int updateChangeUser(Map<String, Object> params);
	
	List<UrgeRepayOrderModelVo> listManage(Map<String, Object> paramMap);
	
}
