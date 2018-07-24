package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.OverduePhone;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 小贷信用卡催收电话号码库Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-24 19:58:22

 *
 *
 */
@RDBatisDao
public interface OverduePhoneMapper extends BaseMapper<OverduePhone, Long> {

    int deleteByIds(List<Long> list);

    List<OverduePhone> listByCondition(Map<String, Object> paramMap);

}
