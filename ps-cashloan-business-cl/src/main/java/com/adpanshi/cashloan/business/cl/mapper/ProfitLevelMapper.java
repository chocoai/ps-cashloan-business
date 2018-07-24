package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.ProfitLevel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 分润等级Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:58:10
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ProfitLevelMapper extends BaseMapper<ProfitLevel,Long> {

	/**
	 * 查询所有
	 * @return
	 */
	List<ProfitLevel> listAll();

    

}
