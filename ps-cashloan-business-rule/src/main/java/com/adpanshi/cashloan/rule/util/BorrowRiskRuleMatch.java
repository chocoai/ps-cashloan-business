package com.adpanshi.cashloan.rule.util;


import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.rule.domain.UserContactsMatch;
import com.adpanshi.cashloan.rule.domain.equifaxReport.base.Envelope;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class BorrowRiskRuleMatch {

    //新德里政府经度
    public static final double LAT = 77.20466;
    //新德里政府纬度
    public static final double LNG = 28.62735;

    public static BorrowRuleResult getResultByRuleEngine(UserBaseInfo user, Envelope envelope) {

        return getResultByRuleEngine(user, envelope, null, null);
    }

    public static BorrowRuleResult getResultByRuleEngine(UserBaseInfo user, Envelope envelope, List<UserContactsMatch> userContactsMatchList, String coordinate) {

        //规则引擎判断结果 默认为通过
        String resultType = BorrowRuleResult.RESULT_TYPE_PASS;
        //审核不通过信息 审核通过
        String resultMsg = "Review and pass through";
        //可贷款额度
        BigDecimal loanLimit = new BigDecimal(0);

        //把结果封存到对象中，以便返回结果
        BorrowRuleResult borrowRuleResult = new BorrowRuleResult();
        //规则引擎总开关
        if ("on".equals(Global.getValue("total_rule_engine_position"))) {

            if (user == null) {
                throw new RuntimeException("用户基本信息不存在");
            }

            //如果存在个人信用报告
            if (envelope != null) {
                //个人信息
                String first_name = envelope.getBody().getInquiryResponse().getInquiryRequestInfo().getFirstName();
                String last_name = envelope.getBody().getInquiryResponse().getInquiryRequestInfo().getLastName();
                String phone = envelope.getBody().getInquiryResponse().getInquiryRequestInfo().getMobilePhone();
                String idAddr = envelope.getBody().getInquiryResponse().getInquiryRequestInfo().getAddrLine1();
                //信用分
                int score = Integer.parseInt(envelope.getBody().getInquiryResponse().getReportData().getScore().getValue());

                //如果用户的信用值低于平均水平，即450分，拒绝贷款申请；
                if (score < 450) {
                    //拒绝贷款 用户信用积分：用户用户的信用值低于平均水平，即450分,拒绝贷款申请
                    resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                    resultMsg = "User credit score:" + score + ",The user's credit value is below the average level, that is, 450 points, refusing to apply for a loan";
                }
                //注册姓名与信用报告姓名强匹配
//            if (StringUtils.isEmpty(user.getFirstName()) || StringUtils.isEmpty(user.getLastName())||!(user.getFirstName().equals(first_name)) || !(user.getLastName().equals(last_name))){
//                //不匹配则不通过 用户注册姓名与信用报告的姓名不一致，用户注册姓名 信用报告姓名 拒绝贷款申请
//                resultType=BorrowRuleResult.RESULT_TYPE_REFUSED;
//                resultMsg = "The name of the user is inconsistent with the name of the credit report, and the name of the user is registered:"+user.getFirstName()+" "+user.getLastName()
//                        +",Name of credit report:"+first_name+" "+last_name+",Refusal of loan application";
//            }
                //手持aadhaar照片，只要上传照片就行
                if (StringUtils.isBlank(user.getLivingImg())) {
                    //不匹配则不通过 用户没有上传手持aadhaar照片
                    resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                    resultMsg = "Users do not upload handheld aadhaar photos";
                }

                //通讯录有效人数低于30人直接拒绝
                if (userContactsMatchList == null || userContactsMatchList.size() < 30) {
                    //不匹配则不通过 通讯录有效人数低于30人
                    resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                    resultMsg = "The number of letters of communication is less than 30";
                }

                //21-40岁（含21和40）不到直接拒
                if (user.getAge() == null || user.getAge() < 21 || user.getAge() > 40) {
                    //不匹配则不通过 借款人年龄不在21~40之间
                    resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                    resultMsg = "The borrower's age is not between 21~40";
                }

                //新德里市中心经纬度77.20466,28.62735 经纬度为空时为机审
                //首先不用判断经纬度 获取位置规则引擎总开关
                String ruleEnginePosition = Global.getValue("rule_engine_position");
                if ("on".equals(ruleEnginePosition)) {
                    if (StringUtils.isBlank(coordinate) || coordinate.split(",").length != 2) {
                        // 未获取到用户位置信息
                        resultType = BorrowRuleResult.RESULT_TYPE_REVIEW;
                        resultMsg = "No access to user location information";
                    } else {

                        try {
                            double lat = Double.parseDouble(coordinate.split(",")[0]);
                            double lng = Double.parseDouble(coordinate.split(",")[1]);
                            //获得用户注册地址
                            String registerAddrUpper = user.getRegisterAddr().toUpperCase();
                            if ((MapUtils.GetDistance(LAT, LNG, lat, lng) > 40 * 1000)
                                    && !registerAddrUpper.contains("NOIDA")
                                    && !registerAddrUpper.contains("GREATER NOIDA")
                                    && !registerAddrUpper.contains("FARIDABAD")
                                    && !registerAddrUpper.contains("GURGAON")
                                    && !registerAddrUpper.contains("GHAZIABAD")
                                    && !registerAddrUpper.contains("SONIPAT")
                                    && !registerAddrUpper.contains("BAHADURGARH")) {

                                //不包含可信任地区 定位德里某个点为中心，40公里范围内能接受，超过直接拒绝 距离新德里市中心超过40公里或者不包含可信任地区，用户位置信息为
                                resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                                resultMsg = "It is more than 40 kilometers away from downtown New Delhi, or does not contain trustworthy areas.User location information:" + user.getRegisterAddr();
                            } else {
                                //判断是否在贫民窟
                                if (registerAddrUpper.contains("SLUM")) {
                                    resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                                    resultMsg = "In the slums, the refusal of a loan.User location information:" + user.getRegisterAddr();
                                }
                            }
                        } catch (Exception e) {
                            //建议人工审核 用户位置信息不合法，经纬度信息
                            resultType = BorrowRuleResult.RESULT_TYPE_REVIEW;
                            resultMsg = "User location information is unlawful, latitude and longitude information为:" + coordinate;
                        }

                    }
                }


                //如果不是拒绝贷款,则下步判定
                if (!BorrowRuleResult.RESULT_TYPE_REFUSED.equals(resultType)) {

                    //注册手机号与信用报告手机号弱匹配
                    if (!user.getPhone().equals(phone)) {
                        //建议人工审核 用户注册手机号与信用报告的手机号不一致，建议人工审核 用户注册手机号 信用报告手机号
                        resultType = BorrowRuleResult.RESULT_TYPE_REVIEW;
                        resultMsg = "User registration phone number is inconsistent with the mobile phone number of the credit report. It is recommended that manual audit be conducted.;" +
                                "User registered mobile phone number:" + user.getPhone() + ",Credit report cell phone number:" + phone;
                    }

                    //注册地址信息与信用报告弱匹配 用户注册地址与信用报告的地址不一致，建议人工审核;用户注册地址 信用报告地址
//                    if (!idAddr.equals(user.getIdAddr())) {
//                        resultType = BorrowRuleResult.RESULT_TYPE_REVIEW;
//                        resultMsg = "The user registration address is inconsistent with the address of the credit report. It is suggested that manual audit should be done; the user's address should be registered:" + user.getIdAddr() + "." +
//                                "Credit report address:" + user.getIdAddr();
//                    }
                    //其余条件已通过，审核信用分，判断贷款额度
                    if (score >= 651) {
                        //3000卢布
                        loanLimit = new BigDecimal(3000);
                    } else if (score >= 551) {
                        //2000卢布
                        loanLimit = new BigDecimal(2000);
                    } else {
                        //信用分450~550
                        //1000卢布
                        loanLimit = new BigDecimal(1000);
                    }
                }
            } else {
                resultType = BorrowRuleResult.RESULT_TYPE_REFUSED;
                //xinyongbaogaobucunzai
                resultMsg = "The credit report does not exist";
            }


        }else {
            //關閉后直接走人審
            loanLimit = new BigDecimal(3000);
            resultType = BorrowRuleResult.RESULT_TYPE_REVIEW;
            resultMsg = "The rule engine has been closed";
        }
        //返回規則
        borrowRuleResult.setResultMsg(resultMsg);
        borrowRuleResult.setResultType(resultType);
        borrowRuleResult.setLoanLimit(loanLimit);
        borrowRuleResult.setAddTime(new Date());
        return borrowRuleResult;
    }

}
