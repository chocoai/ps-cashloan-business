package com.adpanshi.cashloan.core.service;

import com.adpanshi.cashloan.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.model.UserWorkInfoModel;

import java.util.List;
import java.util.Map;


/**
 * 用户详情表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:08:04
 *
 *
 * 
 *
 */
public interface UserBaseInfoService extends BaseService<UserBaseInfo, Long>{

	/**
	 * 据userId查询用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	UserBaseInfo findByUserId(Long userId);

	/**
	 * 据条件查询用户基本信息
	 * 
	 * @param paramMap
	 * @return
	 */
	UserBaseInfo findSelective(Map<String, Object> paramMap);

	/**
	 *
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> getDictsCache(String type);

	/**
	 *
	 * @param regionalCode
	 * @return
	 */
	String getRegionalName(String regionalCode);
	
	/**
	 * 查询信息
	 * @param userId
	 * @return
	 */
	ManagerUserModel getBaseModelByUserId(Long userId);

	/**
	 * 添加取现黑名单
	 * @param id
	 * @param state
	 * @param remarks
	 * @return
	 */
	int updateState(long id, String state, String remarks);
	
	/**
	 * 修改用户基本信息
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);
	
	/**
	 * 查询用户工作信息
	 * @param userId
	 * @return
	 */
	UserWorkInfoModel getWorkInfo(Long userId);


	/**
	 * 添加取现黑名单/取消.
	 * @param userId
	 * @param remarks
	 * @param authUserRole
	 * @return
	 */
	int updateState(Long userId, String remarks, AuthUserRole authUserRole);

	/**
	 * <p>根据给定userId更新</p>
	 * @param userId
	 * @param dateBirthday
	 * @param education
	 * @param liveAddr
	 * @return 受影响的行数
	 * */
	int updateUserBaseInfoByUserId(Long userId, String dateBirthday, String education, String liveAddr);


	
}
