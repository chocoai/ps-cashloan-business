
package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-01 16:47:13
 * @history
 */
@RDBatisDao
public interface AppMsgTplMapper extends BaseMapper<AppMsgTpl,Long>{
	
	/**
	 * <p>根据给定type、state查询记录</p>
	 * @param map
	 * @return UserDeviceTokens
	 * */
	AppMsgTpl getByTypeWithState(Map<String, Object> map);
	
	
}
