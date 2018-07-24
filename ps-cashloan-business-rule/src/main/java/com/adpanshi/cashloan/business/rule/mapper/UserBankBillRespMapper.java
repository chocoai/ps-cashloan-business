package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.UserBankBillResp;
import org.apache.ibatis.annotations.Param;

@RDBatisDao
public interface UserBankBillRespMapper extends BaseMapper<UserBankBillResp, Long> {

    /**
     * 根据userId,查找最后一个账单响应数据
     * @param userId
     * @return
     */
    UserBankBillResp findLastBillRespByUserId(@Param("userId") Long userId);
}
