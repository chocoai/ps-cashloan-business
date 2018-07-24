package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.Channel;
import com.adpanshi.cashloan.business.cl.mapper.ChannelMapper;
import com.adpanshi.cashloan.business.cl.model.ChannelModel;
import com.adpanshi.cashloan.business.cl.service.ChannelService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("channelService")
public class ChannelServiceImpl extends BaseServiceImpl<Channel, Long> implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    @Override
    public BaseMapper<Channel, Long> getMapper() {
        return channelMapper;
    }

    @Override
    public boolean save(Channel channel) {
        channel.setCreateTime(new Date());
        channel.setState(ChannelModel.STATE_ENABLE);
        return channelMapper.save(channel) > 0;
    }
    @Override
    public boolean update(Channel channel) {
        return channelMapper.update(channel) > 0;
    }

    @Override
    public Channel findByCode(String code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", code);
        return channelMapper.findSelective(paramMap);
    }

    @Override
    public Page<ChannelModel> page(int current, int pageSize,
                                   Map<String, Object> searchMap) {
        PageHelper.startPage(current, pageSize);
        return (Page<ChannelModel>) channelMapper.page(searchMap);
    }

    @Override
    public List<Channel> listChannel() {
        return channelMapper.listChannel();
    }

}