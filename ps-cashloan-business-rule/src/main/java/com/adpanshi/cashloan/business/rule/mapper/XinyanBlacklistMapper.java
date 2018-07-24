
package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.XinyanBlacklist;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-10-21 16:33:33
 * @history
 */
@RDBatisDao
public interface XinyanBlacklistMapper  extends BaseMapper<XinyanBlacklist,Long>{
	
	/**
	 * <p>根据给定idNo查找最近一条记录</p>
	 * @param idNo 身份证号
	 * @return XinyanBlacklist
	 * */
	XinyanBlacklist getXinyanBlacklistByLasterIdNo(String idNo);
	
	/**
	 * <p>更新</p>
	 * @param xinyanBlacklist 待更新的对象(id 必填)
	 * @param return int 受影响的行数
	 * */
	int updateSelective(XinyanBlacklist xinyanBlacklist);
	
}
