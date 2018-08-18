package com.adpanshi.cashloan.cl.service;

import com.adpanshi.cashloan.cl.domain.Channel;
import com.adpanshi.cashloan.cl.model.ChannelCountModel;
import com.adpanshi.cashloan.cl.model.ChannelModel;
import com.adpanshi.cashloan.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 渠道信息Service
 *
 * @version 1.0.0
 * @date 2017-03-03 10:52:07
 * @Update_Date 2017/12/21
 * @Updator huangqin
 */
public interface ChannelService extends BaseService<Channel, Long> {

    /**
     * 保存渠道信息
     *
     * @param channel
     * @return boolean
     */
    boolean save(Channel channel);

    /**
     * 更新渠道信息
     *
     * @param channel
     * @return boolean
     */
    boolean update(Channel channel);

    /**
     * 更新渠道状态
     *
     * @param id
     * @param state
     * @return boolean
     */
    boolean updateState(Long id, String state);

    /**
     * 根据code查询对象
     *
     * @param code
     * @return
     */
    Channel findByCode(String code);

    /**
     * 列表查询渠道信息
     *
     * @param current
     * @param pageSize
     * @param searchMap
     * @return
     */
    Page<ChannelModel> page(int current, int pageSize,
                            Map<String, Object> searchMap);

    /**
     * 渠道用户统计
     *
     * @param current
     * @param pageSize
     * @param searchMap
     * @return
     */
    List<ChannelCountModel> channelUserList(int current, int pageSize,
                                            Map<String, Object> searchMap);

    /**
     * 查出所有渠道信息
     *
     * @return List<Channel>
     */
    List<Channel> listChannel();

    /**
     * 查询没有版本信息的渠道id和名称
     *
     * @return List<Channel>
     */
    List<Channel> listChannelWithoutApp();

    /**
     * 根据userID查找渠道Code
     *
     * @return String
     * @Param userId
     */
    String findCodeByUserId(long userId);

    /**
     * 导出渠道用户统计查询
     *
     * @param searchMap List<ChannelCountModel>
     */
    List<ChannelCountModel> channelUserList(Map<String, Object> searchMap);

    /**
     * 渠道用户统计详细
     *
     * @param current
     * @param pageSize
     * @param searchMap
     * @return Page<ChannelCountModel>
     */
    Page<ChannelCountModel> channelUserInfoList(int current, int pageSize, Map<String, Object> searchMap);
}
