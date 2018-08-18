package com.adpanshi.cashloan.rc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.adpanshi.cashloan.rc.model.ManageTppBusinessModel;
import com.adpanshi.cashloan.rc.model.TppBusinessModel;
import org.springframework.stereotype.Service;

import tool.util.DateUtil;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.rc.domain.TppBusiness;
import com.adpanshi.cashloan.rc.mapper.TppBusinessMapper;
import com.adpanshi.cashloan.rc.service.TppBusinessService;


/**
 * 第三方征信接口信息ServiceImpl
 *
 * @version 1.1.0
 * @date 2017-03-14 13:41:57
 * @updateDate 2017/12/22
 * @updator huangqin
 */

@Service("tppBusinessService")
public class TppBusinessServiceImpl extends BaseServiceImpl<TppBusiness, Long> implements TppBusinessService {

    @Resource
    private TppBusinessMapper tppBusinessMapper;

    @Override
    public BaseMapper<TppBusiness, Long> getMapper() {
        return tppBusinessMapper;
    }

    @Override
    public List<TppBusinessModel> listAll() {
        return tppBusinessMapper.listAll();
    }

    @Override
    public Page<ManageTppBusinessModel> page(Map<String, Object> paramMap, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        return (Page<ManageTppBusinessModel>) tppBusinessMapper.list(paramMap);
    }

    @Override
    public List<TppBusiness> listSelective(Map<String, Object> paramMap) {
        return tppBusinessMapper.listSelective(paramMap);
    }

    @Override
    public boolean save(TppBusiness tppBusiness) {
        tppBusiness.setState("10");
        tppBusiness.setAddTime(DateUtil.getNow());
        return tppBusinessMapper.save(tppBusiness) > 0;
    }

    @Override
    public boolean update(TppBusiness tppBusiness) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", tppBusiness.getId());
        paramMap.put("nid", tppBusiness.getNid());
        paramMap.put("name", tppBusiness.getName());
        paramMap.put("url", tppBusiness.getUrl());
        paramMap.put("testUrl", tppBusiness.getTestUrl());
        paramMap.put("extend", tppBusiness.getExtend());
        return tppBusinessMapper.updateSelective(paramMap) > 0;
    }

    public boolean updateState(Long id, String state) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("state", state);
        return tppBusinessMapper.updateSelective(paramMap) > 0;
    }

    @Override
    public boolean tppBusinessExist(TppBusiness business) {
        return null != tppBusinessMapper.findByNid(business.getNid(), business.getTppId());
    }

    @Override
    public TppBusiness findByNid(String nid, Long tppId) {
        return tppBusinessMapper.findByNid(nid, tppId);
    }

}