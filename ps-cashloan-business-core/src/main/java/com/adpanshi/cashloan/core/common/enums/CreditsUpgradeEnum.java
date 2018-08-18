package com.adpanshi.cashloan.core.common.enums;

import com.adpanshi.cashloan.core.common.context.Global;
import org.apache.commons.lang3.StringUtils;

/***
 ** @category 用户信用额度提升枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月31日上午11:44:10
 **/
public class CreditsUpgradeEnum {

	/**借款额度区间 最小额度-最大额度以逗号隔开.(对应-sys_config)*/
	public static final String BORROW_CREDIT="borrow_credit";
	
	/**临时额度过期提醒JOB.(对应sys_config)*/
	public static final String CREDITS_EXPIRED_REMIND_JOB="creditsExpiredRemind";
	
	/**用户临时额度过期作废JOB.(对应sys_config)*/
	public static final String CREDITS_INVALID="creditsInvalid";
	
	/**额度提升总开关(对应sys_config) on:开*/
	public static final String CREDITS_SWITCH="credits_switch";
	
	
	/**临时额度期限*/
	public enum  TMP_PERIOD implements ICommonEnum{
		
		DAY_7("7","临时额度期限为7天");
		
	    private final String code;
	    private final String name;
	    
	    private TMP_PERIOD( String code,String name) {
	        this.code = code;
	        this.name = name;
	    }
		@Override
		public String getCode() {
			return code;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public Integer getKey() {
			return Integer.parseInt(code);
		}
	}
	
	/**消息发送状态*/
	public enum  SEND_STATUS implements ICommonEnum{
		//(1:待发送、2.发送失败、3.发送成功)
		WAIT_SEND("1","待发送"),
		FAILURE_SEND("2","发送失败"),
		SUCCESS_SEND("3","发送成功");
	    private final String code;
	    private final String name;
	    
	    private SEND_STATUS( String code,String name) {
	        this.code = code;
	        this.name = name;
	    }
		@Override
		public String getCode() {
			return code;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public Integer getKey() {
			return Integer.parseInt(code);
		}
	}
	
	/**
	 * 芝麻分
	 * */
	public enum ZHIMA_FEN implements ICommonEnum{
		Zhimafen_620("620","620芝麻分");
		
	    private final String code;
	    private final String name;
	    
	    private ZHIMA_FEN( String code,String name) {
	        this.code = code;
	        this.name = name;
	    }
		@Override
		public String getCode() {
			return code;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public Integer getKey() {
			return Integer.parseInt(code);
		}
	}
	
	/**状态*/
	public enum Status implements ICommonEnum{
		/**非正常-作废*/
		UN_NORMAL("-1","非正常"),
		/**正常*/
		NORMAL("1","正常");
	    private final String code;
	    private final String name;
	    
	    private Status( String code,String name) {
	        this.code = code;
	        this.name = name;
	    }
		@Override
		public String getCode() {
			return code;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public Integer getKey() {
			return Integer.parseInt(code);
		}
	}
	
	/**
	 * 额度提升阶段
	 * */
	public enum CreditsLeve implements ICommonEnum{
		
		LEVE_1("1","第一阶段",200,3),
		LEVE_2("2","第二阶段",200,5),
		LEVE_3("3","第三阶段",100,6);
		
		/**额度等级*/
	    private final String code;
	    
	    /**说明*/
	    private final String name;
	    
	    /**提升的额度(金额)*/
	    private final Integer credits;
	    
	    /**借款次数*/
	    private final Integer borNumber;
	    
	    /**
	     * @param code 额度等级
	     * @param name 说明
	     * @param credits 提升的额度
	     * @param borNumber 借款次数
	     * */
	    private CreditsLeve( String code,String name,Integer credits,Integer borNumber) {
	        this.code = code;
	        this.name = name;
	        this.credits=credits;
	        this.borNumber=borNumber;
	    }
		@Override
		public String getCode() {
			return code;
		}
		@Override
		public String getName() {
			return name;
		}
		@Override
		public Integer getKey() {
			return Integer.parseInt(code);
		}
		public Integer getCredits() {
			return credits;
		}
		public Integer getBorNumber() {
			return borNumber;
		}
		
	}
	
	/**
	 * <p>根据等级获取临时额度</p>
	 * @param creditsLeve 级别
	 * */
	public static Double getTmpCredits(Integer creditsLeve){
		if(creditsLeve.intValue()== CreditsLeve.LEVE_1.getKey().intValue()){
			return Double.parseDouble(CreditsLeve.LEVE_1.getCredits().toString());
		}
		int credit1= CreditsLeve.LEVE_1.getCredits().intValue();
		int credit2= CreditsLeve.LEVE_2.getCredits().intValue();
		int credit3= CreditsLeve.LEVE_3.getCredits().intValue();
		if(creditsLeve.intValue()== CreditsLeve.LEVE_2.getKey().intValue()){
			int sum=credit2+credit1;
			return Double.parseDouble(sum+"");
		}else if(creditsLeve.intValue()== CreditsLeve.LEVE_3.getKey().intValue()){
			int sum=credit2+credit1+credit3;
			return Double.parseDouble(sum+"");
		}
		return Double.parseDouble("0");
	}
	
