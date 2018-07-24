package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.lianxin.LianXinModel;
import com.adpanshi.cashloan.business.cl.mapper.LianXinReqLogMapper;
import com.adpanshi.cashloan.business.cl.mapper.LianXinRespLogMapper;
import com.adpanshi.cashloan.business.cl.service.LianXinService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by cc on 2017-08-30 14:21.
 */
@Service("lianXinService")
public class LianXinServiceImpl implements LianXinService{

    private static final Logger logger = LoggerFactory.getLogger(LianXinServiceImpl.class);

    @Resource
    private LianXinReqLogMapper lianXinReqLogMapper;

    @Resource
    private LianXinRespLogMapper lianXinRespLogMapper;

    @Override
    public Page<LianXinModel> listModel(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<LianXinModel>)lianXinRespLogMapper.listModel(params);
    }


}
