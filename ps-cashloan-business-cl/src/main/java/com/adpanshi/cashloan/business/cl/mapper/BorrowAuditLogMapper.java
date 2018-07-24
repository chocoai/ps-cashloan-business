package com.adpanshi.cashloan.business.cl.mapper;

import com.adpanshi.cashloan.business.cl.domain.BorrowAuditLog;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * 人工复审操作记录
 * Created by cc on 2017/8/2.
 */
@RDBatisDao
public interface BorrowAuditLogMapper {

    //查找记录是否存在
    BorrowAuditLog findOne(long borrowId);

    //新增人工复审记录
    void addOne(Map<String, Object> paramMap);

    //添加人工复审记录
    void updateOne(Map<String, Object> paramMap);

    //修改人工复审记录
    void update(BorrowAuditLog borrowAuditLog);
}
