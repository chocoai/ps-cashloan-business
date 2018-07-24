package com.adpanshi.cashloan.business.core.xinyan.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 新颜可配置参数枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日上午11:46:04
 **/
public class XinyanConfigEnum {

	/**
	 * 商家-提供的数据
	 * */
	public enum MERCHANTS_DATA implements ICommonEnum{
		/**商户私钥[sys_config.code]*/
		PRIVATE_KEY("xinyan_private_key","商户私钥"),
		
		/**商户私钥密码[sys_config.code]*/
		PRIVATE_KEY_PWD("xinyan_private_key_pwd","商户私钥密码"),
		
		/**终端号[sys_config.code]*/
		TERMINAL_NUMBER("xinyan_terminal_number","终端号"),
		
		/**商户号[sys_config.code]*/
		MERCHANTS("xinyan_merchants","商户号"),
		
		/**服务器地址[sys_config.code]*/
		HOST("xinyan_host","服务器地址"),
		
		/**负面拉黑接口地址[sys_config.code]*/
		BLACK_URL("xinyan_black_url","负面拉黑接口地址");
		
		private String code;
		
		private String name;
		
		private MERCHANTS_DATA(String code, String name){
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
			return null;
		}
	}
}
