package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.UserAuth;
import com.adpanshi.cashloan.cl.enums.UserAuthEnum;
import com.adpanshi.cashloan.cl.model.UserAuthModel;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.model.UserAuthData;
import com.github.pagehelper.Page;

import java.util.Map;


/**
 * 用户认证信息表Service
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:18:17
 *
 *
 * 
 *
 */
public interface UserAuthService extends BaseService<UserAuth, Long>{

	/**
	 * 获取用户认证状态
	 * @method: getUserAuth
	 * @param paramMap
	 * @return: com.adpanshi.cashloan.cl.domain.UserAuth
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 17:32
	 */
	public UserAuth getUserAuth(Map<String, Object> paramMap);

	/**
	 * 获取用户认证状态，关联同盾认证状态
	 * @param paramMap
	 * @return
	 */
	public UserAuth getUserAuthNewVersion(Map<String, Object> paramMap) ;

	/**
	 *  updateByUserId
	 * @method: updateByUserId
	 * @param paramMap
	 * @return: java.lang.Integer
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 17:34
	 */
	public Integer updateByUserId(Map<String, Object> paramMap);

	/**
	 * 认证用户紧急联系人
	 *
	 * @param userId
	 */
	public boolean authUserEmerContacts(long userId);

	/**
	 * 获取用户认证列表
	 * @method: listUserAuth
	 * @param params
	 * @param currentPage
	 * @param pageSize
	 * @return: com.github.pagehelper.Page<com.adpanshi.cashloan.cl.model.UserAuthModel>
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 17:32
	 */
	Page<UserAuthModel> listUserAuth(Map<String, Object> params, int currentPage,
                                     int pageSize);
	/**
	 * 查询认证状态
	 * @param userId
	 * @return
	 */
	public UserAuth findSelective(long userId);

	/**
	 * findSelectiveWithVersion
	 * @method: findSelectiveWithVersion
	 * @param userId
	 * @return: com.adpanshi.cashloan.cl.domain.UserAuth
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 17:33
	 */
	public UserAuth findSelectiveWithVersion(long userId);

	/**
	 * <p>更新用户租房收入认证状态为失效与场景对应记录为作废</p>
	 * <p>只有当state=27[人工复审不通过]且orderView中包含(租房收入证明不合法code=88888888且code_type=28)才会执行</p>
	 * @param state 审核状态
	 * @param userId 借款人id
	 * @param orderView 拒绝原因(JSON串)-[{"code":"2","value":"客户明确表示不需要"},{"code":"88888888","value":"租房收入证明不合法"}]
	 * @return 受影响的行数
	 * */
	int updateLoanSceneStateRefusedWithLoanScene(Integer state, Long userId, String orderView);

	/**
	 * <p>根据给定用户查询租房收入证明是否已完善</p>
	 * @param userId
	 * @return true:已认证、false:未认证
	 * */
	boolean houseIncomeIsAuth(Long userId);

	/**
	 * <p>@1.根据给定userId查询用户认证项数量</p>
	 * <p>@2.为了做成可扩展可配置(认证项后台数据库已做配置)、查询出用户认证项后与库中的配置项进行比对并重置</p>
	 * <p>@3.数据重置后、会进行排序(默认按升序排序)</p>
	 * @param userId
	 * @return UserAuthData
	 * */
	UserAuthData getUserAuthByUserIdWithReset(Long userId);

	/**
	 * <p>获取认证配置项</p>
	 * @1.获取数据库表中认证项配置信息
	 * @2.解释配置项
	 * @3.重新封装至UserAuthData中
	 * @return UserAuthData
	 * */
	UserAuthData getAuthConfigure();

	/**
	 * <p>根据给定数据进行组装SQL</p>
	 * @return Map
	 * */
	Map<String,Object> handleAuthDataJonSql();

	/**
	 * <p>根据给定查询条件查询户认证指数</p>
	 * <p>总认证指数、已认证指数、已认证必填指数</p>
	 * @param userId
	 * @return Map 返回值不确定[要看数据库配置]--其中 返回值必包括如下字段:
	 * 		<p>total:总认证数(包括必填,可选项,隐藏项 )</p>
	 * 		<p>result:已认证项数</p>
	 * 		<p>qualified:值为0时表示必填项有未完善的认证项,当值为1时表示所有必填项都已认证</p>
	 * 		<p>隐藏项会以对应表字段来标识</p>
	 * */
	Map<String,Object> getUserAuthWithConfigByUserId(Long userId);

	/**
	 * <p>根据给定条件查询指定认证项的状态</p>
	 * @param userId
	 * @param whatAuth
	 * */
	String getUserAuthState(Long userId, UserAuthEnum.WHAT_AUTH whatAuth);

	/**
	 * <p>根据给定条件查询用户是否已认证或认证中</p>
	 * @param userId
	 * @param whatAuth
	 * @return
	 * */
	boolean isVerified(Long userId, UserAuthEnum.WHAT_AUTH whatAuth);
	
}
