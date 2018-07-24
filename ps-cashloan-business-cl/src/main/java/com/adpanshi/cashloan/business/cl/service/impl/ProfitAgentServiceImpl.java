package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ProfitAgent;
import com.adpanshi.cashloan.business.cl.mapper.ProfitAgentMapper;
import com.adpanshi.cashloan.business.cl.service.ProfitAgentService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.system.mapper.SysAccessCodeMapper;
import com.adpanshi.cashloan.business.system.mapper.SysRoleMapper;
import com.adpanshi.cashloan.business.system.mapper.SysUserMapper;
import com.adpanshi.cashloan.business.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("profitAgentService")
public class ProfitAgentServiceImpl extends BaseServiceImpl<ProfitAgent, Long> implements ProfitAgentService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfitAgentServiceImpl.class);
   
    @Resource
    private ProfitAgentMapper profitAgentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
	private SysUserMapper sysUserMapper;
    @Resource
	private UserBaseInfoMapper userBaseInfoMapper;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysAccessCodeMapper sysAccessCodeMapper;
    
	@Override
	public BaseMapper<ProfitAgent, Long> getMapper() {
		return profitAgentMapper;
	}

	@Override
	public int pass(int isUse,long id) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("id", id);
		result.put("isUse", isUse);
		return profitAgentMapper.updateSelective(result);
	}


}