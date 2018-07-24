package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Invest;
import com.adpanshi.cashloan.business.cl.mapper.InvestMapper;
import com.adpanshi.cashloan.business.cl.service.InvestService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 银程投资标存储表ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:51:50
 */

@Service("investService")
public class InvestServiceImpl extends BaseServiceImpl<Invest, String> implements InvestService {

    private static final Logger logger = LoggerFactory.getLogger(InvestServiceImpl.class);

    @Resource
    private InvestMapper investMapper;

    @Override
    public BaseMapper<Invest, String> getMapper() {
        return investMapper;
    }

    @Override
    public int saveAll(List<Invest> invests) {
        if(CollectionUtils.isEmpty(invests)){
            return 0;
        }
        return investMapper.saveAll(invests);
    }
}
