package com.adpanshi.cashloan.business.cr.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.cr.domain.RankDetail;
import com.adpanshi.cashloan.business.cr.mapper.RankDetailMapper;
import com.adpanshi.cashloan.business.cr.service.RankDetailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 评分卡等级详情表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-06 11:27:25
 *
 *
 * 
 *
 */
 
@Service("rankDetailService")
public class RankDetailServiceImpl extends BaseServiceImpl<RankDetail, Long> implements RankDetailService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RankDetailServiceImpl.class);
   
    @Resource
    private RankDetailMapper rankDetailMapper;




	@Override
	public BaseMapper<RankDetail, Long> getMapper() {
		return rankDetailMapper;
	}




	@Override
	public int save(RankDetail rankDetail) {
		return rankDetailMapper.save(rankDetail);
	}




	@Override
	public int updateSelective(Map<String, Object> rankDetailMap) {
		return rankDetailMapper.updateSelective(rankDetailMap);
	}




	@Override
	public Page<RankDetail> page(Map<String, Object> searchMap, int current,
			int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<RankDetail> list = rankDetailMapper.listSelective(searchMap);
		return (Page<RankDetail>)list;
	}
	
}