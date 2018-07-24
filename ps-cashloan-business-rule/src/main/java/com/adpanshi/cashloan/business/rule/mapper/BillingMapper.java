package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.Billing;
import org.apache.ibatis.annotations.Param;

@RDBatisDao
public interface BillingMapper extends BaseMapper<Billing, Long> {

	/**
	 * 根据用户Id和账单类型查询账单信息是否存在
	 * @param userId
	 * @param type
	 * @return
	 */
	Boolean existsByUserIdAndType(@Param("userId") long userId, @Param("type") String type);

}
