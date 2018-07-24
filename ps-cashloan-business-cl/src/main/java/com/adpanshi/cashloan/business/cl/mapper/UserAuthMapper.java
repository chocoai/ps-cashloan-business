package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserAuth;
import com.adpanshi.cashloan.business.cl.model.UserAuthModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户Dao
 * 

 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserAuthMapper extends BaseMapper<UserAuth,Long> {

	List<UserAuthModel> listUserAuthModel(Map<String, Object> params);

	int updateByUserId(Map<String, Object> paramMap);

//    public Map<String,Object> getAuthState(Map<String,Object> paramMap);

	/**
	 * 芝麻必填查询
	 * @deprecated 后期因为业务需要认证项现已修改成可配置项、请谨慎调用此接口!
	 * <p>请切换至{@link #getUserAuthWithConfigByUserId(Object data)}接口</p>
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmRequiredAuthState(Map<String, Object> authMap);
	
	/**
	 * <p>根据给定查询条件查询户认证指数</p>
	 * <p>总认证指数、已认证指数、已认证必填指数</p>
	 * @param data 
	 * @return Map 返回值不确定[要看数据库配置]--其中 返回值必包括如下字段:
	 * 		<p>total:总认证数(包括必填,可选项,隐藏项 )</p>
	 * 		<p>result:已认证项数</p>
	 * 		<p>qualified:值为0时表示必填项有未完善的认证项,当值为1时表示所有必填项都已认证</p>
	 * 	    <p>choose:值为0时表示选项项有未完善的认证项，当值为1时表示选择项已有认证</p>
	 * 		<p>隐藏项会以对应表字段来标识</p>
	 * */
	Map<String,Object> getUserAuthWithConfigByUserId(Map<String, Object> data);

	/**
	 * 芝麻选填查询
	 * @deprecated 后期因为业务需要认证项现已修改成可配置项、请谨慎调用此接口!
	 * <p>请切换至{@link #getUserAuthWithConfigByUserId(Object data)}接口</p>
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmOptionalAuthState(Map<String, Object> authMap);

	/**
	 * 芝麻去除查询
	 * @deprecated 后期因为业务需要认证项现已修改成可配置项、请谨慎调用此接口!
	 * <p>请切换至{@link #getUserAuthWithConfigByUserId(Object data)}接口</p>
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmRemoveAuthState(Map<String, Object> authMap);

	/**
	 * 插入带版本的userAuth
	 *
	 * @param userAuth
	 *            实体类
	 * @return 主键值
	 */
	int saveWithVersion(UserAuth userAuth);


	/**
	 * 获取条带版本的数据
	 *
	 * @param paramMap
	 *            查询条件
	 * @return 查询结果
	 */
	UserAuth findSelectiveWithVersion(Map<String, Object> paramMap);

	int updatePhoneState(Map<String, Object> userAuth);

	/**
	 * 更新紧急联系人状态
	 * @returns
	 */
	int updateContractStateByUserId(@Param("list") List<Long> list, @Param("state") String state);

	/**
	 * 通过时间查询用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> listByContactTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 更新运营商状态
	 * @return
	 */
	int updatePhoneStateByTime(@Param("list") List<Long> list, @Param("state") String state);

	/**
	 * 通过时间查询用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> listByPhoneTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * <p>根据给定userId更新租房收入认证状态</p>
	 * @param userId
	 * @param state
	 * @return 受影响的行数
	 * */
	int updateTenementIncomeStateWithTime(@Param("userId") Long userId, @Param("tenementIncomeState") String tenementIncomeState);

	/**
	 * <p>根据给定条件统计记录条数</p>
	 * @param map
	 * @return 记录数
	 * */
	int queryCount(Map<String, Object> map);

}
