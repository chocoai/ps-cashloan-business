package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.IdnoProvinceMatchDict;

/**
 * @author huang qin
 * @Since 2011-2017
 * @create 2017-11-16 10:27:48
 * @history
 */
@RDBatisDao
public interface IdnoProvinceMatchDictMapper extends BaseMapper<IdnoProvinceMatchDict,Long> {

    /**
     * 删除记录
     * */
    int del(Long id);

}
