
package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.SaasRespRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author qing.yunhui
 * @Since 2011-2018
 * @create 2018-04-03 12:04:06
 * @history
 */
@RDBatisDao
public interface SaasRespRecordMapper extends BaseMapper<SaasRespRecord, Long> {

    /**
     * 获取最新一条成功响应记录的taskId
     *
     * @param type   账户类型
     * @param userId 用户id
     * @return
     */
    String findLastSuccessTaskId(@Param("type") Integer type, @Param("userId") Long userId);

}
