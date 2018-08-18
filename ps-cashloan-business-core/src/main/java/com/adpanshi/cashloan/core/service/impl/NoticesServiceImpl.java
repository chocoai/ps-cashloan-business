package com.adpanshi.cashloan.core.service.impl;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.domain.Notices;
import com.adpanshi.cashloan.core.mapper.NoticesMapper;
import com.adpanshi.cashloan.core.service.NoticesService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: fenqidai
 * @description: 消息服务实现
 * @author: Mr.Wange
 * @create: 2018-07-23 11:40
 **/
@Service("noticesService")
public class NoticesServiceImpl extends BaseServiceImpl<Notices,Long> implements NoticesService {

    @Resource
    private NoticesMapper noticesMapper;

    @Override
    public BaseMapper<Notices, Long> getMapper() {
        return noticesMapper;
    }

    /**
     * 获取消息列表
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Notices> queryNoticesList(Map<String, Object> params, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Notices> page = (Page<Notices>)noticesMapper.listSelective(params);
        return page;
    }

    /**
     * 获取一条消息
     * @param id
     * @return
     */
    @Override
    public Notices findByPrimary(Long id) {
        return noticesMapper.findByPrimary(id);
    }

    /**
     * @param params
     * @Description: 根据时间查询未读消息个数
     * @Param: [params]
     * @return: int
     * @Author: Mr.Wange
     * @Date: 2018/7/24
     */
    @Override
    public int queryNoticesReadState(Map<String, Object> params) {
        return noticesMapper.queryNoticesReadState(params);
    }
}
