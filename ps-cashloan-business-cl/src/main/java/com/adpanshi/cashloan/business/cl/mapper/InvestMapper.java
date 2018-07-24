package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Invest;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;

/**
 * 银程投资标存储表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:51:50

 *
 *
 */
@RDBatisDao
public interface InvestMapper extends BaseMapper<Invest, String> {

   int saveAll(List<Invest> invests);


    

}
