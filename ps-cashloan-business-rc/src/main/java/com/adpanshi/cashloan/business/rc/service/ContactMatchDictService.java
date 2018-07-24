package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.rc.domain.ContactMatchDict;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 通讯录匹配字典service
 * Created by cc on 2017-09-05 11:35.
 */
public interface ContactMatchDictService {


    /**
     * 保存字典
     * @param contactMatchDict
     */
    int save(ContactMatchDict contactMatchDict);

    /**
     * 更新字典
     * @param contactMatchDict
     */
    int update(ContactMatchDict contactMatchDict);

    /**
     * 删除字典
     * @param id
     * @return
     */
    int delById(Long id);


    Page<ContactMatchDict> list(int current, int pageSize, Map<String, Object> searchMap);

}
