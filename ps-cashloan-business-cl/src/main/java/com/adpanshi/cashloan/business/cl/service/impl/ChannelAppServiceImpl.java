package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ChannelApp;
import com.adpanshi.cashloan.business.cl.mapper.ChannelAppMapper;
import com.adpanshi.cashloan.business.cl.service.ChannelAppService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * app渠道版本表ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-05-10 10:29:55
 *
 *
 *
 *
 */
@Service("channelAppService")
public class ChannelAppServiceImpl extends BaseServiceImpl<ChannelApp, Long> implements ChannelAppService {
	
    @Resource
    private ChannelAppMapper channelAppMapper;

	@Override
	public BaseMapper<ChannelApp, Long> getMapper() {
		return null;
	}

	@Override
	public List<ChannelApp> listChannelApp() {
		return channelAppMapper.listSelective();
	}

	@Override
	public ChannelApp findByPrimary(long id) {
		return channelAppMapper.findByPrimary(id);
	}

	@Override
	public int save(ChannelApp channelApp) {
		return channelAppMapper.save(channelApp);
	}

	@Override
	public int updateSelective(Map<String, Object> paramMap) {
		return channelAppMapper.updateSelective(paramMap);
	}
	






	
}