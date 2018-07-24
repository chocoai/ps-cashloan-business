package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.business.rule.enums.TCRiskManage;
import com.adpanshi.cashloan.business.rule.mapper.BorrowRuleResultMapper;
import com.adpanshi.cashloan.business.rule.model.srule.model.SimpleRule;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 借款规则详细配置表ServiceImpl
 *
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 */

@Service("borrowRuleResultService")
public class BorrowRuleResultServiceImpl extends BaseServiceImpl<BorrowRuleResult, Long> implements BorrowRuleResultService {

    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;

    @Override
    public BaseMapper<BorrowRuleResult, Long> getMapper() {
        return borrowRuleResultMapper;
    }

    @Override
    public int saveResultFilled(long borrowId,long ruleId,String reviewResult,String tbNid,String tbName,String colNid,String colName,String rule) {
        BorrowRuleResult result = new BorrowRuleResult();
        result.setBorrowId(borrowId);
        result.setRuleId(ruleId);
        result.setTbNid(tbNid);
        result.setTbName(tbName);
        result.setColNid(colNid);
        result.setColName(colName);
        result.setMatching(changeBorrowResultTypeToTCRiskManage(reviewResult).getRiskManageChinese());
        result.setValue(changeBorrowResultTypeToTCRiskManage(reviewResult).getRiskManageChinese());
        result.setRule(rule);
        result.setResult(SimpleRule.COMPAR_PASS);
        result.setResultType(reviewResult);
        result.setAddTime(new Date());
        return borrowRuleResultMapper.save(result);
    }

    /**
     * 将BorrowResultType结果转成TCRiskManage结果
     * */
    private TCRiskManage changeBorrowResultTypeToTCRiskManage(String reviewResult){
        if (BorrowRuleResult.RESULT_TYPE_PASS.equals(reviewResult)) {
            return TCRiskManage.ACCEPT;
        }
        if (BorrowRuleResult.RESULT_TYPE_REVIEW.equals(reviewResult)) {
            return TCRiskManage.REVIEW;
        }
        return TCRiskManage.REJECT;
    }

}