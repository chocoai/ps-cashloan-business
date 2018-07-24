package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.rc.domain.IdnoProvinceMatchDict;
import com.adpanshi.cashloan.business.rc.mapper.IdnoProvinceMatchDictMapper;
import com.adpanshi.cashloan.business.rc.service.IdnoProvinceMatchDictService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cc on 2017-09-05 11:40.
 */
@Service("idnoProvinceMatchDictService")
public class IdnoProvinceMatchDictServiceImpl implements IdnoProvinceMatchDictService {

    @Resource
    private IdnoProvinceMatchDictMapper idnoProvinceMatchDictMapper;

    @Override
    public int save(IdnoProvinceMatchDict idnoProvinceMatchDict) {
        //provinceStartNo = provinceStartNo.replaceAll("[^0-9]", "");
        //@remarks: 默认启用 @author: nmnl @date 2017-12-28 23:21
        idnoProvinceMatchDict.setState(1);
        return idnoProvinceMatchDictMapper.save(idnoProvinceMatchDict);
    }

    @Override
    public int updateProvince(IdnoProvinceMatchDict idnoProvinceMatchDict) {
        //provinceStartNo = provinceStartNo.replaceAll("[^0-9]", "");
        return idnoProvinceMatchDictMapper.update(idnoProvinceMatchDict);
    }

    @Override
    public int updateState(Long id) {
        IdnoProvinceMatchDict idnoProvinceMatchDict = idnoProvinceMatchDictMapper.findByPrimary(id);
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("id",id);
        searchMap.put("state",idnoProvinceMatchDict.getState()==1?0:1);
        return idnoProvinceMatchDictMapper.updateSelective(searchMap);
    }

    @Override
    public int delById(Long id) {
        return idnoProvinceMatchDictMapper.del(id);
    }

    @Override
    public Page<IdnoProvinceMatchDict> list(int current, int pageSize, Map<String, Object> searchMap) {
        PageHelper.startPage(current,pageSize);
        Page<IdnoProvinceMatchDict> pages = (Page<IdnoProvinceMatchDict>) idnoProvinceMatchDictMapper.listSelective(searchMap);
        return pages;
    }
}
