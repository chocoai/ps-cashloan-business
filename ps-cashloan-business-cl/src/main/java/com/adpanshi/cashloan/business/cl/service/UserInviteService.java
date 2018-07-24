package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.UserInvite;
import com.adpanshi.cashloan.business.cl.model.InviteBorrowModel;
import com.adpanshi.cashloan.business.cl.model.ManageAgentModel;
import com.adpanshi.cashloan.business.cl.model.ManageProfitModel;
import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 邀请记录Service
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-18 15:54:41
 */
public interface UserInviteService extends BaseService<UserInvite, Long> {

    /**
     * 查询联系人电话
     *
     * @param userId
     * @return
     */
    Map<String, Object> findPhone(long userId);

    /**
     * 查询邀请记录
     *
     * @param phone
     * @param userName
     * @return
     * @throws ServiceException
     */
    Page<ManageProfitModel> findAgent(String phone, String userName, int current, int pageSize) throws ServiceException;

    /**
     * 系统查询代理商
     *
     * @param userName
     * @param current
     * @param pageSize
     * @return
     */
    Page<ManageAgentModel> findSysAgent(String userName, int current,
                                        int pageSize);

    /**
     * 查询用户邀请记录
     *
     * @param userId
     * @param current
     * @param pageSize
     * @return
     */
    Page<InviteBorrowModel> listInviteBorrow(Long userId, int current, int pageSize);

}
