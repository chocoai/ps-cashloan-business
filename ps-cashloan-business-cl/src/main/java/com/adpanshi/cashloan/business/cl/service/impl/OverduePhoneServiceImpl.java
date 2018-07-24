package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.OverduePhone;
import com.adpanshi.cashloan.business.cl.mapper.OverduePhoneMapper;
import com.adpanshi.cashloan.business.cl.service.OverduePhoneService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 小贷信用卡催收电话号码库ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-24 19:58:22
 */

@Service("overduePhoneService")
public class OverduePhoneServiceImpl extends BaseServiceImpl<OverduePhone, Long> implements OverduePhoneService {

    private static final Logger logger = LoggerFactory.getLogger(OverduePhoneServiceImpl.class);

    @Resource
    private OverduePhoneMapper overduePhoneMapper;

    @Override
    public BaseMapper<OverduePhone, Long> getMapper() {
        return overduePhoneMapper;
    }

    @Override
    public int save(OverduePhone overduePhone) {
        overduePhone.setCreateTime(DateUtil.getNow());
        return overduePhoneMapper.save(overduePhone);
    }

    @Override
    public int update(OverduePhone overduePhone) {
        return overduePhoneMapper.update(overduePhone);
    }

    @Override
    public int deleteByIds(List<Long> list) {
        return overduePhoneMapper.deleteByIds(list);
    }

    @Override
    public Page<OverduePhone> list(int current, int pageSize, Map<String, Object> searchMap) {
        PageHelper.startPage(current,pageSize);
        return  (Page<OverduePhone>) overduePhoneMapper.listByCondition(searchMap);
    }
}
