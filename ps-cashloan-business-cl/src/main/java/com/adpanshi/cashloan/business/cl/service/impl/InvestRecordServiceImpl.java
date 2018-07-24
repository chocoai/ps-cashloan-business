package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestRecord;
import com.adpanshi.cashloan.business.cl.mapper.InvestRecordMapper;
import com.adpanshi.cashloan.business.cl.service.InvestRecordService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 投资人投资记录表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:57:14

 *
 *
 */
 
@Service("investRecordService")
public class InvestRecordServiceImpl extends BaseServiceImpl<InvestRecord, Long> implements InvestRecordService {
	
    private static final Logger logger = LoggerFactory.getLogger(InvestRecordServiceImpl.class);
   
    @Resource
    private InvestRecordMapper investRecordMapper;

	@Override
	public BaseMapper<InvestRecord, Long> getMapper() {
		return investRecordMapper;
	}

	@Override
	public int saveAll(List<InvestRecord> invests) {
		if(CollectionUtils.isEmpty(invests)){
			return 0;
		}
		return investRecordMapper.saveAll(invests);
	}
}
