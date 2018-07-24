package com.adpanshi.cashloan.business.rc.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rc.domain.TppReqLog;
import com.adpanshi.cashloan.business.rc.model.TppReqLogModel;

import java.util.List;
import java.util.Map;

/**
 * 第三方征信请求记录Dao
 * 

 * @version 1.0.0
 * @date 2017-03-20 13:50:34
 *
 *
 *
 *
 */
@RDBatisDao
public interface TppReqLogMapper extends BaseMapper<TppReqLog,Long> {

	/**
	 * 根据订单号修改记录
	 * @param log
	 * @return
	 */
	int modifyTppReqLog(TppReqLog log);

	/**
	 * 分页查询
	 * @param searchMap
	 * @return
	 */
	List<TppReqLogModel> page(Map<String, Object> searchMap);

}
