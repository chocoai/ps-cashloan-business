package com.adpanshi.cashloan.rule.mapper;

import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.rule.domain.equifaxReport.EquifaxReport;

import java.util.Map;

@RDBatisDao
public interface EquifaxReportMapper {
    int save(Map<String, Object> map);

    EquifaxReport getReport(Long userId);
}
