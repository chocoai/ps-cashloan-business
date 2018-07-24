package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.ZhimaBatch;
import com.adpanshi.cashloan.business.cl.model.ZhimaBatchModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 批量生产芝麻授权数据Dao
 * 
 * @author nmnl
 * @version 1.0.0
 * @date 2017-08-15 17:54:27
 *
 */
@RDBatisDao
public interface ZhimaBatchMapper extends BaseMapper<ZhimaBatch, Long> {

    /**
     * 按照日期查询用户芝麻授权后的订单数据
     * @param map
     *          startTime && endTime
     * @return
     */
    List<ZhimaBatchModel> listZhimaBatchData(Map<String, Object> map);
    
    /**
     * 按照日期查询用户芝麻授权后,无订单数据
     * @param map
     *          startTime && endTime
     * @return
     */
    List<ZhimaBatchModel> listZhimaBatchDataNoOrder(Map<String, Object> map);

    /**
     * 历史逾期订单
     * @return
     */
    List<ZhimaBatchModel> listZhimaRepayBatchData();

    /**
     * 按照日期或者状态查询芝麻授权是否处理的文件
     * @param map
     *          startTime && endTime || state
     * @return
     */
    List<ZhimaBatch> listSelective(Map<String, Object> map);
}
