package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.PaymentRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@RDBatisDao
public interface TCPaymentRecordMapper extends BaseMapper<PaymentRecord,Long> {

	public void batchInsert(@Param("paymentRecords") List<PaymentRecord> paymentRecords);
}
  