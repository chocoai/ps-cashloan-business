package com.adpanshi.cashloan.cl.mapper;

import com.adpanshi.cashloan.cl.domain.BorrowAuditLog;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;

import java.util.Map;

/**
 * 人工复审操作记录
 * Created by cc on 2017/8/2.
 * @author 8452
 */
@RDBatisDao
public interface BorrowAuditLogMapper {

    /**
     * 查找记录是否存在
     * @param borrowId
     * @return
     */
    BorrowAuditLog findOne(long borrowId);

    /**
     * 新增人工复审记录
     * @param paramMap
     */
    void addOne(Map<String, Object> paramMap);

    /**
     * 添加人工复审记录
     * @param paramMap
     */
    void updateOne(Map<String, Object> paramMap);

    /**
     * 修改人工复审记录
     * @param borrowAuditLog
     */
    void update(BorrowAuditLog borrowAuditLog);
}
