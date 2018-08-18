package com.adpanshi.cashloan.core.service.impl;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.model.AuthUserModel;
import com.adpanshi.cashloan.core.model.CloanUserModel;
import com.adpanshi.cashloan.core.service.CloanUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户认证ServiceImpl
 * author: nmnlØ
 * @version 1.0.0
 * @date 2017-12-16 10:40:02
 * Copyright 粉团网路 arc All Rights Reserved
 */

@Service("cloanUserService")
public class CloanUserServiceImpl extends BaseServiceImpl<User, Long> implements CloanUserService {
	private static final Logger logger = LoggerFactory.getLogger(CloanUserServiceImpl.class);

	@Resource
	private UserMapper userMapper;
	
	@Override
	public BaseMapper<User, Long> getMapper() {
		return userMapper;
	}

	@Override
	public Page<CloanUserModel> listUser(Map<String, Object> params,
                                         int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<CloanUserModel> list = userMapper.listModel(params);
		return (Page<CloanUserModel>) list;
	}

	/**
	 * 复借用户 zy
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<CloanUserModel> listReUser(Map<String, Object> params,
                                           int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<CloanUserModel> list = userMapper.reListModelNew(params);
		return (Page<CloanUserModel>) list;
	}

	@Override
	public long todayCount() {
		return userMapper.todayCount();
	}

	@Override
	public Page<AuthUserModel> listAuthUser(Map<String, Object> params,
                                            int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<AuthUserModel> list = userMapper.listAuthUserModel(params);
		return (Page<AuthUserModel>) list;
	}
}