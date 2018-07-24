package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.ChannelApp;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * app渠道版本表Service
 */
public interface ChannelAppService extends BaseService<ChannelApp, Long>{

	/**
	 * 查询app更新信息
	 */
	List<ChannelApp> listChannelApp();
	
	/**
	 * 主键查询信息
	 */
	ChannelApp findByPrimary(long id);
	
	/**
	 * 保存信息
	 */
	int save(ChannelApp channelApp);
	
	/**
	 * 更新信息
	 */
	int updateSelective(Map<String, Object> paramMap);
}
