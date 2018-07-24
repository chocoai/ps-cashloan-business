package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.SmsTpl;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

/**
 * 短信记录Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-13 18:36:01
 *
 *
 * 
 *
 */
@RDBatisDao
public interface SmsTplMapper extends BaseMapper<SmsTpl,Long> {

	/**
	 * <p>根据给定type找到对应的短信模板</p>
	 * @param type
	 * @return SmsTpl
	 * */
	SmsTpl findByType(String type);
	
}
