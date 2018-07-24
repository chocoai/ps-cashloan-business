package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.lianxin.LianXinReqLog;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

/**
 * 联信请求日志
 * Created by cc on 2017-08-31 20:33.
 */
@RDBatisDao
public interface LianXinReqLogMapper {

    /**
     * 保存联信请求日志
     * @param lianXinReqLog
     */
    void save(LianXinReqLog lianXinReqLog);

    int findByRequestId(String requestId);
}
