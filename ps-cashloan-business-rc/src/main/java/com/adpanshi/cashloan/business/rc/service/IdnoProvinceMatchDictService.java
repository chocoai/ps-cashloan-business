package com.adpanshi.cashloan.business.rc.service;

import com.adpanshi.cashloan.business.rc.domain.IdnoProvinceMatchDict;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 通讯录匹配字典service
 * Created by cc on 2017-09-05 11:35.
 */
public interface IdnoProvinceMatchDictService {


    /**
     * 保存字典
     * @param idnoProvinceMatchDict
     * @author nmnl
     * @date 2017-12-28 23:25
     */
    int save(IdnoProvinceMatchDict idnoProvinceMatchDict);

    /**
     * 更新字典省份数据
     * @param idnoProvinceMatchDict
     * @author nmnl
     * @date 2017-12-28 23:25
     */
    int updateProvince(IdnoProvinceMatchDict idnoProvinceMatchDict);

    /**
     * 更新字典省份数据
     * @param id
     * @author nmnl
     * @date 2017-12-28 23:25
     */
    int updateState(Long id);

    /**
     * 更新字典省份数据
     * @param id
     * @author nmnl
     * @date 2018-1-16 19:16
     */
    int delById(Long id);

    Page<IdnoProvinceMatchDict> list(int current, int pageSize, Map<String, Object> searchMap);

}
