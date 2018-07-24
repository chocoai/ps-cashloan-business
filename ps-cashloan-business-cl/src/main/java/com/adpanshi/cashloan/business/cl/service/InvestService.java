package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Invest;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.List;

/**
 * 银程投资标存储表Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:51:50

 *
 *
 */
public interface InvestService extends BaseService<Invest, String>{

    /**
     * 批量保存
     * @return
     */
    int saveAll(List<Invest> invests);


}
