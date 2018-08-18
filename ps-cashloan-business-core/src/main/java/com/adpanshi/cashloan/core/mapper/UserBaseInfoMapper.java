package com.adpanshi.cashloan.core.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.model.UserWorkInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户详细信息Dao
 *
 * @version 1.0.0
 * @date 2017-02-21 13:44:30
 * @author 8452
 */
@RDBatisDao
public interface UserBaseInfoMapper extends BaseMapper<UserBaseInfo,Long> {

	/**
	 *  获取字典信息
	 * @method: getDictsCache
	 * @param type
	 * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:36
	 */
	List<Map<String, Object>> getDictsCache(String type);

	/**
	 *  getRegionalName
	 * @method: getRegionalName
	 * @param regionalCode
	 * @return: java.lang.String
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:36
	 */
	String getRegionalName(String regionalCode);

	/**
	 *  通过userId获取用户基本信息
	 * @method: getBaseModelByUserId
	 * @param userId
	 * @return: com.adpanshi.cashloan.core.model.ManagerUserModel
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:37
	 */
	ManagerUserModel getBaseModelByUserId(Long userId);

	/**
	 *  获取用户图片地址
	 * @method: findUserImags
	 * @param userId
	 * @return: com.adpanshi.cashloan.core.domain.UserBaseInfo
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 16:36
	 */
	UserBaseInfo findUserImags(@Param("userId") Long userId);

	/**
	 * 查询用户工作信息
	 * 
	 * @param userId
	 * @return
	 */
	UserWorkInfoModel findWorkInfo(@Param("userId") Long userId);

	/**
	 * 据用户id查询用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	UserBaseInfo findByUserId(@Param("userId") Long userId);
	
}
