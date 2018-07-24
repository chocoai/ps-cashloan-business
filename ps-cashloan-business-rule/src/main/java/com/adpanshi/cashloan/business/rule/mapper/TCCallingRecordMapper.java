package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.OperatorVoices;
import com.adpanshi.cashloan.business.rule.model.OperatorTdCallModel;
import com.adpanshi.cashloan.business.rule.model.tianchuang.CallingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface TCCallingRecordMapper extends BaseMapper<CallingRecord,Long> {

	public void batchInsert(@Param("callingRecords") List<CallingRecord> callingRecords);

	public List<OperatorVoices> selectByOrderId(@Param("contractTableName") String contractTableName,
                                                @Param("params") Map<String, Object> params);

	public List<OperatorTdCallModel> selectByOrderIdMatchOverphone(@Param("contractTableName") String contractTableName,
                                                                   @Param("params") Map<String, Object> params);


	String sunCallTime(@Param("params") Map<String, Object> params);

	/**
	 * 删除通过请求id
	 * @param list
	 * @return
	 */
	int deleteByOrderId(List<String> list);
	
	/**
	 * 查询通话记录
	 */
	public List<CallingRecord>  selectByOrderIdRecord(Map<String, Object> params);
}
