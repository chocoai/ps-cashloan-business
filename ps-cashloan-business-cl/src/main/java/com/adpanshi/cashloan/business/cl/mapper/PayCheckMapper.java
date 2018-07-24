package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.PayCheck;
import com.adpanshi.cashloan.business.cl.model.ManagePayCheckModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;


/**
 * 资金对账记录Dao
 * 

 * @version 1.0.0
 * @date 2017-04-13 17:12:20
 *
 *
 *
 */
@RDBatisDao
public interface PayCheckMapper extends BaseMapper<PayCheck,Long> {

    
	/**
	 *
	 * @param searchMap
	 * @return
	 */
	 List<ManagePayCheckModel> page(Map<String, Object> searchMap);

}
