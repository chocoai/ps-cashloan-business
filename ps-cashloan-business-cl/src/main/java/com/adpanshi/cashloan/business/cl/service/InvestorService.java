package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Investor;
import com.adpanshi.cashloan.business.core.common.service.BaseService;

import java.util.Collection;
import java.util.List;

/**
 * 投资标中投资人信息表Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:55:33

 *
 *
 */
public interface InvestorService extends BaseService<Investor, Long>{

    int saveAll(List<Investor> investors);

    List<Investor> getExistedLogin(Collection<String> loginName);
    
}
