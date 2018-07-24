package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.BillList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RDBatisDao
public interface TCBillListMapper extends BaseMapper<BillList,Long> {

	public void batchInsert(@Param("billLists") List<BillList> billLists);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);

}
  