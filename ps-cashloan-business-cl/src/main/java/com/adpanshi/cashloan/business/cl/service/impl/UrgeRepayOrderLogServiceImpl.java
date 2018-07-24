package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrder;
import com.adpanshi.cashloan.business.cl.domain.UrgeRepayOrderLog;
import com.adpanshi.cashloan.business.cl.mapper.BorrowProgressMapper;
import com.adpanshi.cashloan.business.cl.mapper.UrgeRepayOrderLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.UrgeRepayOrderMapper;
import com.adpanshi.cashloan.business.cl.model.UrgeRepayOrderModel;
import com.adpanshi.cashloan.business.cl.service.UrgeRepayOrderLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 催款记录表ServiceImpl
 *
 * @author huangqin
 * @version 1.1.0
 * @date 2018-01-01 19:23
 */

@Service("urgeRepayOrderLogService")
public class UrgeRepayOrderLogServiceImpl extends BaseServiceImpl<UrgeRepayOrderLog, Long> implements UrgeRepayOrderLogService {
    @Resource
    private UrgeRepayOrderMapper urgeRepayOrderMapper;

    @Resource
    private UrgeRepayOrderLogMapper urgeRepayOrderLogMapper;

    @Resource
    private ClBorrowMapper clBorrowMapper;

    @Resource
    private BorrowProgressMapper borrowProgressMapper;

    @Override
    public BaseMapper<UrgeRepayOrderLog, Long> getMapper() {
        return urgeRepayOrderLogMapper;
    }

    @Override
    public Page<UrgeRepayOrderLog> list(Map<String, Object> params, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<UrgeRepayOrderLog>) urgeRepayOrderLogMapper.listSelective(params);
    }

    @Override
    public void saveOrderInfo(UrgeRepayOrderLog orderLog, UrgeRepayOrder order) {
        if (StringUtils.isEmpty(orderLog.getState())) {
            orderLog.setState(UrgeRepayOrderModel.STATE_ORDER_URGE);
        }
        orderLog.setDueId(order.getId());
        orderLog.setBorrowId(order.getBorrowId());
        orderLog.setUserId(order.getUserId());
        //更新催收记录
        urgeRepayOrderLogMapper.save(orderLog);
        //更新催收订单进度
        order.setCount(order.getCount() + 1);
        order.setState(orderLog.getState());
        urgeRepayOrderMapper.update(order);
        if (order.getState().equals(UrgeRepayOrderModel.STATE_ORDER_BAD)) {
            //更新借款状态
            Borrow b = clBorrowMapper.findByPrimary(order.getBorrowId());
            Map<String, Object> stateMap = new HashMap<String, Object>();
            stateMap.put("id", order.getBorrowId());
            stateMap.put("state", BorrowModel.STATE_BAD);
            clBorrowMapper.updateSelective(stateMap);
            //添加借款进度
            BorrowProgress bp = new BorrowProgress();
            bp.setBorrowId(b.getId());
            bp.setUserId(b.getUserId());
            bp.setRemark(BorrowModel.convertBorrowRemark(BorrowModel.STATE_BAD));
            bp.setState(BorrowModel.STATE_BAD);
            bp.setCreateTime(new Date());
            borrowProgressMapper.save(bp);
        }
    }

    @Override
    public boolean deleteByOrderId(Long dueId) {
        return urgeRepayOrderLogMapper.deleteByOrderId(dueId) > 0;
    }
}