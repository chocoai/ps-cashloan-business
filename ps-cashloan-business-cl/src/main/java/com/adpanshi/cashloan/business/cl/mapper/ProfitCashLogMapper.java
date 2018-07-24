package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.ProfitCashLog;
import com.adpanshi.cashloan.business.cl.model.ManageProfitAmountModel;
import com.adpanshi.cashloan.business.cl.model.ManageProfitLogModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 分润提现记录Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:51:48
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ProfitCashLogMapper extends BaseMapper<ProfitCashLog,Long> {

	/**
	 * 查询分润明细
	 * @return
	 */
	List<ManageProfitLogModel> findLog(Map<String, Object> map);
	
	/**
	 * 提现记录查询
	 * @param map
	 * @return
	 */
	List<ManageProfitAmountModel> findAmount(Map<String, Object> map);

	/**
	 * 管理员查询分润明细
	 * @param map
	 * @return
	 */
	List<ManageProfitLogModel> findSysLog(Map<String, Object> map);

	/**
	 * 管理员查看提现
	 * @param map
	 * @return
	 */
	List<ManageProfitAmountModel> findSysAmount(Map<String, Object> map);
}
