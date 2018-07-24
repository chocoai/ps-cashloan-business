package com.adpanshi.cashloan.business.cl.model.pay.lianlian.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 连连的一些参数配置...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年3月12日上午10:11:02
 **/
public class LianlianParameterEnum {

	/**商品类目-实名类*/
	public enum REAL_NAME_CLASS implements ICommonEnum{
		
		Business_air_ticket(2001,"商旅机票类[航空机票、酒订预订(国内、国际)]"),
		TRAVEL_TICKETING(2002,"旅游票务[旅馆酒店/景区/度假区;汽车票/船票等长途交通票务]"),
		SECURITIES(2003,"证券"),
		FUND(2004,"基金"),
		INSURANCE(2005,"保险"),
		PRECIOUS_METALS_TRADING(2006,"贵金属交易"),
		TECHNOLOGY_DEVELOPMENT_SOFTWARE_SERVICES(2007,"技术开发软件服务[各类软件（学习/办公管理等）销售、开发或服务费]"),
		RECREATION_FITNESS_SERVICES(2008,"娱乐/健身服务[美容/健身类会所、会员服务、套餐；会员制俱乐部/高尔夫球场/休闲会所]"),
		P2P_MICRO_LOAN(2009,"P2P 小额贷款"),
		Microfinance(2010,"小额贷款"),
		CONSUMPTION_PERIOD(2013,"消费分期"),
		OTHER(2999,"其它[其他特殊有实名信息但虚拟类商品或服务]");
		private Integer key;
		private String name;
		
		private REAL_NAME_CLASS(Integer key, String name){
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
