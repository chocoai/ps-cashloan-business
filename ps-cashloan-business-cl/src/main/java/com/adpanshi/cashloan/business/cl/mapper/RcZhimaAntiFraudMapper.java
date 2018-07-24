package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.RcZhimaAntiFraud;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 芝麻反欺诈Dao
 * 

 * @version 1.0.0
 * @date 2017-03-06 18:47:03
 *
 *
 * 
 *
 */
@RDBatisDao
public interface RcZhimaAntiFraudMapper extends BaseMapper<RcZhimaAntiFraud,Long> {

	List<RcZhimaAntiFraud> findByuserId(@Param("userId") Long userId);

}
