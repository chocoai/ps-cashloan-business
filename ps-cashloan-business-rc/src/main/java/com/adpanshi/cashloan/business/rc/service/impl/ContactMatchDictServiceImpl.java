package com.adpanshi.cashloan.business.rc.service.impl;

import com.adpanshi.cashloan.business.rc.domain.ContactMatchDict;
import com.adpanshi.cashloan.business.rc.mapper.ContactMatchDictMapper;
import com.adpanshi.cashloan.business.rc.service.ContactMatchDictService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by cc on 2017-09-05 11:40.
 */
@Service("contactMatchDictService")
public class ContactMatchDictServiceImpl implements ContactMatchDictService{

    @Resource
    private ContactMatchDictMapper contactMatchDictMapper;

    @Override
    public int save(ContactMatchDict contactMatchDict) {
        return contactMatchDictMapper.save(contactMatchDict);

    }

    @Override
    public int update(ContactMatchDict contactMatchDict) {
        return contactMatchDictMapper.update(contactMatchDict);
    }

    @Override
    public int delById(Long id) {
        return contactMatchDictMapper.del(id);
    }

    @Override
    public Page<ContactMatchDict> list(int current, int pageSize, Map<String, Object> searchMap) {
        PageHelper.startPage(current,pageSize);
        Page<ContactMatchDict> pages = (Page<ContactMatchDict>) contactMatchDictMapper.listSelective(searchMap);
        return pages;
    }
}
