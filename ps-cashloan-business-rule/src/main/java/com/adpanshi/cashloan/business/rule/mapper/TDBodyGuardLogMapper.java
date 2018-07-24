package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.TDBodyGuardLog;

/**
 * 同盾信贷保镖第三方请求记录Dao
 * @version 1.0.0
 * @date 2017-04-26 15:26:56
 */
@RDBatisDao
public interface TDBodyGuardLogMapper extends BaseMapper<TDBodyGuardLog, Long> {
}
