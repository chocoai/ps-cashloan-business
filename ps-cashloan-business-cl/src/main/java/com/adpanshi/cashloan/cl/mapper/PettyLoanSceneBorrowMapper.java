package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.PettyLoanSceneBorrow;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-06 09:52:47
 * @history
 */
@RDBatisDao
public interface PettyLoanSceneBorrowMapper extends BaseMapper<PettyLoanSceneBorrow,Long>{
	
	/**
	 * <p>一般用于统计或查询是否存在</p>
	 * @param map
	 * @return 记录条数
	 * */
	int queryCount(Map<String, Object> map);
	
}
