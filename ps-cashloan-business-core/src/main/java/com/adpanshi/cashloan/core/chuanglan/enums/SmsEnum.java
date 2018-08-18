package com.adpanshi.cashloan.core.chuanglan.enums;

import com.adpanshi.cashloan.core.common.enums.ICommonEnum;

import java.util.Map;
import java.util.TreeMap;

/***
 ** @category 创蓝短信.状态枚举类
 ** @author tangqiang
 **/

/**通知状态*/
public enum SmsEnum implements ICommonEnum{

	STATUS_SUCCESS("DELIVRD","短信发送成功"),
	STATUS_UNKOWN("UNKOWN","未知短信状态"),
	STATUS_REJECTD("REJECTD","短信被短信中心拒绝"),
	STATUS_MBBLACK("MBBLACK","目的号码是黑名单号码"),
	STATUS_SM11("SM11","网关验证号码格式错误"),
	STATUS_SM12("SM12","我方验证号码格式错误");

	private final String code;
	private final String name;

	SmsEnum(String code, String name) {
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

	public static Map<String,Object> EnumValueS(){
		Map<String,Object> outMap = new TreeMap<>();
		SmsEnum[] enums = values();
		for(SmsEnum e:enums){
			outMap.put(e.code,e.name);
		}
		return outMap;
	}

	public static SmsEnum getByEnumKey(String EnumKey){
		SmsEnum[] enums = values();
		for(SmsEnum e:enums){
			if(e.code.equals(EnumKey)){
				return e;
			}
		}
		return null;
	}
}



	

