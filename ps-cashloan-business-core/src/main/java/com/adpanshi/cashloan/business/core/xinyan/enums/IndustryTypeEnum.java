package com.adpanshi.cashloan.business.core.xinyan.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 行业类别...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年10月21日上午10:01:18
 **/
public class IndustryTypeEnum {
	
	/**互联网，游戏，软件*/
	public enum INTERNET_GAMES_SOFTWARE implements ICommonEnum{
		A1("A1","通讯、社交、社区类产品"),
		A2("A2","电商产品"),
		A3("A3","本地服务、O2O类产品"),
		A4("A4","信息、资讯产品"),
		A5("A5","文化、娱乐产品"),
		A6("A6","安全、工具类产品"),
		A7("A7","互联网+"),
		A8("A8","游戏"),
		A9("A9","To B 产品"),
		A10("A10","智能硬件"),
		A11("A11","VR/AR 产品"),
		A12("A12","IT服务，系统集成"),
		A13("A13","计算机软件"),
		A14("A14","其他件");
		private String code;
		
		private String name;
		
		private INTERNET_GAMES_SOFTWARE(String code, String name){
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
	
	/**互联网金融*/
	public enum INTERNET_FINANCE implements ICommonEnum{

		B1("B1","配资"),
		B2("B2","理财"),
		B3("B3","黄金白银交易"),
		B4("B4","众筹"),
		B5("B5","有偿资讯"),
		B6("B6","基金交易"),
		B7("B7","外汇"),
		B8("B8","期货"),
		B9("B9","信托"),
		B10("B10","拍卖公司"),
		B11("B11","证券交易"),
		B12("B12","二元期权"),
		B13("B13","证券/基金公司推出的服务类产品"),
		B14("B14","金融软件"),
		B15("B15","积分通道"),
		B16("B16","票据"),
		B17("B17","货币代兑"),
		B18("B18","新金融"),
		B19("B19","P2P网贷"),
		B20("B20","第三方支付"),
		B21("B21","数字货币"),
		B22("B22","大数据金融"),
		B23("B23","信息化金融机构"),
		B24("B24","金融门户"),
		B25("B25","其他互联网金融类");
		
		private String code;
		private String name;
		
		private INTERNET_FINANCE(String code, String name){
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
	
	/**消费金融*/
	public enum CONSUMER_FINANCE implements ICommonEnum{
		
		C1("C1","综合消费金融"),
		C2("C2","旅游消费金融"),
		C3("C3","电商消费金融"),
		C4("C4","医疗消费金融"),
		C5("C5","教育消费金融"),
		C6("C6","农村消费金融"),
		C7("C7","房产消费金融"),
		C8("C8","汽车消费金融"),
		C9("C9","互联网小贷"),
		C10("10","大学生分期"),
		C11("C11","医疗分期"),
		C12("C12","租房分期"),
		C13("C12","教育分期"),
		C14("C14","其他消费金融类");
		
		private String code;
		private String name;
		
		private CONSUMER_FINANCE(String code, String name){
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

	/**其它*/
	public enum OTHER implements ICommonEnum{
		D1("D1","电子，通信，硬件"),
		D2("D2","汽车、机械、制造"),
		D3("D3","交通、贸易、物流"),
		D4("D4","制药、医疗"),
		D5("D5","能源化工环保"),
		D6("D6","农林牧渔"),
		D7("D7","消费品"),
		D8("D8","广告、传媒、教育、文化"),
		D9("D9","房地产、建筑、物业"),
		D10("D10","电子，通信，硬件"),
		D11("D11","服务、中介、外包"),
		D12("D12","金融"),
		D13("D13","其他");
		
		private String code;
		private String name;
		
		private OTHER(String code, String name){
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
