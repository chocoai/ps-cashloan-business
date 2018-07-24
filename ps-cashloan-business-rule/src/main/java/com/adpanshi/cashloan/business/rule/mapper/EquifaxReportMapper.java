package com.adpanshi.cashloan.business.rule.mapper;

import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.business.rule.domain.equifaxReport.EquifaxReport;

import java.util.Map;

@RDBatisDao
public interface EquifaxReportMapper {
    int save(Map<String, Object> map);

    EquifaxReport getReport(Long userId);
}
