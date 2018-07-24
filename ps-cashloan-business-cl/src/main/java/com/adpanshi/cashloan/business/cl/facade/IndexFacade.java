package com.adpanshi.cashloan.business.cl.facade;

import com.adpanshi.cashloan.business.cl.model.BorrowConfirmModel;
import com.adpanshi.cashloan.business.cl.model.IndexPageModel;
import com.adpanshi.cashloan.business.cl.model.RepayShowModel;
import com.adpanshi.cashloan.business.rule.model.IndexModel;

import java.util.List;

/**
 * @author yecy
 * @date 2017/12/16 11:36
 */
public interface IndexFacade {

    IndexPageModel getIndex(Long userId);

    /**
     * 用户未登录下的还款计划
     *
     * @param amount
     * @param timeLimit
     * @return
     */
    RepayShowModel getRepayPlan(Double amount, Integer timeLimit);

    RepayShowModel getRepayPlan(Double amount, Integer timeLimit, Long userId);


    /**
     * 首页借款成功轮播信息
     *
     * @return
     */
    List<IndexModel> listIndexWithPhone();

    /**
     * 用户借款确认界面
     *
     * @param userId
     * @param maxAmount 用户最大可借额度
     */
    BorrowConfirmModel getConfirmBorrow(Long userId, Double maxAmount);
}
