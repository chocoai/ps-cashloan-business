package com.adpanshi.cashloan.rule.service.impl;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import com.adpanshi.cashloan.rule.mapper.BorrowRuleResultMapper;
import com.adpanshi.cashloan.rule.service.BorrowRuleResultService;
import com.adpanshi.cashloan.rule.util.BorrowRiskRuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款规则详细配置表ServiceImpl
 *
 * @version 1.0.0
 * @date 2017-04-21 15:23:19
 * @author
 */

@Service("borrowRuleResultService")
public class BorrowRuleResultServiceImpl extends BaseServiceImpl<BorrowRuleResult, Long> implements BorrowRuleResultService {

    final Logger logger = LoggerFactory.getLogger(BorrowRuleResultServiceImpl.class);

    @Resource
    private BorrowRuleResultMapper borrowRuleResultMapper;
    @Resource
    private BorrowMainMapper borrowMainMapper;
    @Resource
    private BorrowRuleResultService borrowRuleResultService;
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;

    @Override
    public BaseMapper<BorrowRuleResult, Long> getMapper() {
        return borrowRuleResultMapper;
    }

    @Override
    public BorrowRuleResult save(long borrowId, String resultType, String resultMsg, BigDecimal loanLimit) {

        BorrowRuleResult result = new BorrowRuleResult();
        result.setBorrowId(borrowId);
        result.setResultType(resultType);
        result.setLoanLimit(loanLimit);
        result.setResultMsg(resultMsg);
        result.setAddTime(new Date());
        System.out.println(result.getResultMsg());
        System.out.println("开始保存====================");
        int save = borrowRuleResultMapper.save(result);
        System.out.println("结束保存====================");
        return result;
    }


    @Override
    public BorrowRuleResult ruleEngine(BorrowMain borrow, Envelope envelope,List<UserContactsMatch> list) {

        logger.info("<-----借款审核：决策判定开始-----borrowId={},userId={}>",new Object[]{ borrow.getId() ,borrow.getUserId() });
        //用户ID
        Long userId=borrow.getUserId();
        //获得用户基本信息
        UserBaseInfo user = userBaseInfoMapper.findByUserId(userId);
        if(user == null){
            throw new RuntimeException("相关用户不存在");
        }
        String coordinate = borrow.getCoordinate();
        //调用规则引擎，获得结果
        BorrowRuleResult result = BorrowRiskRuleMatch.getResultByRuleEngine(user, envelope,list,coordinate);
        //保存borrowId
        result.setBorrowId(borrow.getId());
        //存入数据库，以便后台人员人工审核及查看
        int insert = borrowRuleResultService.insert(result);
        if(insert < 1){
            logger.error("数据库保存异常，borrowId为"+borrow.getId());
            throw new RuntimeException("记录保存异常");
        }
        //追加到备注里面去
        //更新一下备注
        Map<String, Object> map = new HashMap<>();
        map.put("id",borrow.getId());
        map.put("remark",( borrow.getRemark() != null ? borrow.getRemark() : "") + ";" + result.getResultMsg());
        borrowMainMapper.updateBorrowRemarkNoState(map);
        return result;
    }

    @Override
    public BorrowRuleResult findByBorrowMainId(Long borrowId) {

        Map<String, Object> params = new HashMap<>();
        params.put("borrowMainId",borrowId);
        return borrowRuleResultMapper.findByBorrowMainId(params);
    }
}