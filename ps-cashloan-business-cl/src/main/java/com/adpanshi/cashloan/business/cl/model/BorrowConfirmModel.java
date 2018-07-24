package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.extra.BorrowIntent;
import com.adpanshi.cashloan.business.rule.model.ScaleShowModel;

import java.util.List;

/**
 * @author yecy
 * @date 2017/12/22 10:28
 */
public class BorrowConfirmModel extends RepayShowModel {
    /**
     * 用户可借金额列表（用于后期扩充，暂时不需要 @yecy 20180202）
     */
    private List<String> amountList;

    /**
     * 可分期列表
     */
    private List<ScaleShowModel> scales;

    /**
     * 借款意图
     * */
    private List<BorrowIntent> borrowIntents;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 银行名称
     */
    private String bank;

    /**
     * 银行图标地址
     */
    private String bankIcon;

    /**
     * 银行卡号Id(用于下单存储)
     */
    private Long cardId;

    /**
     * 最大可借金额
     */
    private String maxAmount;

    /**
     * 最少可借金额
     */
    private String minAmount;

    /**
     * 可借金额间隔
     */
    private String interval;
    
    /**借款场景*/
    private Integer sceneType;
    
    /**场景是否上传*/
    private boolean sceneUpload;

    public List<String> getAmountList() {
        return amountList;
    }

    public void setAmountList(List<String> amountList) {
        this.amountList = amountList;
    }

    public List<ScaleShowModel> getScales() {
        return scales;
    }

    public void setScales(List<ScaleShowModel> scales) {
        this.scales = scales;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

	public List<BorrowIntent> getBorrowIntents() {
		return borrowIntents;
	}

	public void setBorrowIntents(List<BorrowIntent> borrowIntents) {
		this.borrowIntents = borrowIntents;
	}


    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	public boolean isSceneUpload() {
		return sceneUpload;
	}

	public void setSceneUpload(boolean sceneUpload) {
		this.sceneUpload = sceneUpload;
	}
	
    
}
