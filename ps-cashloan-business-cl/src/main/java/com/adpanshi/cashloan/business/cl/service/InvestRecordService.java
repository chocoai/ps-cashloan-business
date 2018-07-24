package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.yincheng.InvestRecord;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;

/**
 * 投资人投资记录表Service
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:57:14
 */
public interface InvestRecordService extends BaseService<InvestRecord, Long> {

    int saveAll(List<InvestRecord> invests);

}
