package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.service.BorrowTemplateService;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.rule.domain.BankCard;
import com.adpanshi.cashloan.rule.domain.BorrowTemplate;
import com.adpanshi.cashloan.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.rule.mapper.BorrowTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.BigDecimalUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 借款模板信息表ServiceImpl
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-11-30 15:14:56
 */

@Service("borrowTemplateService")
public class BorrowTemplateServiceImpl extends BaseServiceImpl<BorrowTemplate, Long> implements BorrowTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowTemplateServiceImpl.class);

    @Resource
    private BorrowTemplateMapper borrowTemplateMapper;
    @Resource
    private BankCardMapper bankCardMapper;
    @Override
    public BaseMapper<BorrowTemplate, Long> getMapper() {
        return borrowTemplateMapper;
    }
    @Override
    public Map<String, Object> choice(double amount, String timeLimit, String userId) {
        // 借款天数
        String borrowDay = Global.getValue("borrow_day");
        String[] days = borrowDay.split(",");
        // 信息认证费
        String[] infoAuthFees = Global.getValue("info_auth_fee").split(",");
        // 居间服务费
        String[] serviceFees = Global.getValue("service_fee").split(",");
        // 利息
        String[] interests = Global.getValue("interest").split(",");
        // 砍头息
        String[] headFees = Global.getValue("head_fee").split(",");

        Map<String, Object> map = new HashMap<>(16);
        map.put("amount", amount);
        map.put("timeLimit", timeLimit);

        double infoAuthFee = 0;
        double serviceFee = 0;
        double interest = 0;
        double fee = 0;
        double headFee = 0;
        for (int i = 0; i < days.length; i++) {
            if (timeLimit.equals(days[i])) {
                infoAuthFee = BigDecimalUtil.round(BigDecimalUtil.mul(amount, Double.parseDouble(infoAuthFees[i]),Double.parseDouble(timeLimit)));
                serviceFee = BigDecimalUtil.round(BigDecimalUtil.mul(amount, Double.parseDouble(serviceFees[i]),Double.parseDouble(timeLimit)));
                interest = BigDecimalUtil.round(BigDecimalUtil.mul(amount, Double.parseDouble(interests[i]),Double.parseDouble(timeLimit)));
                headFee = BigDecimalUtil.round(BigDecimalUtil.mul(amount,Double.parseDouble(headFees[i])));
                fee = BigDecimalUtil.add(infoAuthFee, serviceFee, interest);

                map.put("fee", BigDecimalUtil.decimal(fee, 2));

                // 是否启用砍头息
                String beheadFee = Global.getValue("behead_fee");
                //砍头息启用状态-开
                String beheadFeeOn = "10";
                // 启用
                if (beheadFeeOn.equals(beheadFee)) {
                    map.put("realAmount", amount - headFee);
                } else {
                    map.put("realAmount", amount);
                }
            }
        }

        Map<String, Object> feeDetail = new HashMap<>(16);

        feeDetail.put("infoAuthFee", infoAuthFee);
        feeDetail.put("serviceFee", serviceFee);
        feeDetail.put("interest", interest);
        feeDetail.put("headFee", headFee);
        feeDetail.put("totalFee",fee);

        //获取绑定卡信息
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("userId", Long.parseLong(userId));
        BankCard bc = bankCardMapper.findSelective(paramMap);
        if (StringUtil.isNotBlank(bc)) {
            Map<String, Object> cardInfo = new HashMap<>(16);
            cardInfo.put("cardId", bc.getId());
            cardInfo.put("cardNo", bc.getCardNo().substring(bc.getCardNo().length()-4));
            cardInfo.put("cardName", bc.getBank());
            map.put("cardInfo",cardInfo);
        }
        map.put("feeDetail", feeDetail);
        return map;
    }

}
