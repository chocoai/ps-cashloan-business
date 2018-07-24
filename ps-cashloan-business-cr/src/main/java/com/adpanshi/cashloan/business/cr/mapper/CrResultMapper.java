package com.adpanshi.cashloan.business.cr.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.cr.domain.CrResult;
import com.adpanshi.cashloan.business.cr.model.CreditRatingModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 评分结果Dao
 * 

 * @version 1.0.0
 * @date 2017-01-05 16:22:54
 *
 * 
 *
 */
@RDBatisDao
public interface CrResultMapper extends BaseMapper<CrResult,Long> {

    List<CreditRatingModel> queryCreditRating(@Param("borrowTypeId") Long borrowTypeId, @Param("type") Integer type);

    CrResult findByConsumerNo(@Param("consumerNo") String consumerNo);

    /**
	 * 统计用户的总评分和总额度
	 * @param userId
	 * @return
	 */
	public Map<String,Object> findUserResult(Long userId);

	/**
	 * 查询用户各借款类型的总额度
	 * @param userId
	 * @return
	 */
	public List<CrResult> findAllBorrowTypeResult(Long userId);

	/**
	 * 查询指定类型的
	 * @param consumerNo
	 * @return
	 */
	public CrResult findCreditTypeResult(@Param("consumerNo") String consumerNo, @Param("creditTypeId") Long creditTypeId);

	/**
     * 自动审批查找需要比对的值
     * @return
     */
    String findValidValue(@Param("statement") String statement);
    
}
