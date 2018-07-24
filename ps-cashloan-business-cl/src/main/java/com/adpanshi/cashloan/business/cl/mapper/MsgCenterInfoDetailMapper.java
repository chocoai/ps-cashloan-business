package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.MsgCenter;
import com.adpanshi.cashloan.business.cl.domain.MsgCenterInfoDetail;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 消息中心详情表Dao
 *
 */
@RDBatisDao
public interface MsgCenterInfoDetailMapper extends BaseMapper<MsgCenterInfoDetail, Long> {

    /**
     * 获取消息详情
     * @param params
     * @return
     */
    List<MsgCenterInfoDetail> findByInfoId(Map<String, Object> params);


    /**
     * 获取消息详情
     * @param params
     * @return
     */
    List<MsgCenter> getDetailByInfoId(Map<String, Object> params);
}
