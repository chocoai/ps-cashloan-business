package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.model.tianchuang.RiskManageRespDetail;

@RDBatisDao
public interface TCRiskManageRespDetailUselessMapper extends BaseMapper<RiskManageRespDetail,Long> {
	
	public void insert(RiskManageRespDetail riskManageRespDetail);

}
