package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.Zhima;

/**
 * 芝麻信用Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 11:35:27
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ZhimaMapper extends BaseMapper<Zhima,Long> {

    /**
     * 通过userId删除芝麻信用
     * @param userId
     * @return
     */
    int deleteZhimaByUserId(long userId);
    

}
