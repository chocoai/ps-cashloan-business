
package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.EnjoysignRecord;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.cl.model.EnjoysignRecordModel;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 8470
 * @description 电子签对象
 * @create 2018-08-02 9:09
 **/

@RDBatisDao
public interface EnjoysignRecordMapper extends BaseMapper<EnjoysignRecord,Long> {
	
	/**
	 * <p>根据给定borId查找请求记录</p>
	 * @param borrowMainId 借款id
	 * @param EnjoysignRecord
	 * */
	EnjoysignRecord getEnjoysignRecordByBorId(Long borrowMainId);
	
	/**
	 * <p>查询待下载的合同</p>
	 * @param params 
	 * @return List<EnjoysignRecord>
	 * */
	List<EnjoysignRecord> queryWaitDownloadEnjoysignRecord(Map<String, Object> params);

	/**
	 * <p>更新</p>
	 * @param enjoysignRecord 待更新的对象(id 必填)
	 * @param return int 受影响的行数
	 * */
	int updateSelective(EnjoysignRecord enjoysignRecord);

	/**
	 * <p>根据borrowId  and statusList 获取签章数据信息</p>
	 * @param borrowMainId 借款订单id
	 * @param statusList 状态集
	 * @return EnjoysignRecord
	 * */
	EnjoysignRecord getEnjoysignRecordByBorrowIdWithStatus(@Param("statusList") List<Integer> statusList, @Param("borId") String borrowMainId);
	
	/**
	 * <p>查询待签章失败的合同</p>
	 * <p>合同中订单Id必须不为NULL且必须在订单中存在、状态未签署成功、签署时间已过去一天(防止在重签时、出现并发)的记录</p>
	 * @param statusList
	 * @return List<EnjoysignRecord>
	 * */
	List<EnjoysignRecord> queryEnjoysignRecordBySignatureFail(@Param("statusList") List<Integer> statusList);


	/**
	 * 根据用户id获取用户电子签文件
	 * @param userId
	 * @return EnjoysignRecordModel
	 */
	EnjoysignRecordModel findSignAttachment(Long userId);

	/**
	 * 获取用户当天认证次数
	 */
	Map findSignCount(@Param("createTime") String createTime, @Param("userId") Long userId);
}
