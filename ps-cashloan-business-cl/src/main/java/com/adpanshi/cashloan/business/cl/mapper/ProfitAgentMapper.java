package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.ProfitAgent;
import com.adpanshi.cashloan.business.cl.model.ManageAgentListModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 代理用户信息Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:24:45
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ProfitAgentMapper extends BaseMapper<ProfitAgent,Long> {

	/**
	 * 查询代理商
	 * @param map
	 * @return
	 */
	List<ManageAgentListModel> findAgentList(Map<String, Object> map);

}
