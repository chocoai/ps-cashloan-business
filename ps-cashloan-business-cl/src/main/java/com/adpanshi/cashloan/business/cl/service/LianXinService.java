package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.lianxin.LianXinModel;
import com.github.pagehelper.Page;

import java.util.Map;

/**
 * 东方联信api对接服务
 * Created by cc on 2017-08-30 14:20.
 */
public interface LianXinService {


    /**
     * 列表查询
     * @param params
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<LianXinModel> listModel(Map<String, Object> params, int currentPage, int pageSize);

}
