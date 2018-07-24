package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ProfitAgent;
import com.adpanshi.cashloan.business.cl.domain.UserInvite;
import com.adpanshi.cashloan.business.cl.mapper.ProfitAgentMapper;
import com.adpanshi.cashloan.business.cl.mapper.UserInviteMapper;
import com.adpanshi.cashloan.business.cl.model.InviteBorrowModel;
import com.adpanshi.cashloan.business.cl.model.ManageAgentModel;
import com.adpanshi.cashloan.business.cl.model.ManageProfitModel;
import com.adpanshi.cashloan.business.cl.service.UserInviteService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.exception.ServiceException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请记录ServiceImpl
 *
 * @author
 * @version 1.0.0
 * @date 2017-02-18 15:54:41
 */

@Service("userInviteService")
public class UserInviteServiceImpl extends BaseServiceImpl<UserInvite, Long> implements UserInviteService {

    @Resource
    private UserInviteMapper userInviteMapper;
    @Resource
    private ProfitAgentMapper profitAgentMapper;
    @Resource
    private UserMapper userMapper;


    @Override
    public BaseMapper<UserInvite, Long> getMapper() {
        return userInviteMapper;
    }

    @Override
    public Map<String, Object> findPhone(long userId) {
        boolean isPhone = true;
        String phone = Global.getValue("phone");
        Map<String, Object> phoneMap = new HashMap<String, Object>();
        phoneMap.put("phone", phone);
        Map<String, Object> inviteMap = new HashMap<String, Object>();
        inviteMap.put("inviteId", userId);
        UserInvite ui = userInviteMapper.findSelective(inviteMap);
        Map<String, Object> profitMap = new HashMap<String, Object>();
        ProfitAgent pa;
        if (StringUtil.isNotBlank(ui)) {
            profitMap.put("userId", ui.getUserId());
            pa = profitAgentMapper.findSelective(profitMap);
            if (StringUtil.isNotBlank(pa) && pa.getLevel() != 3) {
                phoneMap.put("phone", ui.getUserName());
            }
        }
        profitMap.put("userId", userId);
        pa = profitAgentMapper.findSelective(profitMap);
        if (StringUtil.isNotBlank(pa) && pa.getLevel() == 1) {
            isPhone = false;
        }
        phoneMap.put("isPhone", isPhone);
        return phoneMap;
    }

    @Override
    public Page<ManageProfitModel> findAgent(String phone, String userName, int current, int pageSize) throws ServiceException {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.findByLoginName(phone);
        map.put("id", user.getId());
        map.put("userId", user.getId());
        if (StringUtil.isNotBlank(userName)) {
            map.put("userName", "%" + userName + "%");
        }
        PageHelper.startPage(current, pageSize);
        return (Page<ManageProfitModel>) userInviteMapper.findAgent(map);
    }

    @Override
    public Page<ManageAgentModel> findSysAgent(String userName, int current,
                                               int pageSize) {
        PageHelper.startPage(current, pageSize);
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isNotBlank(userName)) {
            map.put("userName", "%" + userName + "%");
        }
        List<ManageAgentModel> list = userInviteMapper.findSysAgent(map);
        for (ManageAgentModel manageAgentModel : list) {
            if ("1".equals(manageAgentModel.getLevel())) {
                manageAgentModel.setCount(userInviteMapper.count(manageAgentModel.getUserId()) + "");
            } else {
                manageAgentModel.setCount("0");
            }
        }
        return (Page<ManageAgentModel>) list;
    }

    @Override
    public Page<InviteBorrowModel> listInviteBorrow(Long userId, int current, int pageSize) {
        PageHelper.startPage(current, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId.longValue());
        return (Page<InviteBorrowModel>) userInviteMapper.listInviteBorrow(map);
    }
}