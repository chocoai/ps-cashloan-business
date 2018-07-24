package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.business.cl.mapper.UrgeRepayOrderMapper;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayOrderModel;
import com.adpanshi.cashloan.business.cl.service.UrgeRepayOrderService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 催款计划ServiceImpl
 *
 * @version 1.0.0
 * @date 2017-03-07 14:21:58
 * @updateDate 2017/12/25
 * @updator huangqin
 */

@Service("urgeRepayOrderService")
public class UrgeRepayOrderServiceImpl extends BaseServiceImpl<UrgeRepayOrder, Long> implements UrgeRepayOrderService {

    @Resource
    private UrgeRepayOrderMapper urgeRepayOrderMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private UserBaseInfoMapper userBaseinfoMapper;

    @Override
    public BaseMapper<UrgeRepayOrder, Long> getMapper() {
        return urgeRepayOrderMapper;
    }

    @Override
    public Page<UrgeRepayOrderModel> list(Map<String, Object> params, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<UrgeRepayOrderModel>) urgeRepayOrderMapper.listUrgeTotalOrder(params);
    }

    public Page<UrgeRepayOrderModel> listModel(Map<String, Object> params, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<UrgeRepayOrderModel>) urgeRepayOrderMapper.listModel(params);
    }


    @Override
    public boolean isCollector(Long id) {
        UrgeRepayOrder uro = urgeRepayOrderMapper.findByPrimary(id);
        if (uro == null) {
            return false;
        }
        //是待催收、催收中、承诺还款、坏账则不更改状态
        if ((UrgeRepayOrderModel.STATE_ORDER_WAIT.equals(uro.getState())
                || UrgeRepayOrderModel.STATE_ORDER_URGE.equals(uro.getState())
                || UrgeRepayOrderModel.STATE_ORDER_PROMISE.equals(uro.getState())
                || UrgeRepayOrderModel.STATE_ORDER_BAD.equals(uro.getState()))) {
            return true;
        }
        return false;
    }

    @Override
    public UrgeRepayOrder findOrderByMap(Map<String, Object> orderMap) {
        return urgeRepayOrderMapper.findSelective(orderMap);
    }
    @Override
    public boolean deleteByBorrowId(Long borrowId) {
        return urgeRepayOrderMapper.deleteByBorrowId(borrowId) > 0;
    }
}