	/**
	 * <p>根据当前阶段级别、统计用户的总临时额度(不会计算已使用临时额度)+信用额度初始值</p>
	 * @param creditsLeve 用户当前提额阶段
	 * @return Double
	 * */
	public static Double getUserTmpCredits(Integer creditsLeve){
		String borrowCreditStr=Global.getValue(CreditsUpgradeEnum.BORROW_CREDIT);
		if(StringUtils.isBlank(borrowCreditStr))borrowCreditStr="1000";//默认借款额度
		String[] credits = borrowCreditStr.split(",");
		double minCredit=Double.parseDouble(credits[0]);//最小借款额度
		double tmpCredits=getTmpCredits(creditsLeve);
		return minCredit+tmpCredits;
	}
	
	/**
	 * <p>根据给定临时额度获取对应的提额值</p>
	 * @param credits 额度
	 * @returnDouble
	 * */
	public static Double getTmpCredits(double credits){
		CreditsLeve[] creditsLeves= CreditsLeve.values();
		for(int i=0;i<creditsLeves.length;i++){
			CreditsLeve leveObj= creditsLeves[i];
			if(credits==Double.parseDouble(leveObj.getCredits().toString())){
				return Double.parseDouble(leveObj.getCredits().toString());
			}
		}
		return Double.parseDouble("0");
	}
	
	/**
	 * <p>计算用户总额度</p>
	 * @param creditsLeve 级别
	 * @param curCredit 当前用户信用额度
	 * */
	public static Double getTmpCredits(Integer creditsLeve,Double curCredit){
		Double tmpCredit=getTmpCredits(creditsLeve);
		return tmpCredit.doubleValue()+curCredit.doubleValue();
	}
	
	/**
	 * 获取总临时额度总和(各阶段临时额度总和)
	 * */
	public static Double getTotalTmpCredits(){
		CreditsLeve[] creditsLeves= CreditsLeve.values();
		Double total=new Double(0);
		for(int i=0;i<creditsLeves.length;i++){
			CreditsLeve leveObj= creditsLeves[i];
			total+=Double.parseDouble(leveObj.getCredits().toString());
		}
		return total;
	}
	
	/**
	 * <p>校验额度是否正确</p>
	 * @param credits 额度
	 * @return  boolean
	 * */
	public static boolean checkCredits(Double credits){
		Boolean flag=Boolean.FALSE;
		CreditsLeve[] creditsLeves= CreditsLeve.values();
		for(int i=0;i<creditsLeves.length;i++){
			CreditsLeve leveObj= creditsLeves[i];
			Integer credit=leveObj.credits;
			if(Double.parseDouble(credit.toString())==credits.doubleValue()){
				return Boolean.TRUE;
			}
		}
		if(credits.doubleValue()<=getTotalTmpCredits().doubleValue()){
			return Boolean.TRUE;
		}
		return flag;
	}
	
	/**
	 * <p>校验额度级别是否正确</p>
	 * @param creditsLeve 额度级别
	 * @return  boolean
	 * */
	public static boolean checkCreditsLeve(Integer creditsLeve){
		Boolean flag=Boolean.FALSE;
		CreditsLeve[] creditsLeves= CreditsLeve.values();
		for(int i=0;i<creditsLeves.length;i++){
			CreditsLeve leveObj= creditsLeves[i];
			Integer cLeve=leveObj.getKey();
			if(cLeve.intValue()==creditsLeve.intValue()){
				return Boolean.TRUE;
			}
		}
		return flag;
	}
	
	/**
	 * 额度提升总开关
	 * @return boolean true:开启、false:关闭
	 * */
	public static boolean isCreditsSwitchOn(){
		String isOn=Global.getValue(CREDITS_SWITCH);
		Boolean flag=Boolean.FALSE;
		if(StringUtils.isBlank(isOn)) return flag;
		if(isOn.equals(SwitchEnum.ON.getCode())){
			flag=Boolean.TRUE;
		}
		return flag;
	}
	
	/**
	 * <p>查询最低可借款额度</p>
	 * */
	public static Double getMinCredit(){
		String borrowCreditStr=Global.getValue(CreditsUpgradeEnum.BORROW_CREDIT);
		if(StringUtils.isBlank(borrowCreditStr))borrowCreditStr="1000";//默认借款额度
		double minCredit=0;
		try {
			String[] credits = borrowCreditStr.split(",");
			minCredit=Double.parseDouble(credits[0]);//最小借款额度
		} catch (Exception e) {
			//保险起见
			minCredit=1000;
		}
		return minCredit;
	}
	
	/**
	 * <p>如果借款额度大于最低可借款额度，则取最低借款额度</p>
	 * @param amount 借款额度
	 * @return Double 
	 * */
	public static Double getDefaultCredit(Double amount){
		double minCredit=getMinCredit();//最小借款额度
		if(amount>minCredit){
			return minCredit;
		}
		return amount;
	}
	
}
