package com.adpanshi.cashloan.business.system.service.impl;

import com.adpanshi.cashloan.business.system.domain.SysDict;
import com.adpanshi.cashloan.business.system.mapper.SysDictMapper;
import com.adpanshi.cashloan.business.system.service.SysDictService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "sysDictService")
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public Page<SysDict> getDictPageList(int currentPage, int pageSize, Map<String, Object> searchMap) {
        PageHelper.startPage(currentPage, pageSize);
        return (Page<SysDict>) sysDictMapper.listSelective(searchMap);
    }

    @Override
    public boolean addOrModify(SysDict sysDict) {
        int num = 0;
        if (null != sysDict.getId() && sysDict.getId().longValue() > 0L) {
            num = sysDictMapper.update(sysDict);
        } else {
            num = sysDictMapper.save(sysDict);
        }
        return num > 0;
    }

    @Override
    public boolean deleteDict(Long id) {
        return sysDictMapper.deleteById(id) > 0;
    }

    /**
     * @param typeDict
     * @return RList<Map<String, Object>>
     * @auther huangqin
     * @description 获取系统参数类型数据字典
     * @data 2017-12-20
     */
    @Override
    public List<Map<String, Object>> getDictsCache(String typeDict) {
        return sysDictMapper.getDictsCache(typeDict);
    }

    @Override
    public SysDict findByTypeCode(String typeDict) {
        return sysDictMapper.findByTypeCode(typeDict);
    }

}