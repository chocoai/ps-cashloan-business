package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Investor;
import com.adpanshi.cashloan.business.cl.mapper.InvestorMapper;
import com.adpanshi.cashloan.business.cl.service.InvestorService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 投资标中投资人信息表ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:55:33
 */

@Service("investorService")
public class InvestorServiceImpl extends BaseServiceImpl<Investor, Long> implements InvestorService {

    static final Logger logger = LoggerFactory.getLogger(InvestorServiceImpl.class);

    @Resource
    private InvestorMapper investorMapper;

    @Override
    public BaseMapper<Investor, Long> getMapper() {
        return investorMapper;
    }

    @Override
    public int saveAll(List<Investor> investors) {
        if (CollectionUtils.isNotEmpty(investors)) {
            return investorMapper.saveAll(investors);
        }
        return 0;
    }

    @Override
    public List<Investor> getExistedLogin(Collection<String> loginName) {
        if (CollectionUtils.isEmpty(loginName)) {
            return new ArrayList<>();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("logins", loginName);
        return investorMapper.getExistedLogin(map);
    }

}
