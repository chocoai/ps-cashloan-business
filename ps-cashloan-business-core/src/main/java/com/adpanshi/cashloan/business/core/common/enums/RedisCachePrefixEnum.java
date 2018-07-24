package com.adpanshi.cashloan.business.core.common.enums;

/**
 * Redis缓存前缀
 * @author mujuezhike
 * @date 20160929
 */
public enum RedisCachePrefixEnum {
	
	// "前缀value" "添加对象字段" "描述"
	EntityCache("ENTITY_INSTANCE_","[CLASSNAME]_[ID]","根据对象实例ID存储"),//ENTITY_INSTANCE_EUser_632673243
	
	TokenCache("USER_TOKEN_","[Token]","根据TokenID存储"),//USER_TOKEN_3FD2936E9B64268F07887E53136B2E11:FG
	
	ManagerMenuByRoleCache("MANAGER_MENU_BY_ROLE_","[RoleId]","根据RoleID存储Menu"),

	ManagerHomeInfoCache("MANAGER_HOMEINFO_","[HOMEINFO]","根据模块存储首页信息"),
	
	AuthPermitByUrlRole("AUTH_PERMIT_BY_URL_ROLE_","[URL]_[RoleId]","根据URLRoleID存储权限细节"),
	
	// "前缀value" "添加对象字段" "描述"
	EntityCacheByUser("ENTITY_INSTANCE_BYUSERID_","[CLASSNAME]_[ID]","根据对象实例ID存储"),//ENTITY_INSTANCE_BYUSER_ESellerUser_632673243
	
	;
	
	
	RedisCachePrefixEnum(String prefix, String addition, String describe){
		this.prefix = prefix;
		this.addition = addition;
		this.describe = describe;
	}
    
	private String prefix;
	private String addition;
	private String describe;
	
	public String prefix() {
		return prefix;
	}
	
	public String addition() {
		return addition;
	}
	
	public String describe() {
		return describe;
	}
	

}
