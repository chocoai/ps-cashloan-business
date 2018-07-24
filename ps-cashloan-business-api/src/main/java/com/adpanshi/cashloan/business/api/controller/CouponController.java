package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.Coupon;
import com.adpanshi.cashloan.business.cl.service.CouponService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/06/19 15:15:01
 * @desc 优惠券信息一览
 * Copyright 浙江盘石 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class CouponController extends BaseController {

    @Resource
    private CouponService couponService;

    /**
     * 我的优惠券
     * @param userId
     * @throws Exception
     */
    @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/coupon/getCoupon.htm", method = RequestMethod.POST)
    public void getCoupon(
            @RequestParam(value="userId") long userId) throws Exception {
        List<Coupon> data = couponService.listCoupon(userId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, data);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }

}
