package com.adpanshi.cashloan.business.api.util;

public enum  AIndexExceptionEnum {
	
	RESPONSE_SUCCESS_CODE    (0000, "交易成功！"),
	SUCCEED_CODE_VALUE     (200, "成功 插入 、删除 更新 修改！"), 
	FAIL_CODE_PARAM_INSUFFICIENT (300, "参数列表不完整"),
	FAIL_CODE_VALUE  (400, "失败 插入 、删除 更新 修改"), 
	PERM_CODE_VALUE (403, "无权限访问"),
	OTHER_CODE_VALUE (500, "其他异常"),
	PARAMETER_CHECKING_CODE (700, "参数校验不通过"),
	TIMEOUT_CODE_VALUE (999, "请求超时"),
	RESPONSE_FAIL_CODE    (9999, "交易失败！"),

	;
	
	/**
	 * 错误码
	 */
	private Integer code;
	
	/**
	 * 错误信息
	 */
	private String msg;
	
	AIndexExceptionEnum(Integer code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Integer Code(){
		return code;
	}
	
	public String Msg(){
		return msg;
	}
	
	/** @20170213  根据errorCode 获取enum对象  **/
	public static AIndexExceptionEnum getByCode(Integer code){
		AIndexExceptionEnum[] enums = values();
		for(AIndexExceptionEnum e:enums){
			if(e.code.equals(code)){
				return e;
			}
		}
 		return null;
	}
	
}
