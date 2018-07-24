
package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.SaasReqRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-04-03 12:03:55
 * @history
 */
@RDBatisDao
public interface SaasReqRecordMapper extends BaseMapper<SaasReqRecord, Long>{
	
	/**
	 * <p>检查每日每个用户高级认证项的次数</p>
	 * @param userId
	 * @param interfaceType
	 * @return true|flase (false:每天认证次数已用尽、true:可以继续认证)
	 * */
	int getAdvancedAuthByCount(@Param("userId") Long userId, @Param("type") Integer interfaceType);
	
}
