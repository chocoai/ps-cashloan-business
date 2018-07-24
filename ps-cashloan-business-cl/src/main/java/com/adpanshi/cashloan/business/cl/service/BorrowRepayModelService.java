package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.model.BorrowAndRepayModel;

import java.util.Date;
import java.util.List;

/**
 * @author yecy
 * @date 2017/12/20 15:24
 */
public interface BorrowRepayModelService {

    List<BorrowAndRepayModel> findExpireToRepay(String state);

    List<BorrowAndRepayModel> findExpireToRepay(String state, Date afterRepayTime);

    List<BorrowAndRepayModel> findExpireToRepay(String state, Date beforeRepayTime, Date afterRepayTime);
}
