package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.Coupon;
import com.adpanshi.cashloan.business.cl.mapper.CouponMapper;
import com.adpanshi.cashloan.business.cl.service.CouponService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 15:50:37
 * @desc 我的优惠券实现类
 * Copyright 浙江盘石 All Rights Reserved
 */
@Service("couponService")
public class CouponServiceImpl extends BaseServiceImpl<Coupon, Long> implements CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Resource
    private CouponMapper couponMapper;

    @Override
    public BaseMapper<Coupon, Long> getMapper() {
        return couponMapper;
    }

    @Override
    public List<Coupon> listCoupon(Long userId){
        logger.info("获取用户优惠券userId:"+userId);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userId",userId);
        List<Coupon> lstCoupon = couponMapper.listSelective(param);
        return lstCoupon;
    }
}
