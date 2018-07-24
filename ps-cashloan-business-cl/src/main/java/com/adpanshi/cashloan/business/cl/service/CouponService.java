package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.Coupon;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;

public interface CouponService extends BaseService<Coupon, Long> {

    /**
     * 获取用户优惠券列表
     * @param userId
     * @return
     */
    List<Coupon> listCoupon(Long userId);
}
