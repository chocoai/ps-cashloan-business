package com.adpanshi.cashloan.rule.service;

import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;

public interface EquifaxReportService {
    void getEquifaxReport(Long UserId, String panNo, String firstName, String lastName);

    Envelope getEquifaxReportDetail(Long userId);
}
