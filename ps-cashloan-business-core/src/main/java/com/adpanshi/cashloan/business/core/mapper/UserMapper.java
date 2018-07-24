package com.adpanshi.cashloan.business.core.mapper;


import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.model.AuthUserModel;
import com.adpanshi.cashloan.business.core.model.CloanUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户管理Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2016-12-08 15:13:39
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserMapper extends BaseMapper<User,Long> {

	//List<CreditModel> page(Map<String,Object> searchMap);
	
	
	List<CloanUserModel> listModel(Map<String, Object> params);

	/**
	 * 用户认证信息列表查询
	 * @param params
	 * @return
	 */
	List<AuthUserModel> listAuthUserModel(Map<String, Object> params);
	//ZY 复借用户 2017.7.26
	List<CloanUserModel> reListModel(Map<String, Object> params);

	CloanUserModel getModel(Long id);

	List<Map<String, Object>> queryAllDic();

	/**
	 * 手机号查询id
	 * @param phone
	 * @return
	 */
	User findByLoginName(String phone);

	/**
	 * 修改等级
	 * @param user
	 * @return
	 */
	int updateLevel(User user);

	/**
	 * 查询用户等级
	 * @param map
	 * @return
	 */
	List<User> findUserLevel(Map<String, Object> map);

	/**
	 * 据uuid 修改用户信息
	 * 
	 * @param paramMap
	 * @return
	 */
	int updateByUuid(Map<String, Object> paramMap);
	
	/**
	 * 今日注册用户数
	 * @return
	 */
	long todayCount();
	
	/**
	 * <p>根据给定手机号查找用户<p>
	 * @param phone
	 * @return User
	 * */
	User getUserByPhone(String phone);

	/**
	 * <p>根据给定Email查找用户<p>
	 * @param email
	 * @return User
	 * */
	User getUserByEmail(String email);

	/**
	 * 最近注册用户
	 * @return
	 */
	User getLatestRegistUser();
	
	Map<String,Object> getTradeState(Long userId);
	
	Map<String,Object> validateTradePwd(@Param("userId") Long userId, @Param("tradePwd") String tradePwd);

	/**
	 * <p>根据给定邀请码查询</p>
	 * @param invitationCode
	 * @return User
	 * */
	User queryUserByInvitation(String invitationCode);

	/**
	 * <p>根据给定手机号更新登陆密码</p>
	 * @param loginName 待查询的手机号
	 * @param loginPwd  待更新的密码
	 * */
	int updateUserByLoginName(@Param("loginName") String loginName, @Param("loginPwd") String loginPwd);
	/**
	 * <p>根据给定手机号更新登陆密码</p>
	 * @param loginName 待查询的手机号
	 * @param loginPwd  待更新的密码
	 * */
	int updateUserByLoginName2(@Param("loginName2") String loginName, @Param("loginPwd") String loginPwd);

	/**
	 * <p>复借用户新</p>
	 * @param params 查询参数
	 * */
	List<CloanUserModel> reListModelNew(Map<String, Object> params);
}
