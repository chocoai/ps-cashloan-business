package com.adpanshi.cashloan.business.cl.service;

import com.adpanshi.cashloan.business.cl.domain.OverduePhone;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 小贷信用卡催收电话号码库Service
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-10-24 19:58:22

 *
 *
 */
public interface OverduePhoneService extends BaseService<OverduePhone, Long>{

    int save(OverduePhone overduePhone);

    int update(OverduePhone overduePhone);

    int deleteByIds(List<Long> list);

    /**
     * 分页查询
     * 支持customer_name & phone 的模糊查询（like），createUser完全匹配(=)，id & create_time 暂不匹配
     * @param current
     * @param pageSize
     * @param searchMap
     * @return
     */
    Page<OverduePhone> list(int current, int pageSize, Map<String, Object> searchMap);

}
