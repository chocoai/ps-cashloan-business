package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.cl.model.ManageBorrowProgressModel;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 借款进度表Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:31:04
 *
 */
@RDBatisDao
public interface BorrowProgressMapper extends BaseMapper<BorrowProgress,Long> {

	/**
	 * 首页查询进度
	 * @param bpMap
	 * @return
	 */
	List<BorrowProgressModel> listIndex(Map<String, Object> bpMap);
	
	/**
	 * 后台借款进度列表
	 * @param params
	 * @return
	 */
	List<ManageBorrowProgressModel> listModel(Map<String, Object> params);

	/**
	 * 借款进度查询
	 * @param bpMap
	 * @return
	 */
	List<BorrowProgressModel> listProgress(Map<String, Object> bpMap);

	/**
	 * 查找借款进度中存在过借款类型为'$state'的流程
	 *
	 * @param stateList
	 * @return
	 * @author yecy 20171224
	 */
	List<BorrowProgress> findProcessByState(@Param("borrowId") Long borrowId, @Param("stateList") Collection<String> stateList);

	/**
	 * 批量保存
	 *
	 * @param processList
	 * @return
	 * @author yecy 20171224
	 */
	int saveAll(@Param("list") List<BorrowProgress> processList);

	/**
	 * <p>根据给定主订单id、userId、状态、查询子订单最近一条还款进度记录</p>
	 * @param borMainId
	 * @param userId
	 * @param state
	 * @return BorrowProgress
	 * */
	BorrowProgress findLateByBorMainIdWithUserId(@Param("borMainId") Long borMainId, @Param("userId") Long userId, @Param("state") String state);
	
}
