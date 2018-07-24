package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.Coupon;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

/**
 * 优惠券表Dao
 *
 */
@RDBatisDao
public interface CouponMapper extends BaseMapper<Coupon, Long> {

}
