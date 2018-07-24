package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.MsgCenter;
import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfoDetail;

import java.util.List;

public interface MsgCenterService {

    /**
     * 获取用户消息中心数
     * @return
     */
    int getMsgNum(Long userId);

    /**
     * 获取用户消息中心列表
     * @return
     */
    List<MsgCenter> getMsgLst(Long userId);

    /**
     * 获取用户消息中心详情
     * @return
     */
    List<MsgCenterInfoDetail> getMsgInfo(Long infoId);
}
