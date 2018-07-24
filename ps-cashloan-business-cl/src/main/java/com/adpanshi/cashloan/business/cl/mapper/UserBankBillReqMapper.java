package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserBankBillReq;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

@RDBatisDao
public interface UserBankBillReqMapper extends BaseMapper<UserBankBillReq, Long> {
	
	/**
	 * <p>检查每日每个用户高级认证项的次数</p>
	 * @param userId
	 * @return true|flase (false:每天认证次数已用尽、true:可以继续认证)
	 * */
	int getAdvancedAuthByCount(@Param("userId") Long userId);
	
}


