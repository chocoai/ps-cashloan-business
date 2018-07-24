package com.adpanshi.cashloan.business.core.service;

import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.model.AuthUserModel;
import com.adpanshi.cashloan.business.core.model.CloanUserModel;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 用户Service
 * 

 * @version 1.0.0
 * @date 2017-02-21 13:39:06
 *
 *
 * 
 *
 */
public interface CloanUserService extends BaseService<User, Long>{
	/**
	 * 查询用户详细信息列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<CloanUserModel> listUser(Map<String, Object> params, int currentPage,
                                  int pageSize);
	/**
	 * 查询复借用户详细信息列表zy 2017.7.26
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<CloanUserModel> listReUser(Map<String, Object> params, int currentPage,
                                    int pageSize);

	/**
	 * 今日注册用户数
	 * @return
	 */
	long todayCount();

	/**
	 * 查询用户详细信息列表
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Page<AuthUserModel> listAuthUser(Map<String, Object> params, int currentPage,
                                     int pageSize);
}
