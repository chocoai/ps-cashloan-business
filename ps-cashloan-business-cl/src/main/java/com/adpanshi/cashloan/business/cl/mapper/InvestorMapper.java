package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.yincheng.Investor;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 投资标中投资人信息表Dao
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-02 20:55:33

 *
 *
 */
@RDBatisDao
public interface InvestorMapper extends BaseMapper<Investor, Long> {

    int saveAll(List<Investor> investors);

    List<Investor> getExistedLogin(Map<String, Object> paramMap);

    /**
     * <p>根据订单号查找投资人信息</p>
     * @param orderNo
     * @return
     * */
    Investor getInvestorByOrderNo(@Param("orderNo") String orderNo);

}
