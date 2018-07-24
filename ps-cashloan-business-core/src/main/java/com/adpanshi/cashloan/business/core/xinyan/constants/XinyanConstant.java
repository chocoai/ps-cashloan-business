package com.adpanshi.cashloan.business.core.xinyan.constants;

/***
 ** @category 新颜用到的常量...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日下午12:14:02
 **/
public class XinyanConstant {

	/**总开关[sys_config.code]*/
	public static final String SWITCH_CODE="xinyan_switch";
	
	/**规则开关[sys_config.code]*/
	public static final String RULE_SWITCH="xinyan_rule_switch";
	
	/**订单号前缀*/
	public static final String PREFIX="X";
	
	/** ------------------------------------>编码  ------------------------------------>*/
	
	
	public final static String KEY_X509 = "X509";
	
	public final static String KEY_PKCS12 = "PKCS12";
	
	public final static String KEY_ALGORITHM = "RSA";
	
	public final static String RSA_CHIPER = "RSA/ECB/PKCS1Padding";

	public final static int KEY_SIZE = 1024;
	/** 1024bit 加密块 大小 */
	public final static int ENCRYPT_KEYSIZE = 117;
	/** 1024bit 解密块 大小 */
	public final static int DECRYPT_KEYSIZE = 128;
	
}
