package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.TongdunReqLog;
import com.adpanshi.cashloan.business.rule.model.TongdunReqLogModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 同盾第三方请求记录Dao
 * 

 * @version 1.0.0
 * @date 2017-04-26 15:26:56
 *
 *
 *
 *
 */
@RDBatisDao
public interface TongdunReqLogMapper {

	/**
	 * <p>新增</p>
	 * @param tableName 待操作的表
	 * @param tongdunReqLog 待新增的对象
	 * @return  int 受影响的行数
	 * */
	int save(@Param("tableName") String tableName, @Param("item") TongdunReqLog tongdunReqLog);

	/**
	 * <p>更新</p>
	 * @param tableName 待操作的表
	 * @param tongdunReqLog 待新增的对象
	 * @return  int 受影响的行数
	 * */
	int update(@Param("tableName") String tableName, @Param("item") TongdunReqLog tongdunReqLog);

	int updateSelective(Map<String, Object> params);

	TongdunReqLogModel findByPrimary(@Param("tableName") String tableName, @Param("id") Long id);

	TongdunReqLog findSelective(Map<String, Object> params);

	List<TongdunReqLogModel> listSelective(Map<String, Object> params);

	/*List<TongdunReqLogModel> listModelByMap(Map<String, Object> params);*/

	TongdunReqLogModel findModelById(@Param("tableName") String tableName, @Param("tableNameDetail") String tableNameDetail, @Param("borrowId") Long borrowId);

	/*List<String> showTable (@Param("tableName")String tableName);*/

    

}
