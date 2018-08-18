package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.core.model.StagingRecordModel;
import com.adpanshi.cashloan.system.domain.SysUser;
import com.github.pagehelper.Page;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 借款主信息表Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 15:32:54

 *
 *
 */
public interface BorrowMainService extends BaseService<BorrowMain, Long> {

    /**
     * 修改借款状态
     * @param id
     * @param state
     * @return
     */
    int modifyState(long id, String state) ;

    /**
     * 修改借款状态和备注
     * @param id
     * @param state
     * @return
     */
    int modifyStateAndRemark(long id, String state, String remark);


    /**
     * 查询失败记录
     * @param userId
     * @return
     */
    BorrowMain findLast(Long userId);

    /**
     * 查询用户未完成的借款
     * @param userId
     * @return
     */
    List<BorrowMain> findUserUnFinishedBorrow(Long userId);

    	/**
	 * 支付时更新状态
	 * @return
	 */
    void updatePayState(Long borrowId, String state);

    /**
     * 支付时更新状态
     *
     * @return
     */
    void updatePayState(Long borrowId, String state, Date loanTime, Date repayTime);

    /**
     * 查询未还款
     * @param paramMap
     * @return
     */
    BorrowMain findByUserIdAndState(Map<String, Object> paramMap);

    /**
     * 查询初审通过和初审建议人工复审的订单
     * @param userId
     * @return
     */
    List<BorrowMain> selectBorrowWithAudit(long userId);

    /**
     * <p>查询指定用户分页查询所有分期记录、默认以id倒序排序</p>
     * @param userId
     * @param current
     * @param pageSize
     * @return List<BorrowMain>
     * */
    Page<StagingRecordModel> pageByUserId(Long userId, int current, int pageSize);

    /**
     * 查询所有用户所有订单信息
     * @param userId
     * @return
     */
    Page<BorrowMain> selectBorrowByUserId(long userId, int current, int pageSize);

    /**
     * 查询指定条件所有订单信息
     * @param searchParams
     * @param pageSize
     * @param current
     * @return
     */
    List<BorrowMainModel> selectBorrowList(Map<String, Object> searchParams, int current, int pageSize);
    /**
    * @Description:  查询还款信息
    * @Param: [id]
    * @return: java.util.Map
    * @Author: Mr.Wange
    * @Date: 2018/7/24
    */
    Map qryRepayLog(Long id);
    /**
     * 查询指定条件所有订单信息
     * @param searchParams
     * @return
     */
    List<BorrowMainModel> selectBorrowListByMap(Map<String, Object> searchParams);
    /**
     * 分配:订单批量指派信审人员
     * @param sysUser
     * @param borrowMainIds
     * @param userId
     * @param userName
     * @date : 2018-1-03 19:41
     * @author: nmnl
     */
    Map<String, Object> orderAllotSysUsers(SysUser sysUser, Long[] borrowMainIds, Long userId, String userName);

    /**
     * <p> 查询指定用户是否存在未还款的订单数量</p>
     * @param userId
     * @return 
     * */
    int getUnRepayCountByUserId(Long userId);

    /**
     * 根据order_no查找订单状态为20（自动审核通过）,26（人工审核通过）的借款订单
     * borrow中只有id, order_no, state三个字段有值，其他字段无值
     * @auther yecy 20180228
     * @param orderNoList
     * @return
     */
    List<BorrowMain> findWaitPayByOrderNos(Collection<String> orderNoList);

    /**
     * 根据id查找借款订单
     *  @auther yecy 20180228
     * @param idList
     * @return
     */
    List<BorrowMain> findByIds(Collection<Long> idList);
}
