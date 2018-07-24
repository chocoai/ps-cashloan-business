package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rc.domain.Tpp;
import com.adpanshi.cashloan.business.rc.mapper.TppMapper;
import com.adpanshi.cashloan.business.rc.model.ManageTppModel;
import com.adpanshi.cashloan.business.rc.model.TppModel;
import com.adpanshi.cashloan.business.rc.service.TppService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 第三方征信信息ServiceImpl
 *
 * @version 1.1.0
 * @date 2017-03-14 13:41:05
 * @updateDate 2017/12/22
 * @updator huangqin
 */

@Service("tppService")
public class TppServiceImpl extends BaseServiceImpl<Tpp, Long> implements TppService {

    @Resource
    private TppMapper tppMapper;

    @Override
    public BaseMapper<Tpp, Long> getMapper() {
        return tppMapper;
    }

    @Override
    public Page<ManageTppModel> page(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<ManageTppModel>) tppMapper.list(params);
    }

    @Override
    public List<TppModel> listAll() {
        return tppMapper.listAll();
    }

    @Override
    public boolean save(Tpp tpp) {
        tpp.setState("10");
        tpp.setAddTime(DateUtil.getNow());
        return tppMapper.save(tpp) > 0;
    }

    @Override
    public boolean update(Tpp tpp) {
        return tppMapper.update(tpp) > 0;
    }

    @Override
    public boolean updateState(Long id, String state) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id.longValue());
        paramMap.put("state", state);
        return tppMapper.updateSelective(paramMap) > 0;
    }

}