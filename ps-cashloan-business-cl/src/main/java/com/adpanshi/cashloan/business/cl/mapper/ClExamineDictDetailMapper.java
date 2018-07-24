package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.ClExamineDictDetail;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface ClExamineDictDetailMapper  extends BaseMapper<ClExamineDictDetail,Long>{
	/**
	 * 查询字段的所有信息
	 * @param params
	 * @return
	 */
	List<ClExamineDictDetail>  findClExamineDictDetailInfo(Map<String, Object> params);

	/**
	 * 更新
	 * @param exma
	 */
	int updateClExamineState(ClExamineDictDetail exma);

	/**
	 * 查询最大的code值
	 */
	String selectMaxCode();

	/**
	 * <p>根据给定条件统计记录条数</p>
	 * @param params
	 * @return 符合条件的总记录条数
	 * */
	int queryCount(Map<String, Object> params);

	ClExamineDictDetail findById(@Param("id") Long id);
}
