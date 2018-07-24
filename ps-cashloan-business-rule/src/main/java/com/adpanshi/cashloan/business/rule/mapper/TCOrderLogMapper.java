package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.TCOrderLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RDBatisDao
public interface TCOrderLogMapper extends BaseMapper<TCOrderLog, Long> {

	
	/**
	 * 插入
	 * @return 
	 */
	int insert(TCOrderLog orderLog);

	/**
	 * @param mobile
	 * @return
	 */
	TCOrderLog getNewestByMobile(String mobile);

	/**
	 * 更新为下单成功
	 * @param orderId 订单号
	 * @param successStatus 成功状态
	 * @return 
	 */
	int updateOrderSuccess(@Param("orderId") String orderId, @Param("successStatus") String successStatus);

	/**
	 * 根据userId查询
	 * @param orderId
	 * @return
	 */
	TCOrderLog findByOrderId(String orderId);

	/**
	 * 查询用户最新的订单号
	 * @param userId
	 * @return
	 */
	String findNewOrderIdByUserId(@Param("userId") Long userId);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByUserId(List<Long> list);

	/**
	 * 请求id
	 * @param list
	 * @return
	 */
	List<String> listOrderIdByUserId(List<Long> list);

}
