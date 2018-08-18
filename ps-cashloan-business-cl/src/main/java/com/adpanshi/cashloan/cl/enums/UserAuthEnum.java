package com.adpanshi.cashloan.cl.enums;

import com.adpanshi.cashloan.core.common.enums.ICommonEnum;

/***
 ** @category 用户认证状态枚举...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月4日上午11:28:42
 **/
public class UserAuthEnum {

	/**认证项配置-[ sys_conig 库中配置的认证项指数]*/
	public static final String AUTH_CONFIGURE="auth_configure";
	
	/**
	 * 可选项-[json串对应的key]{与AUTH_CONFIGURE中的json串匹配}
	 * */
	public static final String OPTIONALS="optionals";
	
	/**必选项-[json串对应的key]{与AUTH_CONFIGURE中的json串匹配}*/
	public static final String REQUIREDS="requireds";
	
	/**隐藏选项-[json串对应的key]{与AUTH_CONFIGURE中的json串匹配}*/
	public static final String HIDDEN="hidden";
	
	/**选择项*/
	public static final String CHOOSE="choose";
	
	/**总认证项-拼接后的-SQL*/
	public static final String TOTAL_AUTH_SQL="total_auth_sql";
	
	public static final String CHOOSE_SQL="choose_sql";
	
	/**必选认证项-拼接后的-SQL*/
	public static final String REQUIRED_SQL="required_sql";
	
	/**隐藏认证项-拼接后的-SQL*/
	public static final String HIDDEN_SQL="hidden_sql";
	
	/**
	 * 认证总数量(为了兼容老的代码)-SQL
	 * */
	public static final String TOTAL_AUTH_QUALIFIED="total_auth_qualified";
	
	/**必选认证数量-SQL*/
	public static final String REQUIRED_QUALIFIED="required_qualified";
	
	/**
	 * app端进入时需要查询用户租房合同状态
	 * */
	public static final String HOUSE_INCOME="house_income";
	
	
	/**认证集*/
	public enum WHAT_AUTH implements ICommonEnum{
		ID_STATE("idState","身份证认证"),
    	ZHIMA_STATE("zhimaState","芝麻授信认证"),
    	PHONE_STATE("phoneState","手机运营商认证"),
    	CONTACT_STATE("contactState","紧急联系人认证"),
    	BANK_CARD_STATE("bankCardState","银行卡认证"),
    	WORK_INFO_STATE("workInfoState","工作信息认证"),
    	OTHER_INFO_STATE("otherInfoState","更多信息认证"),
    	LIVING_IDENTIFY_STATE("livingIdentifyState","活体识别认证"),
    	TONGDUN_STATE("tongdunState","同盾认证"),
    	BANK_CARD_BILL_STATE("bankCardBillState","银行卡账单认证"),
    	TENEMENT_INCOME_STATE("tenementIncomeState","租房收入认证"),
    	GONG_JIJIN_STATE("gongJiJinState","公积金认证"),
    	SHE_BAO_STATE("sheBaoState","社保认证");
		
		 private String code;
		 private String name;
		
		private WHAT_AUTH(String code,String name){
			this.code=code;
			this.name=name;
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
	 * 认证状态(10未认证/未完善,20认证中/完善中,25认证失败,26已过期,30已认证/已完善)
	 * */
	public enum TENEMENT_INCOME_STATE implements ICommonEnum{
		UN_AUTH("10","未认证/未完善"),
		CERTIFICATION("20","认证中/完善中"),
		AUTH_FAIL("25","认证失败"),
		AUTHENTICATED("30","已认证/已完善"),
		AUTH_EXPIRE("40","已过期");
		 private String code;
		 private String name;
		
		private TENEMENT_INCOME_STATE(String code,String name){
			this.code=code;
			this.name=name;
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
}
