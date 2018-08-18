package com.adpanshi.cashloan.core.service.impl;

import com.adpanshi.cashloan.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.model.UserWorkInfoModel;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户详情表ServiceImpl
 * 
 * @author nmnl
 * @version 2.0.0
 * @date 2017-12-16 12:10:55
 * Copyright 粉团网路  cl All Rights Reserved
 */
 
@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends BaseServiceImpl<UserBaseInfo, Long> implements UserBaseInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);
	
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;
 
	@Override
	public BaseMapper<UserBaseInfo, Long> getMapper() {
		return  userBaseInfoMapper;
	}
	
	@Override
	public UserBaseInfo findByUserId(Long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo baseInfo = null;
		try {
			baseInfo = userBaseInfoMapper.findSelective(paramMap);
		} catch (Exception e) {
			logger.error("查询用户基本信息异常", e);
		}

		return baseInfo;
	}

	@Override
	public UserBaseInfo findSelective(Map<String, Object> paramMap) {
		return userBaseInfoMapper.findSelective(paramMap);
	}

	@Override
	public List<Map<String, Object>> getDictsCache(String type) {
		return userBaseInfoMapper.getDictsCache(type);
	}

	@Override
	public String getRegionalName(String regionalCode) {
		return userBaseInfoMapper.getRegionalName(regionalCode);
	}

	@Override
	public ManagerUserModel getBaseModelByUserId(Long userId) {
		return userBaseInfoMapper.getBaseModelByUserId(userId);
	}

	@Override
	public int updateState(long id, String state,String remarks) {
		int i = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", id);
		UserBaseInfo base=userBaseInfoMapper.findSelective(paramMap);
		if (base != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", base.getId());
			map.put("state", state);
			map.put("black_reason", remarks);
			i = userBaseInfoMapper.updateSelective(map);
		}
		return i;
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap) {
		int result = userBaseInfoMapper.updateSelective(paramMap);
		if(result >0L){
			return true;
		}
		return false;
	}
	
	@Override
	public UserWorkInfoModel getWorkInfo(Long userId){
		return userBaseInfoMapper.findWorkInfo(userId);
	}


	@Override
	public int updateState(Long userId,String remarks,AuthUserRole authUserRole) {
		int i = 0;
		UserBaseInfo base = findByUserId(userId);
		if (base != null) {
			Map<String, Object> map = new HashMap<>();
			String state = StringUtils.isEmpty(base.getState())?"20":("10".equals(base.getState())?"20":"10");
			map.put("id", base.getId());
			map.put("state", state);
			StringBuffer sb = new StringBuffer();
			sb.append(base.getBlackReason()).append("|").append(new Date()).append("|").append(base.getState()).append("-").append(state).append("|")
					.append(authUserRole.getUserId()).append("|").append(authUserRole.getUserName()).append("|").append(remarks).append("|");
			map.put("blackReason", sb.toString());
			i = userBaseInfoMapper.updateSelective(map);
		}
		return i;
	}

	@Override
	public int updateUserBaseInfoByUserId(Long userId, String dateBirthday, String education, String liveAddr) {
		if(StringUtil.isEmpty(userId,dateBirthday,education,liveAddr)){
			throw new SimpleMessageException("请求参数非法!");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo baseInfo=userBaseInfoMapper.findSelective(paramMap);
		if(null==baseInfo){
			throw new SimpleMessageException("未查询到待更新的用户!userId="+userId);
		}
		paramMap.put("id", baseInfo.getId());
		paramMap.put("dateBirthday", dateBirthday);
		paramMap.put("education", education);
		paramMap.put("liveAddr", liveAddr);
		return userBaseInfoMapper.updateSelective(paramMap);
	}


}