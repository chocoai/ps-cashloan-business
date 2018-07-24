package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.BillDetailInfo;

import java.util.List;

@RDBatisDao
public interface TCBillDetailInfoMapper extends BaseMapper<BillDetailInfo,Long> {

	public Long insert(BillDetailInfo billDetailInfo);

	public List<BillDetailInfo> selectByOrderId(String orderId);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);
}
  