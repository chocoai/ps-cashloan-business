package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ProfitLevel;
import com.adpanshi.cashloan.business.cl.mapper.ProfitLevelMapper;
import com.adpanshi.cashloan.business.cl.service.ProfitLevelService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分润等级ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:58:10
 *
 *
 * 
 *
 */
 
@Service("profitLevelService")
public class ProfitLevelServiceImpl extends BaseServiceImpl<ProfitLevel, Long> implements ProfitLevelService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProfitLevelServiceImpl.class);
   
    @Resource
    private ProfitLevelMapper profitLevelMapper;




	@Override
	public BaseMapper<ProfitLevel, Long> getMapper() {
		return profitLevelMapper;
	}




	@Override
	public List<ProfitLevel> find() {
		return profitLevelMapper.listAll();
	}




	@Override
	public int update(long id, double rate) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		map.put("rate", rate);
		return profitLevelMapper.updateSelective(map);
	}
	
}