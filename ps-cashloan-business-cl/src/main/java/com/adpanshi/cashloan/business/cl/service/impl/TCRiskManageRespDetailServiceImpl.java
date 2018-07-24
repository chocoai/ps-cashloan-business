package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.TCRiskManageRespDetailMapper;
import com.adpanshi.cashloan.business.cl.service.TCRiskManageRespDetailService;
import com.adpanshi.cashloan.business.rule.model.tianchuang.RiskManageRespDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 风控结果信息
 * @author ppchen
 * 2017年9月1日 下午4:29:12
 * 
 */
@Service("tcRiskManageRespDetailService")
public class TCRiskManageRespDetailServiceImpl implements TCRiskManageRespDetailService{
	
	@Autowired
	private TCRiskManageRespDetailMapper tcRiskManageRespDetailMapper;
	
	@Override
	public void insert(RiskManageRespDetail riskManageRespDetail) {
		tcRiskManageRespDetailMapper.insert(riskManageRespDetail);
	}
	
}
