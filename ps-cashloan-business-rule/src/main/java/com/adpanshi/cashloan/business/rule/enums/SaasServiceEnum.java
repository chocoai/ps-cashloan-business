package com.adpanshi.cashloan.business.rule.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月3日下午2:54:23
 **/
public class SaasServiceEnum {

	/**渠道*/
	public enum CHANNEL implements ICommonEnum{
		/**渠道(10:天创 、20:SAAS) */
		TIAN_CHUANG (10,"天创"),
		/**SAAS*/
		SAAS(20,"SAAS");
		
		private Integer key;
		private String name;
		private CHANNEL(Integer key, String name){
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
	
	/**爬取状态*/
	public enum STATE implements ICommonEnum{
		/**爬取状态(10:已提交申请、20:爬取成功、 30:爬取失败) */
		SUBMIT (10,"已提交申请"),
		SUCCESS(20,"爬取成功"),
		FAIL(30,"爬取失败");
		
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
	
	/**接口类型*/
	public enum TYPE implements ICommonEnum{
		/**接口类型(1:社保、2:公积金) */
		SHE_BAO (1,"社保","Shebao"),
		GONG_JI_JIN(2,"公积金","Gongjijin");
		
		
		private Integer key;
		private String name;
		private String url;//非必填
		private TYPE(Integer key, String name,String url){
			this.key = key;
			this.name = name;
			this.url=url;
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
		public String getUrl() {
			return url;
		}
	}
	
}
