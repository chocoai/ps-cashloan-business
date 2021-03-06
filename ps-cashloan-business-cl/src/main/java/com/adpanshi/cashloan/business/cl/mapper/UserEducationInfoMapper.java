package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserEducationInfo;
import com.adpanshi.cashloan.business.cl.model.UserEducationInfoModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 学信查询记录表Mapper
 * 

 * @version 1.0.0
 * @date 2017-04-18 16:30:45
 *
 *
 *
 *
 */
@RDBatisDao
public interface UserEducationInfoMapper extends BaseMapper<UserEducationInfo,Long> {

	/**
	 * 列表查询
	 * @param searchMap
	 * @return
	 */
	List<UserEducationInfoModel> page(Map<String, Object> searchMap);

    

}
