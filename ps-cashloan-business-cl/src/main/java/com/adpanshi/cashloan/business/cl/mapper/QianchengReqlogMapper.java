package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.QianchengReqlog;
import com.adpanshi.cashloan.business.cl.model.QianchengReqlogModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 浅橙借款申请审核Dao
 * 

 * @version 1.0.0
 * @date 2017-03-15 11:46:54
 *
 *
 * 
 *
 */
@RDBatisDao
public interface QianchengReqlogMapper extends BaseMapper<QianchengReqlog,Long> {

    /**
     * 根据订单号查找日志
     * @param orderNo
     * @return
     */
	QianchengReqlog findByOrderNo(@Param("orderNo") String orderNo);


	/**
	 * 机审请求记录查询
	 * @param params
	 * return
	 */
	List<QianchengReqlogModel> listQianchengReqlog(Map<String, Object> params);

	/**
	 * 根据借款申请查找
	 * @param borrowId
	 * @return
	 */
	QianchengReqlog findByBorrowId(@Param("borrowId") String borrowId);
	
}
