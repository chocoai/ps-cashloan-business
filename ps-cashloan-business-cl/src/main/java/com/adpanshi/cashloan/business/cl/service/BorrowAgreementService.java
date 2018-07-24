package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.BorrowAgreement;
import com.adpanshi.cashloan.business.cl.domain.BorrowStagesAgreement;

/**
 * 借款协议
 * Created by cc on 2017/7/25.
 */

public interface BorrowAgreementService {


    //查询实际借款协议
    BorrowAgreement findByBowMainIdWithUserId(Long borrowMainId);

    /**
     * <p>根据订单主键id查询借款协议</p>
     * @param borrowMainId
     * @return BorrowStagesAgreement
     * */
    BorrowStagesAgreement findByBowMainId(Long borrowMainId);
}
