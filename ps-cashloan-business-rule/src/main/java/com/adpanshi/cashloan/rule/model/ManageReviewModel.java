package com.adpanshi.cashloan.rule.model;

import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;

/**
 * @author 8452
 */
public class ManageReviewModel extends BorrowRuleResult{

	private static final long serialVersionUID = 1L;

	private Long engineRuleId;

	private String engineRuleName;

	public Long getEngineRuleId() {
		return engineRuleId;
	}

	public void setEngineRuleId(Long engineRuleId) {
		this.engineRuleId = engineRuleId;
	}

	public String getEngineRuleName() {
		return engineRuleName;
	}

	public void setEngineRuleName(String engineRuleName) {
		this.engineRuleName = engineRuleName;
	}
}
