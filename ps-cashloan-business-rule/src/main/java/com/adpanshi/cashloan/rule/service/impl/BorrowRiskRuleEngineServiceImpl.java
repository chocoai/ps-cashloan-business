package com.adpanshi.cashloan.rule.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import com.adpanshi.cashloan.rule.service.*;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 借款风控规则-同盾
 *
 * @version 1.0.0
 * @date 2018-02-27 09:23:19
 * @author
 */

@Service("BorrowRiskRuleEngineService")
public class BorrowRiskRuleEngineServiceImpl implements BorrowRiskRuleEngineService {
    final Logger logger = LoggerFactory.getLogger(BorrowRiskRuleEngineServiceImpl.class);

    @Resource
    private ApplicationContext context;
    @Resource
    private BorrowRuleResultService borrowRuleResultService;
    @Resource
    private UserContactsService userContactsService;
    @Resource
    private EquifaxReportService equifaxReportService;

    @Override
    public String borrowRiskRule(final BorrowMain borrow,String mobileType,boolean reBorrowUser){
        logger.info("<-----借款审核：风控规则判定进入----->：");
        //执行规则获得返回结果,默认人审核
        String resultCode=BorrowRuleResult.RESULT_TYPE_REVIEW;
        //获取风控开关
        JSONObject ruleSwitch= JSONObject.parseObject(Global.getValue("borrowRiskRule_switch"));
        if("on".equals(ruleSwitch.getJSONObject("BorrowRiskRule").getString("switch"))) {
            try {

                //获取通讯录
                Page<UserContactsMatch> page = userContactsService.listContactsNew(borrow.getUserId(), 1, 30);
                //获得用户信用报告
                Envelope envelope = equifaxReportService.getEquifaxReportDetail(borrow.getUserId());
                BorrowRuleResult borrowRuleResult = borrowRuleResultService.ruleEngine( borrow,envelope,page);
                return borrowRuleResult.getResultType();
//                //获取所有风控决策类名称
//                String[] beanNames = context.getBeanNamesForType(BorrowRiskRule.class);
//                //有决策类就进行决策
//                if (beanNames != null && beanNames.length > 0) {
//                    ruleSwitch = ruleSwitch.getJSONObject("BorrowRiskRuleSwitchs");
//                    //保存排序的class
//                    Class[] clazzs = new Class[beuanNames.length];
//                    int needExcuteCount = 0;
//                    for (int i = 0; i < beanNames.length; i++) {
//                        //开关打开才会执行
//                        if(null!=ruleSwitch.getJSONObject(beanNames[i]) && "on".equals(ruleSwitch.getJSONObject(beanNames[i]).getString("switch"))) {
//                            //获取单个风控决策决策类
//                            Class clazz = context.getBean(beanNames[i]).getClass();
//                            clazzs[needExcuteCount] = clazz;
//                            needExcuteCount++;
//                        }
//                    }
//                    //截取 这个读取了什么~ -~
//                    clazzs = Arrays.copyOfRange(clazzs,0,needExcuteCount);
//                    //排序
//                    clazzs = ClassSortUtil.sortClass(clazzs);
//                    for (Class clazz : clazzs) {
//                        //执行风控，返回结果
//                        resultCode = (String) clazz.getMethod("ruleEngine", BorrowMain.class, String.class, boolean.class).invoke(context.getBean(clazz), borrow, mobileType, reBorrowUser);
//                        //拒绝则跳过其余判定
//                        if (StringUtils.isNotEmpty(resultCode) && BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultCode)) {
//                            break;
//                        }
//                    }
//                }
            } catch (Exception e) {
                logger.error("------风控过程异常------", e);
            }
        }
        return resultCode;
    }
}