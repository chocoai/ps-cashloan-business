package com.adpanshi.cashloan.core.common.enums;

import java.util.Map;
import java.util.TreeMap;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月25日下午10:48:12
 **/
public class BorrowRepayLogEnum {

	/**还款方式*/
	public enum REPAY_WAY implements ICommonEnum{
		REPAY_WAY_CHARGE("10","代扣"),
		REPAY_WAY_TRANSFER("20","银行卡转账"),
		REPAY_WAY_ALIPAY_TRANSFER("30","支付宝转账"),
		REPAY_WAY_ACTIVE_TRANSFER("40","主动还款");
			
	    private final String code;
	    private final String name;
	    
	    REPAY_WAY( String code,String name) {
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
			REPAY_WAY[] enums = values();
			for(REPAY_WAY e:enums){
				outMap.put(e.code,e.name);
			}
			return outMap;
		}

		public static REPAY_WAY getByEnumKey(String EnumKey){
			REPAY_WAY[] enums = values();
			for(REPAY_WAY e:enums){
				if(e.code.equals(EnumKey)){
					return e;
				}
			}
			return null;
		}
	}
	
}
