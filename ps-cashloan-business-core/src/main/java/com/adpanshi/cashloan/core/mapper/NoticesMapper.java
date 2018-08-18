package com.adpanshi.cashloan.core.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.core.domain.Notices;

import java.util.Map;

/**
 * 消息表Dao
 * @author 8452
 */
@RDBatisDao
public interface NoticesMapper extends BaseMapper<Notices, Long> {
    /** 
    * @Description: 获取未读消息个数
    * @Param: [paramMap] 
    * @return: int
    * @Author: Mr.Wange
    * @Date: 2018/7/24 
    */ 
    int queryNoticesReadState(Map<String, Object> paramMap);
}
