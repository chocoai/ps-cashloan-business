
package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.CreditsUpgrade;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @category 用户额度提升表
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-30 20:46:14
 * @history
 */
@RDBatisDao
public interface CreditsUpgradeMapper extends BaseMapper<CreditsUpgrade,Long>{
	
	/**
	 * <p>根据给定userID、status、sendStatus查询记录,并根据 额度级别进行倒序排序</p>
	 * @param userId 用户id
	 * @param statusList 状态
	 * @param sendStatusList 发送状态
	 * @return List<CreditsUpgrade>
	 * */
	List<CreditsUpgrade> queryByUserIdWithStatus(@Param("userId") Long userId, @Param("statusList") List<Integer> statusList, @Param("sendStatusList") List<Integer> sendStatusList);

	/**
	 * <p>更新</p>
	 * @param map 待更新的数
	 * @param userId 更新的前置条件
	 * @param status 更新的前置条件
	 * @return int
	 * */
	int updateSelectiveByUserId(@Param("entity") Map<String, Object> map, @Param("userId") Long userId, @Param("status") Integer status);

	/**
	 * <p>查询临时额度正常的数据并且以userId进行分组、最后以等级进行倒序排序
	 *    (需要查询临时额度过期前一天的用户进行短信及推送通知...)
	 * </p>
	 * @param borrowRepayState 还款计划中的状态
	 * @param creditsUpgradeStatus 临时额度中的状态
	 * @param sendStatusList 发送状态集
	 * @return List<CreditsUpgrade>
	 * */
	List<CreditsUpgrade> queryCreditsByStatusWithGroupUserId(@Param("borrowRepayState") Integer borrowRepayState,
                                                             @Param("creditsUpgradeStatus") Integer creditsUpgradeStatus,
                                                             @Param("sendStatusList") List<Integer> sendStatusList);

	/**
	 * <p>根据给定userId查找用户临时额度</p>
	 * @param userId
	 * @param status
	 * @return CreditsUpgrade
	 * */
	List<CreditsUpgrade> queryCreditsUpgradeByUserId(@Param("userId") Long userId, @Param("status") Integer status);

	/***
	 * <p>查询临时额度已失效的用户并进行去重处理(前置条件是用户没有未还的订单)、按阶段、过期时间倒序排序最后对user_id进行分组 </p>
	 * @param borrowRepayState
	 * @param creditsUpgradeStatus
	 * @return List<CreditsUpgrade>
	 * */
	List<CreditsUpgrade> queryCreditsInvalidByParams(@Param("borrowRepayState") Integer borrowRepayState, @Param("creditsUpgradeStatus") Integer creditsUpgradeStatus);

	/**
	 * <p>重置用户已使用额度为0</p>
	 * @param userId
	 * @param status
	 * @return int
	 * */
	int updateUserTmpUsedResetDefault(@Param("userId") Long userId, @Param("status") Integer status);
	
}
