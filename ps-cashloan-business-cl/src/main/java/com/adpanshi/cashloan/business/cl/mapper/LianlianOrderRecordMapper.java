
package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.LianlianOrderRecord;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-14 10:08:24
 * @history
 */
@RDBatisDao
public interface LianlianOrderRecordMapper  extends BaseMapper<LianlianOrderRecord,Long>{
	
	/**
	 * <p>根据给定orderNo查询最近一条记录</p>
	 * @param params 查询条件
	 * @return LianlianOrderRecord
	 * */
	LianlianOrderRecord queryByLastOrderNo(Map<String, Object> params);
	
}
