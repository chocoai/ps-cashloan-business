package com.adpanshi.cashloan.business.rule.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yecy
 * @date 2017/12/13 10:52
 */
public enum TCRiskManage {

    ACCEPT("accept", "建议通过"),
    REVIEW("review", "建议审核"),
    REJECT("reject", "建议拒绝");

    private String riskManage;
    private String riskManageChinese;

    public static String getRiskManege(String riskManage) {
        for (TCRiskManage tcRiskManage : TCRiskManage.values()) {
            if (StringUtils.equals(tcRiskManage.getRiskManage(), riskManage)) {
                return tcRiskManage.getRiskManageChinese();
            }
        }
        return REVIEW.getRiskManageChinese();
    }

    private TCRiskManage(String riskManage, String riskManageChinese) {
        this.riskManage = riskManage;
        this.riskManageChinese = riskManageChinese;
    }

    public String getRiskManage() {
        return riskManage;
    }

    public void setRiskManage(String riskManage) {
        this.riskManage = riskManage;
    }

    public String getRiskManageChinese() {
        return riskManageChinese;
    }

    public void setRiskManageChinese(String riskManageChinese) {
        this.riskManageChinese = riskManageChinese;
    }
}
