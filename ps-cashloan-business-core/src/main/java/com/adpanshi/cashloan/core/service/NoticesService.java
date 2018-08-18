package com.adpanshi.cashloan.core.service;

import com.adpanshi.cashloan.core.common.service.BaseService;
import com.adpanshi.cashloan.core.domain.Notices;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * @program:
 * @Description:
 * @Author: Mr.Wange
 * @Date: 2018/7/24
*/ 
public interface NoticesService extends BaseService<Notices, Long> {
    /**
     * 获取消息列表
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<Notices> queryNoticesList(Map<String, Object> params, int currentPage, int pageSize);

    /**
     * 根据id获取消息
     * @param id
     * @return
     */
    Notices findByPrimary(Long id);
    
    /** 
    * @Description: 根据时间查询未读消息个数
    * @Param: [params] 
    * @return: int 
    * @Author: Mr.Wange
    * @Date: 2018/7/24 
    */ 
    int queryNoticesReadState(Map<String, Object> params);
}
