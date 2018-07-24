package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OperatorBasic;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;


/**
 * 运营商信息-基础信息Dao
 * 

 * @version 1.0.0
 * @date 2017-03-13 16:35:43
 *
 *
 * 
 *
 */
@RDBatisDao
public interface OperatorBasicMapper extends BaseMapper<OperatorBasic,Long> {

    /**
     * 删除
     * @param list
     * @return
     */
    int deleteByUserId(List<Long> list);

}
