package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.CurrentPaying;

import java.util.List;

@RDBatisDao
public interface TCCurrentPayingMapper extends BaseMapper<CurrentPaying,Long> {

	public void insert(CurrentPaying currentPaying);
	
	public CurrentPaying selectByOrderId(String orderId);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);
}
  