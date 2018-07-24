package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowMainProgress;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;

import java.util.List;
import java.util.Map;

/**
 * 借款主信息表Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 16:09:17

 *
 *
 */
public interface BorrowMainProgressService extends BaseService<BorrowMainProgress, Long> {

    /**
     * 添加借款进度
     *
     * @param borrow
     * @param state
     */
    void savePressState(BorrowMain borrow, String state);

    /**
     * 更新最近的借款进度
     *
     * @param borrow
     * @param state
     */
    void updateNearestPressState(BorrowMain borrow, String state);

    /**
     * 获取借款流程
     * @param borrowMainId
     * @return
     */
    List<BorrowMainProgress> getProcessByMainId(Long borrowMainId);

    /**
     * 获取借款流程
     * @param id
     * @Param borrow
     * @return
     */
    List<BorrowMainProgress> getProcessById(Long id, Borrow borrow);

    /**
     * 查询借款
     * @param borrowMainId
     * @return
     */
    Map<String,Object> result(Long borrowMainId);

}
