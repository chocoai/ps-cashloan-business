package com.adpanshi.cashloan.business.system.constant;

public class SystemConstant {

	/*********************** 菜单：是否是菜单 ****************************************/
	/** 不是菜单 */
	public static final byte MENU_NO = 0;
	/** 是菜单 */
	public static final byte MENU_IS = 1;
	/** 所有菜单 */
	public static final byte MENU_ALL = -1;


	/*********************** 用户状态 ****************************************/
	/** 用户状态：正常状态 */
	public static final byte USER_STATUS_NORMAL = 0;


	/*********************** security用户组处理常量类 ****************************************/
	/** system用户登录名 */
	public static final String SYSTEM_LOGIN_NAME = "mlms";
	/** system用户初始化密码 */
	public static final String SYSTEM_PASSWORD_DEFAULT = "123456123456";
	
	/** 默认角色 */
	public static final String ROLE_DEFAULT = "ROLE_DEFAULT";

	/*********************** Token常量 ****************************************/
	/** token刷新概率 */
	public static final double TOKEN_REFLESH_CHANCE = 0.02;
}
