package com.adpanshi.cashloan.rule.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.rule.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 借款信息表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:13:31
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ClBorrowMapper extends BaseMapper<Borrow,Long> {


	/**
	 * 首页信息查询
	 * @return
	 */
	List<IndexModel> listIndex();

	/**
	 * 订单查询
	 * @param searchMap
	 * @return
	 */
	List<ClBorrowModel> findBorrow(Map<String, Object> searchMap);

	/**
	 * 查看借款
	 * @param searchMap
	 * @return
	 */
	List<RepayModel> findRepay(Map<String, Object> searchMap);

	/**
	 * 计时任务计算逾期
	 * @return
	 */
	List<RepayModel> compute();
	
	/**
	 * 查询
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listModel(Map<String, Object> params);

	/**
	 * 关联用户的借款分页查询后台列表显示 && 机审通过的所有订单
	 * @author:M
	 * @date:2017-07-10
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> automaticListModel(Map<String, Object> params);

	/**
	 * 查询所有
	 * @param searchMap
	 * @return
	 */
	List<ClBorrowModel> listAll(Map<String, Object> searchMap);

	/**
	 * 查询可借款用户
	 * @return
	 */
	List<ManageBorrowTestModel> seleteUser();
	
	/**
	 * 更新借款状态
	 * @param state
	 * @param id
	 * @return
	 */
	int updateState(@Param("state") String state, @Param("id") Long id);
	/**
	 * 借款部分还款信息
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listBorrowModel(Map<String, Object> params);



	/**
	 * 导出查询
	 * @param params
	 * @return
	 */
	List<ManageBorrowExportModel> listExportModel(Map<String, Object> params);


	/**
	 * 人工复审通过查询
	 * @return
	 */
	List<ManageBorrowModel> listReview(Map<String, Object> params);


	/**
	 *  人工复审时查看用户所有借款订单的信息（包含逾期天数和逾期金额）
	 * @param params
	 * @return
	 */
	List<ManageBorrowModel> listBorrowModelForPersonCheck(Map<String, Object> params);



    /**
     * 我的信审订单查询
     */
	List<ManageBorrowModel> listRevieworder(Map<String, Object> params);
	/**
	 * 更新挂起状态订单备注
	 * @param params
	 */
	void updateBorrowRemark(Map<String, Object> params);

	/**
	 * 查询待人工审核订单信息
	 */
	List<ManageBorrowModel> listReview1(Map<String, Object> params);
	/**
	 * 更新订单挂起状态进度
	 */
	void  updateOrderStateInfo(Map<String, Object> params);

	/**
	 * 统计成功借款次数
	 * @param l
	 * @return
	 */
	long countBorrow(long l);

	/**
	 * 查询未还款订单
	 * @param borrowMap
	 * @return
	 */
	Borrow findRepayBorrow(Map<String, Object> borrowMap);

	/**
	 * 查询最后一笔订单
	 * @param borrowMap
	 * @return
	 */
	Borrow findRepayBorrowMain(Map<String, Object> borrowMap);

	/**
	 * 查询未还款
	 * @param paramMap
	 * @return
	 */
	Borrow findByUserIdAndState(Map<String, Object> paramMap);

	/**
	 * 批量保存
	 */
	int saveAll(@Param("list") List<Borrow> borrowList);

	/**
	 * <p>根据给定条件统计</p>
	 * @param userId
	 * @param state
	 * @param borrowMainId
	 * @return 符合条件的记录条数
	 * */
	int queryCount(@Param("userId") Long userId, @Param("state") String state, @Param("borrowMainId") Long borrowMainId);

	/**
	 * <p>(分期还款明细列表)  根据给定userId及borrowMainId 查询还款明细列表 </p>
	 * @param userId
	 * @param borrowMainId
	 * @return
	 * */
	List<StaginRepaymentModel> queryRepaymentsByUserIdWithBorMainId(@Param("userId") Long userId, @Param("borrowMainId") Long borrowMainId);

	/**
	 * 根据orderNo列表 查找borrow列表
	 * @param orderNos
	 * @return
	 */
	List<Borrow> findBorrowsByOrderNo(@Param("list") List<String> orderNos);

	/**
	 * <p>(分期还款-还款计划列表)  根据给定userId及borrowMainId 查询还款计划列表 </p>
	 * @param userId
	 * @param borrowMainId
	 * @return
	 * */
	List<StaginRepaymentPlanModel> queryRepaymentPlanByUserIdWithBorMainId(@Param("userId") Long userId, @Param("borrowMainId") Long borrowMainId);

	/**
	 * <p>根据给定订单号集合与userId、状态(可选项)、查询订单号</p>
	 * @param userId
	 * @param state 可选项
	 * @param orderNoList
	 * @return
	 * */
	List<Borrow> queryBorrowByOrderNosWithUserId(@Param("userId") long userId, @Param("state") String state, @Param("orderNoList") List<String> orderNoList);

	/**
	 * 获取逾期金额
	 * @param borrowMainId
	 * @return
	 */
	Map queryBorrowPenaltyByBorrowId(@Param("borrowMainId") long borrowMainId);

}
