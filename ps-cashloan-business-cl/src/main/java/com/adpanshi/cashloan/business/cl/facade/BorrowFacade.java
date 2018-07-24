package com.adpanshi.cashloan.business.cl.facade;



import com.adpanshi.cashloan.business.core.common.exception.ManageException;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.system.domain.SysUser;

import java.util.Map;

/**
 * @author yecy
 * @date 2017/12/12 10:52
 */
public interface BorrowFacade {


    BorrowMain rcBorrowApplyForAPI(BorrowMain borrow, String tradePwd, String mobileType) throws Exception;

    /**
     * 重新获取审核数据
     * @param borrowId
     */
    String reVerifyBorrowDataForRisk(Long borrowId);


    /**
     * 人工复审
     * @date : 2017-12-30 17:22
     * @author: nmnl
     * @throws Exception
     */
    String manualVerifyBorrow(Long borrowMainId, String state, String remark, Long auditId, String auditName, String
            orderView) throws ManageException;

    /**
     * 批量支付
     * @date : 20180101
     * @author : nmnl
     * @param borrowMainIds
     * @param sysUser
     * @return
     */
    Map<String,Object> batchPayAgain(Long[] borrowMainIds, SysUser sysUser);

    /**
     * 是否可以创建订单（在评估额度之前，如果返回否，则不进行额度评估）
     *
     * @param userId
     * @return
     * @author yecy 20180202
     */
    Boolean isCanCreateBorrow(Long userId);
}
