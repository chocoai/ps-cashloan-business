package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.InternetTraffic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RDBatisDao
public interface TCInternetTrafficMapper extends BaseMapper<InternetTraffic,Long> {

	public void batchInsert(@Param("internetTraffics") List<InternetTraffic> internetTraffics);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);

}
  