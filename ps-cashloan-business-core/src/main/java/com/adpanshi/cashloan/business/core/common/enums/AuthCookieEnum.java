package com.adpanshi.cashloan.business.core.common.enums;

/**
 * 
 * @describe 用户权限Enum
 * @author mujuezhike
 *
 */
public enum AuthCookieEnum {
	
    USER_NAME 				    ("username",  "用户名"),
    USER_ID 						    ("userid",    "权限用户id"),
    TOKEN                             ("token",     "token"),//实际权限判断需要
	
    
    M_USER_NAME 					 ("musername",  "后台用户名"),
    M_USER_ID 						     ("muserid",    "权限用户id"),
    M_TOKEN                             ("mtoken",     "后台token"),//实际权限判断需要
    M_MANAGER_USER_ID 	     ("muserid",    "后台管理员id"),
    M_MANAGER_NAME          ("musername",  "后台显示名"),
    
    S_USER_NAME 					("susername",  "卖家用户名"),
    S_USER_ID 						    ("suserid",    "权限用户id"),
    S_TOKEN                             ("stoken",     "卖家token"),//实际权限判断需要
    S_SELLER_USER_ID 				("suserid",    "卖家卖家id"),
    S_MANAGER_NAME           ("susername",  "卖家显示名"),
    
	;
	
	
	/**
	 * cookie key
	 */
	private String cookieKey;
	
	/**
	 * 描述信息
	 */
	private String describe;
	
	AuthCookieEnum(String cookieKey, String describe){
		this.cookieKey = cookieKey;
		this.describe = describe;
	}
	
	public String cookieKey(){
		return cookieKey;
	}
	
	public String describe(){
		return describe;
	}
	

}
