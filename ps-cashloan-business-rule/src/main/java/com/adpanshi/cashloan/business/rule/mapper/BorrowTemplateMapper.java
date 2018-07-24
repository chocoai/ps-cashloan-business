package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.business.rule.model.ScaleShowModel;

import java.util.List;
import java.util.Map;

/**
 * 借款模板信息表Dao
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-30 15:14:56
 */
@RDBatisDao
public interface BorrowTemplateMapper extends BaseMapper<BorrowTemplate, Long> {

    List<ScaleShowModel> getScaleList();

    /**
     * 数据查询,根据timelimit字段升序
     *
     * @param paramMap
     * @return
     */
    List<BorrowTemplate> findSelectiveOrderByTimeLimit(Map<String, Object> paramMap);

}
