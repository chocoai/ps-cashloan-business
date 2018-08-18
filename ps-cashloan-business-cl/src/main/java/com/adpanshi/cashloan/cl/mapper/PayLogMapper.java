package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.PayLog;
import com.adpanshi.cashloan.cl.model.ManagePayLogModel;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 支付记录Mapper
 *
 * @version 1.0.0
 * @date 2017-03-07 16:18:56
 * @author
 */
@RDBatisDao
public interface PayLogMapper extends BaseMapper<PayLog, Long> {

	/**
	 * 列表查询
	 * 
	 * @param searchMap
	 * @return
	 */
	List<ManagePayLogModel> page(Map<String, Object> searchMap);

	/**
	 * 查看详情
	 * 
	 * @param id
	 * @return
	 */
	ManagePayLogModel findDetail(Long id);
	
	
	/**
	 * 更新状态校验订单状态
	 * @param paramMap
	 * @return
	 */
	int updateState(Map<String, Object> paramMap);

	/**
	 * 据条件查询对账List
	 *
	 * @param paramMap
	 * @return
	 */
	List<PayLog> findCheckList(Map<String, Object> paramMap);


	/**
	 * 获取一条记录
	 *
	 * @param paramMap
	 *            查询条件
	 * @return 查询结果
	 */
	PayLog findSelectiveOne(Map<String, Object> paramMap);

	/**
	 * <p>根据给定条件查询最近一条支付记录</p>
	 * @param paramMap
	 * @return
	 *
	 */
	PayLog getPayLogByLateOrderNo(Map<String, Object> paramMap);

	/**
	 * <p>根据orderNo、borrowId查找最近(创建时间:create_time倒序)的一条支付记录</p>
	 * @param orderNo  订单号(必填)
	 * @param borrowId 借款id(非必填)
	 * @return PayLog
	 * */
	PayLog findPayLogByLastOrderNoWithBorrowId(@Param("orderNo") String orderNo, @Param("borrowMainId") String borrowId);
	
	/**
	 * <p>根据订单查询首笔支付记录(同一订单中必需是没有支付成功的记录)</p>
	 * @param orderNo 订单号
	 * @return PayLog
	 * */
	PayLog getPayLogByFirstOrderNo(@Param("orderNo") String orderNo);
}
