package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.BorrowAgreement;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

/**
 * 借款协议
 * Created by cc on 2017/7/28.
 */
@RDBatisDao
public interface BorrowAgreementMapper {


    /**
     * 根据给定borrowMainId、userId查询借款协议
     * @param borrowMainId
     * @return
     */
    BorrowAgreement findByBowMainIdWithUserId(@Param("borrowMainId") Long borrowMainId);

}
