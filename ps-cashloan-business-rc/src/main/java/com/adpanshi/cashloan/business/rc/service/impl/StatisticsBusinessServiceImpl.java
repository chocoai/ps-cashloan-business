package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.StatisticsBusiness;
import com.adpanshi.cashloan.business.rc.mapper.StatisticsBusinessMapper;
import com.adpanshi.cashloan.business.rc.service.StatisticsBusinessService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 风控数据统计接口ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-04-13 17:57:55
 *
 *
 *
 *
 */
 
@Service("statisticsBusinessService")
public class StatisticsBusinessServiceImpl extends BaseServiceImpl<StatisticsBusiness, Long> implements StatisticsBusinessService {
	
    @Resource
    private StatisticsBusinessMapper statisticsBusinessMapper;

	@Override
	public BaseMapper<StatisticsBusiness, Long> getMapper() {
		return statisticsBusinessMapper;
	}

	@Override
	public Page<StatisticsBusiness> page(Map<String, Object> params,int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		Page<StatisticsBusiness> page = (Page<StatisticsBusiness>) statisticsBusinessMapper.listSelective(params);
		return page;
	}

	@Override
	public List<StatisticsBusiness> listSelective(Map<String, Object> params) {
		return statisticsBusinessMapper.listSelective(params);
	}
	
}