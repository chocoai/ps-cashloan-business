package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.Channel;
import com.adpanshi.cashloan.business.cl.model.ChannelModel;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 渠道信息Service
 */
public interface ChannelService extends BaseService<Channel, Long> {

    /**
     * 保存渠道信息
     */
    boolean save(Channel channel);

    /**
     * 更新渠道信息
     */
    boolean update(Channel channel);

    /**
     * 根据code查询对象
     */
    Channel findByCode(String code);

    /**
     * 列表查询渠道信息
     */
    Page<ChannelModel> page(int current, int pageSize,
                            Map<String, Object> searchMap);

    /**
     * 查出所有渠道信息
     *
     * @return List<Channel>
     */
    List<Channel> listChannel();

}
