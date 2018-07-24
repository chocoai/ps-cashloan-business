
package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

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
	 * @param type 模板类型(必填项)
	 * @param state 模板状态(必填项)
	 * @return UserDeviceTokens
	 * */
	AppMsgTpl getByTypeWithState(Map<String, Object> map);
	
	
}
