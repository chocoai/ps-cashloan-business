package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.AppMsgTpl;
import com.adpanshi.cashloan.business.cl.domain.appMsgPushLog;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-01 16:47:13
 * @history
 */
public interface AppMsgTplService extends BaseService<AppMsgTpl,Long>{
	
	/**
	 * <p>根据给定type、state查询记录</p>
	 * @param type 模板类型(必填项)
	 * @param state 模板状态(必填项)
	 * @return UserDeviceTokens
	 * */
	AppMsgTpl getByTypeWithState(Integer type, Integer state);

	/**
	 * 后台列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<appMsgPushLog> listModel(Map<String, Object> params, int currentPage,
                                  int pageSize);

}