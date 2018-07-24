package com.adpanshi.cashloan.business.cl.domain;

import java.io.Serializable;

/**
 * 借款主信息表实体
 *
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 16:09:17
 */
public class BorrowMainProgress extends BaseBorrowProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 申请成功待审核 */
    public static final String PROGRESS_APPLY = "10";
    /** 自动审核通过 */
    public static final String PROGRESS_AUTO_PASS = "20";
    /** 自动审核不通过 */
    public static final String PROGRESS_AUTO_REFUSED = "21";
    /** 自动审核未决待人工复审 */
    public static final String PROGRESS_NEED_REVIEW = "22";
    /** 人工复审挂起 */
    public static final String PROGRESS_PERSON_HANGUP = "25";
    /** 人工复审通过 */
    public static final String PROGRESS_PERSON_PASS = "26";
    /** 人工复审不通过 */
    public static final String PROGRESS_PERSON_RESUSED = "27";
    /** 放款成功 */
    public static final String PROGRESS_LOAN_SUCCESS = "30";
    /** 放款失败 */
    public static final String PROGRESS_LOAN_FAIL = "31";
    /** 还款成功 */
    public static final String PROGRESS_REPAY_SUCCESS = "40";
    /** 逾期减免 */
    public static final String PROGRESS_REPAY_REMISSION_SUCCESS = "41";
    /** 逾期 */
    public static final String PROGRESS_REPAY_OVERDUE = "50";
    /** 坏账 */
    public static final String PROGRESS_BILL_BAD = "90";

    /** 临时状态-自动审核 */
    public static final String STATE_TEMPORARY_AUTO_PASS = "12";   //70-初审审核成功 72初审待人工复审

    /** 临时状态-自动审核未决待人工复审 */
    public static final String STATE_TEMPORARY_NEED_REVIEW = "14";   //70-初审审核成功 72初审待人工复审

    /**
     * 状态描述
     */
    private String str;

    private String alter(String state) {
        String stateStr = "";
        if (PROGRESS_APPLY.equals(state)) {
            stateStr = "Submit Success";
        }else if (PROGRESS_NEED_REVIEW.equals(state)
                || STATE_TEMPORARY_NEED_REVIEW.equals(state)
                || STATE_TEMPORARY_NEED_REVIEW.equals(state)
                || PROGRESS_PERSON_HANGUP.equals(state)) {
            stateStr = "Under Review";
        }else if (PROGRESS_AUTO_PASS.equals(state)
                ||PROGRESS_PERSON_PASS.equals(state)) {
            stateStr = "Approved";
        }else if (PROGRESS_AUTO_REFUSED.equals(state)
                ||PROGRESS_PERSON_RESUSED.equals(state)) {
            stateStr = "UnApproved";
        }else if (PROGRESS_LOAN_SUCCESS.equals(state)
                ||PROGRESS_LOAN_FAIL.equals(state)) {
            stateStr = "Paid Out";
        }else if (PROGRESS_REPAY_SUCCESS.equals(state)
                ||PROGRESS_REPAY_REMISSION_SUCCESS.equals(state)){
            stateStr="Repaid";
        }else if (PROGRESS_REPAY_OVERDUE.equals(state)){
            stateStr="Overdue";
        }else if (PROGRESS_BILL_BAD.equals(state)){
            stateStr="Bad Bill";
        }else{
            stateStr=state;
        }
        return stateStr;
    }

    /**
     * @return the str
     */
    public String getStr() {
        return str;
    }


    /**
     * @param str the str to set
     */
    public void setStr(String str) {
        this.str = alter(str);
    }

}
