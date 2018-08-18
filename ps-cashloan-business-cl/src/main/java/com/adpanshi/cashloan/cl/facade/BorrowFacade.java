package com.adpanshi.cashloan.cl.facade;

import com.adpanshi.cashloan.core.domain.BorrowMain;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yecy
 * @date 2017/12/12 10:52
 */
public interface BorrowFacade {

    /**
     * rcBorrowApplyForAPI
     * @param borrow
     * @param tradePwd
     * @param mobileType
     * @return
     * @throws Exception
     */
    BorrowMain rcBorrowApplyForAPI(BorrowMain borrow, String tradePwd, String mobileType,
                                   HttpServletRequest request) throws Exception;

}
