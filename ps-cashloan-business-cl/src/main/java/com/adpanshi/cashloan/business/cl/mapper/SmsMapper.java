package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.Sms;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 短信记录Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 *
 *
 * 
 *
 */
@RDBatisDao
public interface SmsMapper extends BaseMapper<Sms,Long> {

	/**
	 * 查询最新一条短信记录
	 * @param data
	 * @return
	 */
	Sms findTimeMsg(Map<String, Object> data);

    /**
     * 查询某号码某种类型当天已发送次数
     * @param data
     * @return
     */
    int countDayTime(Map<String, Object> data);

	/**
	 * 修改短信响应状态
	 * @param map
	 * @return
	 */
    int updateByMsgid(Map<String, Object> map);

    List<Sms> findResendList(Map<String, Object> map);

    int updateByIds(Map<String, Object> smsInfo);
}
