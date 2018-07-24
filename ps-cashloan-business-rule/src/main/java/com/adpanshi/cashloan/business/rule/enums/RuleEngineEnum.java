package com.adpanshi.cashloan.business.rule.enums;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @Description : manageuser Enum
 * @author huangqin
 * @version 1.1
 * @Date 2017-12-28
 *
 */
public class RuleEngineEnum {

	/**
	 * 适用场景
	 * */
	public enum ADAPTED_STATE{
		ADAPTED_TC_BEFORE_LOAN			("9","天创贷前"),
		ADAPTED_TD_BEFORE_LOAN			("10","同盾贷前"),
		ADAPTED_USER_INFO				("11","个人信息"),
		ADAPTED_AFTER					("12","运营商"),
		ADAPTED_EMER_CONTACT			("13","紧急联系人"),
		ADAPTED_ZHIMA_CREDIT			("14","芝麻信用"),
		ADAPTED_TD						("15","同盾"),
		ADAPTED_NEW_TD					("16","新同盾");

		private String EnumKey;
		private String EnumValue;
		ADAPTED_STATE(String EnumKey, String EnumValue){
			this.EnumKey = EnumKey;
			this.EnumValue = EnumValue;
		}
		public String EnumKey(){
			return EnumKey;
		}

		public String EnumValue(){
			return EnumValue;
		}

		public static Map<String,Object> EnumValueS(){
			Map<String,Object> outMap = new TreeMap<>();
			ADAPTED_STATE[] enums = values();
			for(ADAPTED_STATE e:enums){
				outMap.put(e.EnumKey,e.EnumValue);
			}
			return outMap;
		}

		public static ADAPTED_STATE getByEnumKey(String EnumKey){
			ADAPTED_STATE[] enums = values();
			for(ADAPTED_STATE e:enums){
				if(e.EnumKey.equals(EnumKey)){
					return e;
				}
			}
			return null;
		}
	}

	/**
	 * 规则状态
	 * */
	public enum RULE_STATE{
		ADAPTED_BEFORE               				("10","启用"),
		ADAPTED_AFTER               				("20","禁用");
		private String EnumKey;
		private String EnumValue;
		RULE_STATE(String EnumKey, String EnumValue){
			this.EnumKey = EnumKey;
			this.EnumValue = EnumValue;
		}
		public String EnumKey(){
			return EnumKey;
		}

		public String EnumValue(){
			return EnumValue;
		}

		public static Map<String,Object> EnumValueS(){
			Map<String,Object> outMap = new TreeMap<>();
			RULE_STATE[] enums = values();
			for(RULE_STATE e:enums){
				outMap.put(e.EnumKey,e.EnumValue);
			}
			return outMap;
		}

		public static RULE_STATE getByEnumKey(String EnumKey){
			RULE_STATE[] enums = values();
			for(RULE_STATE e:enums){
				if(e.EnumKey.equals(EnumKey)){
					return e;
				}
			}
			return null;
		}
	}
}

