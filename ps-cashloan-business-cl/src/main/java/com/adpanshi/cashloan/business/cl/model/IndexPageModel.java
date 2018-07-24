package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.rule.model.ScaleShowModel;

import java.util.List;

/**
 * @author yecy
 * @date 2017/12/15 22:37
 */
public class IndexPageModel {
    // 最大可借金额（用于展示）
    private String maxAmount;

    // 最小可借金额
    private String minAmount;

    //金额最小间隔
    private String interval;

    //可分期列表
    private List<ScaleShowModel> scales;

    // 总认证个数
    private Integer authTotalNum;

    //用户已经认证的个数
    private Integer authNum = 0;

    //基础认证必填项是否全部填写
    private Boolean qualified = false;
    
  //高级认证必填项是否填写(是否满足三选一)
    private Boolean choose = false;

    // 首页轮播图
    private List<Banner> banners;

    // 当前借款订单状态
    private String state;

    // mainId
    private Long borrowMainId;

    // 租房认证状态
    private Boolean isAuthToTenement;
    
    /**常见问题*/
    private List<AskedQuestionsModel> askedQuestions;

    // 最近还款日
    private String recentRepayDate;

    //最近应还金额
    private String recentRepayAmount;

    // 剩余应还总额
    private String totalRepayAmount;

    //逾期笔数
    private Integer penaltyPeriods;

    // 是否可以借款（评估额度）
    private Boolean canAssessment;
    
    //调用大风策审批接口间隔时间
    private int intervalDay;
    
    private String errorMsg;

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

    public List<ScaleShowModel> getScales() {
        return scales;
    }

    public void setScales(List<ScaleShowModel> scales) {
        this.scales = scales;
    }

    public Integer getAuthTotalNum() {
        return authTotalNum;
    }

    public void setAuthTotalNum(Integer authTotalNum) {
        this.authTotalNum = authTotalNum;
    }

    public Boolean getChoose() {
		return choose;
	}

	public void setChoose(Boolean choose) {
		this.choose = choose;
	}

	public Integer getAuthNum() {
        return authNum;
    }

    public void setAuthNum(Integer authNum) {
        this.authNum = authNum;
    }

    public Boolean getQualified() {
        return qualified;
    }

    public void setQualified(Boolean qualified) {
        this.qualified = qualified;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getBorrowMainId() {
        return borrowMainId;
    }

    public void setBorrowMainId(Long borrowMainId) {
        this.borrowMainId = borrowMainId;
    }

    public Boolean getAuthToTenement() {
        return isAuthToTenement;
    }

    public void setAuthToTenement(Boolean authToTenement) {
        isAuthToTenement = authToTenement;
    }

    public List<AskedQuestionsModel> getAskedQuestions() {
		return askedQuestions;
	}

	public void setAskedQuestions(List<AskedQuestionsModel> askedQuestions) {
		this.askedQuestions = askedQuestions;
	}

    public String getRecentRepayDate() {
        return recentRepayDate;
    }

    public void setRecentRepayDate(String recentRepayDate) {
        this.recentRepayDate = recentRepayDate;
    }

    public String getRecentRepayAmount() {
        return recentRepayAmount;
    }

    public void setRecentRepayAmount(String recentRepayAmount) {
        this.recentRepayAmount = recentRepayAmount;
    }

    public String getTotalRepayAmount() {
        return totalRepayAmount;
    }

    public void setTotalRepayAmount(String totalRepayAmount) {
        this.totalRepayAmount = totalRepayAmount;
    }

    public Integer getPenaltyPeriods() {
        return penaltyPeriods;
    }

    public void setPenaltyPeriods(Integer penaltyPeriods) {
        this.penaltyPeriods = penaltyPeriods;
    }

    public Boolean getCanAssessment() {
        return canAssessment;
    }

    public void setCanAssessment(Boolean canAssessment) {
        this.canAssessment = canAssessment;
    }
    
    public int getIntervalDay() {
		return intervalDay;
	}

	public void setIntervalDay(int intervalDay) {
		this.intervalDay = intervalDay;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	public static class Banner {
        private String bannerUrl;
        private String landPage;

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getLandPage() {
            return landPage;
        }

        public void setLandPage(String landPage) {
            this.landPage = landPage;
        }
    }
}


