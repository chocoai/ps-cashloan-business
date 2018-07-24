package com.adpanshi.cashloan.business.core.common.enums;
/***
 ** @category 要分表的表、表名限制枚举...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月25日下午4:48:27
 **/
public class TableDataEnum {
	
	/** sys_config >>> cl_operator_td_call_record  最大userId  */
	public static final String USERID_CODE="userId_code";
	
	public enum TABLE_DATA implements ICommonEnum{

		/**同盾运营商通话记录具体详细*/
		CL_OPERATOR_TD_CALL_RECORD("cl_operator_td_call_record","同盾运营商通话记录具体详细",256),
		
		/**同盾运营商短信记录具体记录*/
		CL_OPERATOR_TD_SMS_RECORD("cl_operator_td_sms_record","同盾运营商短信记录具体记录",256),

		/**用户已安装的 app 应用*/
		CL_USER_APPS("cl_user_apps","用户已安装的 app 应用",128),
		
		CL_USER_BANK_BILL("cl_user_bank_bill", "",256);


		/**表名*/
	    private final String code;
	    /**表别名*/
	    private final String name;
	    /**最大序号(表的最大序号，比如要把test表分成test1-test100，那么这里的max就是100)*/
	    private final long max;
	    
	    private TABLE_DATA( String code,String name,int max) {
	        this.code = code;
	        this.name = name;
	        this.max=max;
	    }
		
		@Override
		public String getCode() {
			return code;
		}

		@Override
		public String getName() {
			return name;
		}
		
		public long getMax() {
			return max;
		}
		
		@Override
		public Integer getKey() {
			return null;
		}
	}

}
