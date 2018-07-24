package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrderLog;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 催款记录表Dao
 * 

 * @version 1.0.0
 * @date 2017-03-07 14:28:22
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UrgeRepayOrderLogMapper extends BaseMapper<UrgeRepayOrderLog,Long> {

	/**
	 * 催收订单Id删除催收记录
	 * 
	 * @param dueId
	 * @return
	 */
	int deleteByOrderId(@Param("dueId") Long dueId);

	/**
	 * zy 催收反馈修改
	 * @param params
	 * @return
	 */
	int updateRemark(Map<String, Object> params);
}
