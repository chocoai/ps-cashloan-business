package com.adpanshi.cashloan.cl.model.stagin;

import com.adpanshi.cashloan.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.cl.model.StaginRepaymentPlanData;
import com.adpanshi.cashloan.cl.model.TemplateInfoModel;
import com.adpanshi.cashloan.core.common.util.MathUtil;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.core.domain.BorrowMain;
import com.adpanshi.cashloan.core.model.BorrowModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 ** @category 订单扩展统计...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月29日下午8:29:16
 **/
public class BorrowData {

	/**校验通过标识 */
	private boolean pass;
	
	/**主订单id*/
	private Long borrowMainId;
	
	/**前端用户勾选还款订单号-所生成的订单号*/
	private String genOrderNo;
	
	/**前端用户勾选还款订单号数组集*/
	private String[] orderNos;
	
	/**还款订单主键集*/
	private List<Long> borrowIds;
	
	private BorrowExtra borrowExtra;
	
	private TemplateInfoModel templateInfoModel;
	
	/**
	 * <p>@1.根据给定borrows校验是否有已还款</p>
	 * <p>@2.根据给定borrows校验是否是同一主订单</p>
	 * <p>@3.根据给定borrows校验是否是同一用户</p>
	 * <p>@4.如果以上三点，任意一点不满足、直接跳过统计并返回、如果满足、则进行各种统计(包括逾期费、提前还款手续费)</p>
	 * @param borrows      待校验的订单集
	 * @param borrowRepays 还款计划列表
	 * @param borrowMain   主订单
	 * @param userId       待校验的用户
	 * @return BorrowData
	 * */
	public static BorrowData getBorrowData(List<Borrow> borrows,List<BorrowRepay> borrowRepays,BorrowMain borrowMain,long userId){
		BorrowExtra borrowExtra=new BorrowExtra();
		BorrowData data=new BorrowData();
		List<Long> borrowIds=new ArrayList<>();
		BigDecimal fees=new BigDecimal(0);
		BigDecimal infoAuthFees=new BigDecimal(0);
		BigDecimal realAmounts=new BigDecimal(0);
		BigDecimal serviceFees=new BigDecimal(0);
		if(borrows.size()!=borrowRepays.size())return data;
		Map<String,Double> map=new HashMap<>();
		long borrowMainId=borrowMain.getId();
		for(Borrow bor:borrows){
			//@1.判断是否已有还款的订单!
			if(bor.getState().equals(BorrowModel.STATE_FINISH)) return data;
			//@2.判断主订单号是否一致!
			if(bor.getBorrowMainId().compareTo(borrowMainId)!=0)return data;
			//@3.判断用户勾选的订单号是否与当前用户一致!
			if(bor.getUserId().compareTo(userId)!=0) return data;
			borrowIds.add(bor.getId());
			fees=fees.add(new BigDecimal(bor.getFee()));
			infoAuthFees=infoAuthFees.add(new BigDecimal(null==bor.getInfoAuthFee()?0:bor.getInfoAuthFee()));
			realAmounts=realAmounts.add(new BigDecimal(bor.getRealAmount()));
			serviceFees=serviceFees.add(new BigDecimal(null==bor.getServiceFee()?0:bor.getServiceFee()));
			map.put(bor.getId().toString(), bor.getFee());
		}
		//计算逾期费与提前还款手续费
		double waitPenaltyAmout=0d;
		TemplateInfoModel templateInfo= new TemplateInfoModel().parseTemplateInfo(borrowMain.getAmount(), borrowMain.getTemplateInfo());
		double earlyFee=0;
		for(BorrowRepay repay: borrowRepays){
			waitPenaltyAmout=MathUtil.add(repay.getPenaltyAmout(),waitPenaltyAmout);
			earlyFee+=StaginRepaymentPlanData.getPrepayment(repay.getRepayTime(), map.get(String.valueOf(repay.getBorrowId())),templateInfo.getCycle());
		}
		data.setBorrowIds(borrowIds);
		borrowExtra.setPenaltyAmounts(waitPenaltyAmout);
		borrowExtra.setFees(fees.doubleValue()-earlyFee);
		borrowExtra.setInfoAuthFees(infoAuthFees.doubleValue());
		borrowExtra.setRealAmounts(realAmounts.doubleValue());
		borrowExtra.setServiceFees(serviceFees.doubleValue());
		borrowExtra.setEarlyFee(earlyFee);
		data.setPass(true);
		data.setBorrowMainId(borrowMainId);
		data.setBorrowExtra(borrowExtra);
		data.setTemplateInfoModel(templateInfo);
		return data;
	}
	
	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public Long getBorrowMainId() {
		return borrowMainId;
	}

	public void setBorrowMainId(Long borrowMainId) {
		this.borrowMainId = borrowMainId;
	}

	public String getGenOrderNo() {
		return genOrderNo;
	}

	public void setGenOrderNo(String genOrderNo) {
		this.genOrderNo = genOrderNo;
	}

	public String[] getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(String[] orderNos) {
		this.orderNos = orderNos;
	}

	public List<Long> getBorrowIds() {
		return borrowIds;
	}

	public void setBorrowIds(List<Long> borrowIds) {
		this.borrowIds = borrowIds;
	}

	public BorrowExtra getBorrowExtra() {
		return borrowExtra;
	}

	public void setBorrowExtra(BorrowExtra borrowExtra) {
		this.borrowExtra = borrowExtra;
	}

	public TemplateInfoModel getTemplateInfoModel() {
		return templateInfoModel;
	}

	public void setTemplateInfoModel(TemplateInfoModel templateInfoModel) {
		this.templateInfoModel = templateInfoModel;
	}
	
}
