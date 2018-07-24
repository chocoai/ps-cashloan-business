package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.UserInvite;
import com.adpanshi.cashloan.business.cl.model.InviteBorrowModel;
import com.adpanshi.cashloan.business.cl.model.ManageAgentModel;
import com.adpanshi.cashloan.business.cl.model.ManageProfitModel;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 邀请记录Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 15:54:41
 *
 *
 * 
 *
 */
@RDBatisDao
public interface UserInviteMapper extends BaseMapper<UserInvite,Long> {

	/**
	 * 代理商查询
	 * @param map
	 * @return
	 */
	List<ManageProfitModel> findAgent(Map<String, Object> map);

	/**
	 * 管理员代理商查询
	 * @param map
	 * @return
	 */
	List<ManageAgentModel> findSysAgent(Map<String, Object> map);

	/**
	 * 统计邀请的二级代理数量
	 * @param userId
	 * @return
	 */
	long count(Long userId);

	/**
	 * 查询邀请明细
	 * @param map
	 * @return
	 */
	List<InviteBorrowModel> listInviteBorrow(Map<String, Object> map);
	
	Map<String,Object> inviteListCnt(Long userId);
	
	Map<String,Object> inviteList(@Param("userId") Long userId, @Param("start") int start, @Param("length") int length);

}
