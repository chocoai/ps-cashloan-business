package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.ContactMatchDict;

import java.util.List;
import java.util.Map;

/**
 * 通讯录匹配字典Dao
 * by cc 2017-09-05
 */
@RDBatisDao
public interface ContactMatchDictMapper {


    int save(ContactMatchDict contactMatchDict);

    int update(ContactMatchDict contactMatchDict);

    int del(Long id);

    List<ContactMatchDict> listSelective(Map<String, Object> searchMap);
}
