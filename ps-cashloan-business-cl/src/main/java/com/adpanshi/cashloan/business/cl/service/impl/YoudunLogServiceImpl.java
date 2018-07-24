package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.YoudunLog;
import com.adpanshi.cashloan.business.cl.mapper.YoudunLogMapper;
import com.adpanshi.cashloan.business.cl.service.YoudunLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 有盾请求记录表ServiceImpl

 */
 
@Service("youdunLogService")
public class YoudunLogServiceImpl extends BaseServiceImpl<YoudunLog, Long> implements YoudunLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(YoudunLogServiceImpl.class);
   
    @Resource
    private YoudunLogMapper youdunLogMapper;

	@Override
	public BaseMapper<YoudunLog, Long> getMapper() { return youdunLogMapper; }

    @Override
    public int save(YoudunLog youdunLog) {
        return youdunLogMapper.save(youdunLog);
    }

    @Override
    public int updateSelective(Map<String, Object> paramMap) {
        return youdunLogMapper.updateSelective(paramMap);
    }

    /**
     * 获取一条记录
     * @date: 20170728
     * @author: nmnl
     * @param paramMap
     * @return 查询结果
     */
    @Override
    public YoudunLog findSelectiveOne(Map<String, Object> paramMap) {
        return youdunLogMapper.findSelectiveOne(paramMap);
    }
}