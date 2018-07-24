package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.Opinion;
import com.adpanshi.cashloan.business.cl.model.OpinionModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;


/**
 * 意见反馈表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:30:57
 *
 *
 * 
 *
 */
@RDBatisDao
public interface OpinionMapper extends BaseMapper<Opinion,Long> {
	
	List<OpinionModel> listModel(Map<String, Object> searchMap);

}
