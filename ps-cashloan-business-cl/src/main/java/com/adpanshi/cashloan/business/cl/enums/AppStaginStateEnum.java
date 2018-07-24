package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 给app端分期进度自定义的状态集...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月27日上午11:18:15
 **/
public class AppStaginStateEnum {

	
	/*   	审核中(12、14、22),审核通过(12,20),放款中(26),审核失败(21、27),已完成(40),还款中(待还款)30,已逾期(50)
  			审核失败(27)、审核中(22)、审核通过(26)、打款中(29)、打款失败(28)、打款成功(30)、还款中(35)、 还款成功(40)、逾期中(50)
	 */
	public enum STATE implements ICommonEnum{
		
		/**审核失败 */
		AUDIT_FAIL(27,"审核失败"),
		/**审核中*/
		UNDER_REVIEW(22,"审核中"),
		/**审核通过*/
		AUDIT_PASS(26,"审核通过"),
		/**打款中*/
		LOANS_IN(29,"打款中"),
		/**打款失败*/
		LOAN_FAILED(28,"打款失败"),
		/**打款成功*/
		LOAN_SUCCESS(30,"打款成功"),
		/**还款中*/
		REPAYMENT(35,"还款中"),
		/**还款成功*/
		REPAYMENT_SUCCESS(40,"还款成功"),
		/**逾期中*/
		DELAY(50,"逾期中");
		
		private Integer key;
		private String name;
		
		private STATE(Integer key, String name){
			this.key = key;
			this.name = name;
		}

		@Override
		public String getCode() {
			return String.valueOf(key);
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Integer getKey() {
			return key;
		}
		
	}
	
}
