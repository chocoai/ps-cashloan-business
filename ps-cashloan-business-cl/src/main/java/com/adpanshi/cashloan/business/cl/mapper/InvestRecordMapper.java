package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestRecord;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 投资人投资记录表Dao
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:57:14
 */
@RDBatisDao
public interface InvestRecordMapper extends BaseMapper<InvestRecord, Long> {

    int saveAll(List<InvestRecord> invests);

}
