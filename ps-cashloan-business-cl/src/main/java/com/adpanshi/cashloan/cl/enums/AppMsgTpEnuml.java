package com.adpanshi.cashloan.cl.enums;
/***
 ** @category 消息推送-专用...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月4日下午5:39:03
 **/
public class AppMsgTpEnuml {
	
	/**对应模板中的字段*/
	public enum FIELD{
		NAME("{$name}"),
		TIME("{$time}"),
		PHONE("{$phone}"),
		LOAN("{$loan}"),
		ORDERNO("{$orderNo}"),
		/**提升的额度*/
		CREDITS("{$credits}"),
		/**额度有效期*/
		VALID_PERIOD("{$validPeriod}"),
		/** 还款期数（范围）*/
		PERIODS("{$periods}");
		
		private String code;
		
		private FIELD(String code){
			this.code=code;
		}

		public String getCode() {
			return code;
		}
	}
	
	public enum TYPE{
		REGISTER(1,"注册验证码"),
		FIND_REG(2,"找回登陆密码"),
		BIND_CARD(3,"绑卡验证码"),
		FIND_PAY(4,"找回交易密码"),
		OVERDUE(5,"逾期催收"),
		LOAN_INFORM(6,"放款通知"),
		REPAY_INFORM(7,"还款后通知"),
		REFUSE(8,"审核不通过"),
		REPAY_BEFORE(9,"还款前通知"),
		ACTIVE_PAYMENT(10,"立即还款通知"),
		CREDITS_UPGRADE(11,"老用户提额");
		
		private Integer code;
		
		private String name;
		
		private  TYPE(Integer code,String name){
			this.code=code;
			this.name=name;
		}

		public Integer getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}
	
	public enum STATE{
		DISABLE(0,"禁用"),
		ENABLE(1,"启用");
		
		private Integer code;
		
		private String name;
		
		private  STATE(Integer code,String name){
			this.code=code;
			this.name=name;
		}

		public Integer getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}

}
