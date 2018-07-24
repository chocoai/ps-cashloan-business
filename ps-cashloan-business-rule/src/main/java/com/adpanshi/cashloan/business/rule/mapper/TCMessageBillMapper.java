package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.MessageBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface TCMessageBillMapper extends BaseMapper<MessageBill,Long> {

	public void batchInsert(@Param("messageBills") List<MessageBill> messageBills);

	public List<MessageBill> selectByOrderId(String orderId);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);
	
	/**
	 * 短信记录查询
	 */
	public List<MessageBill> selectByOrderIdRecord(Map<String, Object> params);
}
  