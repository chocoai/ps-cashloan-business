package com.adpanshi.cashloan.business.cl.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 请用一句话来描述其用途...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月4日下午3:03:19
 **/
public class PettyloanSceneEnum {
	
	/**户型-字典表关联*/
	public static final String HOUSE_TYPE="HOUSE_TYPE";
	
	/**产权性质-字典关联*/
	public static final String NATURE_PROPERTY="NATURE_PROPERTY";
	
	/**押金方式-字典关联*/
	public static final String DEPOSIT_TYPE="DEPOSIT_TYPE";
	
	/**自定义状态-仅限于用来标识场景提交-(0:新增、1.重新提交、2.不可重复提交)*/
	public enum DEFINED_STATE implements ICommonEnum{
		ADD(0,"新增"),
		RESUBMISSION(1,"重新提交"),
		QUERY(2,"仅限查看");
		
		private Integer key;
		private String name;
		
		private DEFINED_STATE(Integer key, String name){
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
	
	/**场景类型-(2.租房贷款、4.自有房产、5.个体工商上传、6.消费贷款、7.住房家装)*/
	public enum SCENE_TYPE implements ICommonEnum{
		HOUSES_DETAIL(2,"租房贷款","scene_housesDetail"),
		SELF_HOUSES(4,"自有房产","scene_selfhouses"),
		PERSON(5,"个体工商","scene_person"),
		BILL(6,"消费贷款","scene_bill"),
		HOME(7,"住房家装","scene_home"),
		OTHER(100,"其他场景","scene_other");

		private Integer key;
		private String name;
		//对应arc_sys_config-每个场景对应每一个h5页面
		private String code;
		
		private SCENE_TYPE(Integer key, String name,String code){
			this.key = key;
			this.name = name;
			this.code=code;
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
			return key;
		}

	}
}
