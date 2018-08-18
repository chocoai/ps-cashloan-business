package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.BorrowRepayLog;
import com.adpanshi.cashloan.cl.model.BorrowRepayLogModel;
import com.adpanshi.cashloan.cl.model.ManageBRepayLogModel;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 还款计录Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:46:12
 *
 *
 * 
 *
 */
@RDBatisDao
public interface BorrowRepayLogMapper extends BaseMapper<BorrowRepayLog,Long> {

	List<ManageBRepayLogModel> listModel(Map<String, Object> params);

	/**
	 * 查询所有
	 * @param searchMap
	 * @return
	 */
	List<BorrowRepayLogModel> listSelModel(Map<String, Object> searchMap);

    /**
     * 退还补扣修改还款记录
     * @param paramMap
     * @return
     */
	int refundDeduction(Map<String, Object> paramMap);

	/**
	 * 根据还款时间，获取走直融借款的(根据borrow_process表中是否有29状态判断借款是否走过直融)，已经还款的记录id
	 * @author yecy 20171129
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Long> getRepayIdsByRepayTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	* @Description: 获取还款信息
	* @param: id
	* @return: java.util.Map
	* @Author: Mr.Wange
	* @Date: 2018/7/25
	*/
	Map qryRepayLog(Long id);
}
