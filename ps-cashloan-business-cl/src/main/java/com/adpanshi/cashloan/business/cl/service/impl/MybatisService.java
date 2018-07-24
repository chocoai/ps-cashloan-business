package com.adpanshi.cashloan.business.cl.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lsk on 2017/2/24.
 */
@Service
@SuppressWarnings({ "rawtypes" })
public class MybatisService {
    @Resource
    private SqlSessionTemplate sessionTemplate;

    public List<Map> querySql(String statment, Object parameter) {
        return sessionTemplate.selectList(statment, parameter);
    }

    public Map queryRec(String statment, Object parameter) {
        return sessionTemplate.selectOne(statment, parameter);
    }
}
