package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.model.BorrowAndRepayModel;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 逾期计算模型拼装
 *
 * @author yecy
 * @date 2017/12/20 10:11
 */
@RDBatisDao
public interface BorrowRepayModelMapper {

   List<BorrowAndRepayModel> findExpireToRepay(Map<String, Object> map);
}
