package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.RcZhimaIndustry;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 芝麻行业关注名单Dao
 * 

 * @version 1.0.0
 * @date 2017-03-06 18:48:00
 *
 *
 * 
 *
 */
@RDBatisDao
public interface RcZhimaIndustryMapper extends BaseMapper<RcZhimaIndustry,Long> {

	/**
	 * 根据用户Id查询芝麻信用信息
	 * @param userId
	 * @return
	 */
	List<RcZhimaIndustry> findByuserId(@Param("userId") Long userId);

}
