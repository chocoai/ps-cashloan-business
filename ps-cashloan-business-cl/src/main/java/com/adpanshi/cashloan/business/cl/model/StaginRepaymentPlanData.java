package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.exception.BussinessException;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.MathUtil;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.model.StaginRepaymentPlanModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月26日上午11:19:35
 **/
public class StaginRepaymentPlanData {

	private String totalStagin;//分期总数
	
	private String repaymentStagin;//已还分期数
	
	private String unRepaymentStagin;//未还分期数
	
	List<StaginRepaymentPlanModel> staginRepaymentPlans;
	

	/**
	 * <p(分期还款-还款计划列表) 根据给定userId及borrowMainId 查询还款计划列表></p>
	 * @param dataList
	 * @param amount 
	 * @param dayRates 日利率
	 * @param repayment (可选项:true 已还款、false 未还款)
	 * @return
	 * */
	public static StaginRepaymentPlanData handle(List<StaginRepaymentPlanModel> dataList,Double amount,Double dayRates,Boolean repayment,Long cycle){
		StaginRepaymentPlanData data=new StaginRepaymentPlanData();
		if(null==dataList||dataList.size()==0){
			data.setStaginRepaymentPlans(new ArrayList<StaginRepaymentPlanModel>());
			return data;
		}
		if(null==repayment) throw new BussinessException(Constant.FAIL_CODE_PARAM_INSUFFICIENT+"", "缺少必要参数");
		List<StaginRepaymentPlanModel> staginRepaymentPlans=new ArrayList<>();
		data.setTotalStagin(String.valueOf(dataList.size()));
		for(StaginRepaymentPlanModel staginRepayment:dataList){
			staginRepayment.setByStages(byStages(staginRepayment.getOrderNo()));
			staginRepayment.setRepaymentDay(DateUtil.daysBetween(new Date(),DateUtil.parse(staginRepayment.getRepayTime(), DateUtil.YYYY_MM_DD)));
			//  staginRepayment.getFee() 字段不需要加到总金额中，手续费包含在利息中 @author yecy 20180306
			staginRepayment.setTotalAmount(MathUtil.add(staginRepayment.getAmount(),staginRepayment.getPenaltyAmout(),staginRepayment.getInterests()));
			if(repayment.booleanValue()){
				if(staginRepayment.getState().equals(BorrowModel.STATE_FINISH)) staginRepaymentPlans.add(staginRepayment);
			}else{
				if(!staginRepayment.getState().equals(BorrowModel.STATE_FINISH)){
					staginRepaymentPlans.add(staginRepayment);
				}
			}
		}
		int repaymentCnt=repayment.booleanValue()?staginRepaymentPlans.size():dataList.size()-staginRepaymentPlans.size();
		data.setRepaymentStagin(String.valueOf(repaymentCnt));
		data.setUnRepaymentStagin(String.valueOf((dataList.size()-repaymentCnt)));
		data.setStaginRepaymentPlans(staginRepaymentPlans);
		return data;
	}

	/**
	 * <p>计算提前还款</p>
	 * @param repayTime 应还款时间
	 * @param interests 每期的利息
	 * @param cycle 每期的周期(天)
	 * @return 手续费
	 * */
    public  static Double getPrepayment(Date repayTime,Double interests,Long cycle){
    	return getPrepayment(repayTime, new Date(), interests, cycle);
	}
    
    /**
	 * <p>计算提前还款</p>
	 * @param repayTime 应还款时间
	 * @param reallyTime 实还时间
	 * @param interests 每期的利息
	 * @param cycle 每期的周期(天)
	 * @return 手续费
	 * */
    public  static Double getPrepayment(Date repayTime,Date reallyTime, Double interests,Long cycle){
    	//每期的利息=总本金*日利率(0.001)*每期的周期(天)
		Double fee=0d;
		int days=DateUtil.daysBetween(reallyTime, repayTime);
		if(days<=0) return fee;
		if(days>cycle)days=cycle.intValue();//如果提前还款、计算提前手续费时、按最大周期算
		//日息率 0.1/100=0.001
//		double dailyRates=null==dayRates? MathUtil.div(0.1d,100d, 3):dayRates;//日利率
		//每天的利息
		// 每日利息改为 每期利息/当前周期 @author yecy 20180306
		double dayFee = MathUtil.div(interests,cycle.doubleValue(), 2);
//		double dayFee= MathUtil.multiply(amount, dailyRates, 2);
		return MathUtil.multiply(dayFee, Double.parseDouble(days+""), 2);
	}
	
	public static String byStages(String orderNo){
		return orderNo.substring(orderNo.lastIndexOf("X")+1);
	}


	public StaginRepaymentPlanData(){}
	
	public StaginRepaymentPlanData(String totalStagin){
		this.totalStagin=totalStagin;
	}
	
	public StaginRepaymentPlanData(String totalStagin,String repaymentStagin,String unRepaymentStagin){
		this.totalStagin=totalStagin;
		this.repaymentStagin=repaymentStagin;
		this.unRepaymentStagin=unRepaymentStagin;
	}

	public String getTotalStagin() {
		return totalStagin;
	}

	public void setTotalStagin(String totalStagin) {
		this.totalStagin = totalStagin;
	}

	public String getRepaymentStagin() {
		return repaymentStagin;
	}

	public void setRepaymentStagin(String repaymentStagin) {
		this.repaymentStagin = repaymentStagin;
	}

	public String getUnRepaymentStagin() {
		return unRepaymentStagin;
	}

	public void setUnRepaymentStagin(String unRepaymentStagin) {
		this.unRepaymentStagin = unRepaymentStagin;
	}

	public List<StaginRepaymentPlanModel> getStaginRepaymentPlans() {
		return staginRepaymentPlans;
	}

	public void setStaginRepaymentPlans(List<StaginRepaymentPlanModel> staginRepaymentPlans) {
		this.staginRepaymentPlans = staginRepaymentPlans;
	}
	
	
}
