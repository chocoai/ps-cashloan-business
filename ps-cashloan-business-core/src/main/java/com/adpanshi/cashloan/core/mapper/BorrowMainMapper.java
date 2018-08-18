package com.adpanshi.cashloan.core.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.core.model.StagingRecordModel;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 借款主信息表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 15:32:54
 *
 */
@RDBatisDao
public interface BorrowMainMapper extends BaseMapper<BorrowMain, Long> {

    /**
     * 查询失败记录
     * @param searchMap
     * @return
     */
    BorrowMain findLast(Map<String, Object> searchMap);


    /**
     * 查询用户未完成的借款
     * @param userId
     * @return
     */
    List<BorrowMain> findUserUnFinishedBorrow(@Param("userId") Long userId);

    /**
     * 支付时更新Borrow状态
     * @param paramMap
     * @return
     */
    int updatePayState(Map<String, Object> paramMap);

    /**
     * 查询未还款
     * @param paramMap
     * @return
     */
    BorrowMain findByUserIdAndState(Map<String, Object> paramMap);

    /**
     * 更新挂起状态订单备注
     * @param params
     */
    void updateBorrowRemark(Map<String, Object> params);

    /**
     * 更新挂起状态订单备注
     * @param params
     */
    void updateBorrowRemarkNoState(Map<String, Object> params);

    /**
     * 人工复审修改状态
     * @param map
     * @return
     */
    int reviewState(Map<String, Object> map);

    /**
     * 查询初审通过和初审建议人工复审的订单
     * @param userId
     * @return
     */
    List<BorrowMain> selectBorrowWithAudit(long userId);

    /**
     * <p>查询指定用户分页查询所有分期记录、默认以id倒序排序<p>
     * @param data
     * @return
     * **/
    List<StagingRecordModel> pageByUserId(Map<String, Object> data);

    /**
     * <p>根据给定主键id查询主订单(关联银行卡)</p>
     * <p>包含银行卡号及名称</p>
     * @param id
     * @return BorrowMain
     * */
    BorrowMain findById(Long id);

    /**
     * 查询当前用户所有订单信息
     * @param userId
     * @return List<BorrowMain>
     */
    List<BorrowMain> selectBorrowByUserId(long userId);

    /**
     * 查询所有订单信息 (用户, 用户基本信息)
     * @param data
     * @return List<BorrowMain>
     */
    List<BorrowMainModel> selectBorrowList(Map<String, Object> data);

    /**
     * 查询信审人员订单分配最少的（无关联）
     * @param data
     * @return BorrowMainModel
     */
    BorrowMainModel selectBorrowSysCount(Map<String, Object> data);

    /**
     * <p>根据brrowId、userId查询主订单</p>
     * @param borrowId
     * @param userId
     * @return
     * */
    BorrowMain getBowMainByBorrowIdWithUserId(@Param("borrowId") Long borrowId, @Param("userId") Long userId);

    /**
     * <p> 查询指定用户是否存在未还款的订单数量</p>
     * @param userId
     * @return
     * */
    int getUnRepayCountByUserId(@Param("userId") Long userId);

    /**
     * 根据order_no查找订单状态为20（自动审核通过）,26（人工审核通过）的借款订单
     * borrow中只有id, order_no, state三个字段有值，其他字段无值
     *  @auther yecy 20180228
     * @param orderNoList
     * @return
     */
    List<BorrowMain> findWaitPayByOrderNos(@Param("orderNoList") Collection<String> orderNoList);

    /**
     * 根据id查找借款订单
     *  @auther yecy 20180228
     * @param idList
     * @return
     */
    List<BorrowMain> findByIds(@Param("idList") Collection<Long> idList);
}
