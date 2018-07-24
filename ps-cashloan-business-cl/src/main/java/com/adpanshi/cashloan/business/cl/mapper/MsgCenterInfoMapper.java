package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfo;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 消息中心表Dao
 *
 */
@RDBatisDao
public interface MsgCenterInfoMapper extends BaseMapper<MsgCenterInfo, Long> {

    /**
     * 获取消息列
     * @param params
     * @return
     */
    List<MsgCenterInfo> getMsgCenterInfoLst(Map<String, Object> params);

    /**
     * 获取消息数
     * @param params
     * @return
     */
    int getMsgCount(Map<String, Object> params);
}
