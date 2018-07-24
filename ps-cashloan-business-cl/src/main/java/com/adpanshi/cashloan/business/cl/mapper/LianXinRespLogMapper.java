package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.lianxin.LianXinModel;
import com.adpanshi.cashloan.business.cl.domain.lianxin.LianXinRespLog;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 联信异步回调日志
 * Created by cc on 2017-08-31 20:33.
 */
@RDBatisDao
public interface LianXinRespLogMapper {

    /**
     * 保存联信请求日志
     */
    void save(LianXinRespLog lianXinRespLog);

    /**
     * 查询通知成功用户
     * @return
     */
    int findSuccesUser(Map<String, Object> paramMap);

    /**
     * 列表查询语音通知结果
     * @param params
     * @return
     */
    List<LianXinModel> listModel(Map<String, Object> params);
}
