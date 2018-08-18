package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.cl.model.BorrowRepayModel;
import com.adpanshi.cashloan.cl.model.ManageBRepayModel;
import com.adpanshi.cashloan.cl.model.StaginRepayMode;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.rule.model.ManageBorrowModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 还款计划Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:42:32
 *
 *
 * 
 *
 */
@RDBatisDao
public interface BorrowRepayMapper extends BaseMapper<BorrowRepay,Long> {

	int updateByBorrowId(Map<String, Object> repayMap);
	/**
	 * 后台还款记录列表 
	 * @param params
	 * @return
	 */
	List<ManageBRepayModel> listModel(Map<String, Object> params);
	
	/**
	 * 逾期更新数据
	 * @param data
	 * @return
	 */
	int updateLate(BorrowRepay data);
	/**
	 * 
	 * @param paramMap
	 */
	int updateParam(Map<String, Object> paramMap);
	/**
	 * 催款管理
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listRepayModel(Map<String, Object> params);
	/**
	 * 逾期未入催
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModelNotUrge(Map<String, Object> params);
	/**
	 * 查询所有
	 * @param searchMap
	 * @return
	 */
	List<BorrowRepayModel> listSelModel(Map<String, Object> searchMap);
	/**
	 * 查询借款成功未还款还款已过还款时间记录(放款成功及逾期但未还款的)
	 * 
	 * @param paramMap
	 * @return
	 */
	List<BorrowRepay> findUnRepay(Map<String, Object> paramMap);
	/**
	 * 查询还款
	 * @param borrowId
	 * @return
	 */
	BorrowRepayModel findOverdue(long borrowId);

	/**
	 * 查询逾期还款
	 * @param orderNo
	 * @return
	 */
	BorrowRepayModel findOverdueByOrderNo(String orderNo);


	/**
	 * 判断是否为复借用户（规则为：成功借款且成功还款，暂不考虑逾期）
	 *
	 * @param userId
	 * @return
	 */
	List<BorrowRepay> findRepayWithUser(long userId);
/**
 * 还款计划表
 */
  BorrowRepay findSelective(Map<String, Object> params);

	/**
	 * 批量保存
	 * @param repayList
	 * @return
	 */
	int saveAll(@Param("list") List<BorrowRepay> repayList);

	/**
	 * <p>根据给定userId及borrowMainId统计用户分期还款明细</p>
	 * @param userId
	 * @param borMainId
	 * @return
	 * */
	StaginRepayMode getStaginRepayDetail(@Param("userId") Long userId, @Param("borMainId") Long borMainId);

	/**
	 * <p>根据给定订单主键ids列表、userId、状态、查询订单</p>
	 * @param userId
	 * @param state 状态(可选项)
	 * @param ids
	 * @return
	 * */
	List<BorrowRepay> queryBorrowRepayByIdsWithUserIdState(@Param("userId") long userId, @Param("state") String state, @Param("ids") List<Long> ids);

	/**
	 * <p>根据给定订单号列表、userId、状态、查询订单</p>
	 * @param userId
	 * @param state 状态(可选项)
	 * @param borrowOrderNos
	 * @return
	 * */
	List<BorrowRepay> queryBorrowRepayByOrderNosWithUserIdState(@Param("userId") long userId, @Param("state") String state, @Param("borrowOrderNos") List<String> borrowOrderNos);

	/**
	 * <p>根据主订单id查询还款计划 </p>
	 * @param borMainId
	 * @return List<BorrowRepay>
	 * */
	List<BorrowRepay> queryBorrowRepayByBorMainId(@Param("borMainId") Long borMainId);

	List<BorrowRepayModel> listModelNew(Map<String, Object> params);
}
