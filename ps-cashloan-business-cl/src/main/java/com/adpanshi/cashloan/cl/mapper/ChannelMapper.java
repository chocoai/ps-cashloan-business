package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.Channel;
import com.adpanshi.cashloan.cl.model.ChannelCountModel;
import com.adpanshi.cashloan.cl.model.ChannelModel;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;
import java.util.Map;

/**
 * 渠道信息Dao
 * 

 * @version 1.0.0
 * @date 2017-03-03 10:52:07
 *
 *
 * 
 *
 */
@RDBatisDao
public interface ChannelMapper extends BaseMapper<Channel,Long> {
	
	/**
	 * 根据条件查询主键
	 */
	long findIdSelective(Map<String, Object> paramMap);
	
	/**
	 * 根据条件查询对象
	 */
	Channel findSelective(Map<String, Object> paramMap);

	/**
	 * 列表查询
	 * @param paramMap
	 * @return
	 */
	List<ChannelModel> page(Map<String, Object> paramMap);

	/**
	 * 查出所有渠道信息
	 */
	List<Channel> listChannel();
	
	/**
	 * 查询没有版本信息的渠道id和名称
	 */
	List<Channel> listChannelWithoutApp();

	/**
	 * 查询渠道code通过用户id
	 * @param userId
	 * @return
	 */
	String findCodeByUserId(long userId);

	/**
	 * 渠道用户信息统计
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserNum(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息统计认证数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserAuthNum(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息统计申请人数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserBorrowNum(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息统计老用户人数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserOldNum(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息详细注册人数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserInfoNum(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息导出Excel统计注册人数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserInfoNumByChannelId(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息获得注册用户ID
	 * @param searchMap
	 * @return
	 */
	List<Long> channelUserIdByTime(Map<String, Object> searchMap);

	/**
	 * 渠道用户信息获得放款用户人数
	 * @param searchMap
	 * @return
	 */
	List<ChannelCountModel> channelUserLoanNum(Map<String, Object> searchMap);

}
