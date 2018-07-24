package com.adpanshi.cashloan.business.core.common.enums;

/***
 ** @category 附件枚举类...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月3日下午9:30:53
 **/
public class SysAttachmentEnum {

	
	/**分类*/
	public enum CLASSIFY implements ICommonEnum{
		ENJOYSIGN(1,"1号签","enjoysign_pdf_dir"),
		//------------------有且有可能产生多个子类---------------------//
		LEASING_CONTRACT(2,"租房贷款","scene_housesDetail_dir"),
		PRIVATE_PROPERTY(4,"自有房产","scene_selfhouses_dir"),
		INDIVIDUAL_BUSINESS(5,"个体工商","scene_person_dir"),
		CONSUMER_LOANS(6,"消费贷款","scene_bill_dir"),
		HOME_IMPROVEMENT(7,"住房家装","scene_home_dir"),
		OTHER_SCENE(100,"其他场景","scene_other");

		private Integer key;
		private String name;
		/**文件存放目录(对应sys_config中的code)*/
		private String fileDirCode;
		
		private CLASSIFY(Integer key,String name,String fileDirCode){
			this.key=key;
			this.name=name;
			this.fileDirCode=fileDirCode;
		}

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

		public String getFileDirCode() {
			return fileDirCode;
		}
	}
	
	/**
	 * 子分类
	 * */
	public enum SUB_CLASSIFY implements ICommonEnum{
//		'1', '租房贷款', 
//		'4', '自有房产', 
//		'5', '个体工商周转
//		'6', '消费贷款', 
//		'7', '住房家装', 
//		####附件分类 (修改后)
//		分类(1.1号签、2.租房贷款、4.自有房产、5.个体工商、6.消费贷款、7.住房家装)
		
//		`scene_type` int(11) NOT NULL COMMENT '(2.租房贷款、4.自有房产、5.个体工商上传、6.消费贷款、7.住房家装)',
		
//		子分类(20.租房合同、21.收入证明 ,40.租房合同、41.收入证明, 50.营业执照、51.税务登记证、52.卫生许可证、53.烟草专卖许可证 , 70.购房合同或购房证明、71.水电开户单据或门牌号、装修合同)
//		'租房贷款', 'scene_housesDetail_dir', '/data/image/scene/housesDetail/'
//		'自有房产', 'scene_selfhouses_dir', '/data/image/scene/selfhouses/', '1
//		'个体工商', 'scene_person_dir', '/data/image/scene/person/', '1', '个体
//		'消费贷款', 'scene_bill_dir', '/data/image/scene/bill/', '1', '消费贷款
//		'住房家装', 'scene_home_dir', '/data/image/scene/home/', '1', '住房家装
		
		//--------对应租房贷款--------
		HOUSES_AGREEMENT(20,"租房贷款->租房合同"),
		HOUSES_PROOF_OF_EARNINGS(21,"租房贷款->收入证明"),
		//--------对应自有房产--------
		SELFHOUSES_AGREEMENT(40,"自有房产->租房合同"),
		SELFHOUSES_PROOF_OF_EARNINGS(41,"自有房产->收入证明"),
		//--------对应个体工商--------
		BUSINESS_LICENSE(50,"营业执照"),
		TAX_REGISTER_PROVE(51,"税务登记证"),
		HEALTH_PERMIT(52,"卫生许可证"),
		TOBACCO_MONOPOLY_PERMIT(53,"烟草专卖许可证"),
		//--------对应住房家装--------
		//购房合同或购房证明、水电开户单据或门牌号、装修合同
		BUY_HOUSE_CONTRACT_PROVE(70,"购房合同或购房证明"),
		HydroBill_HouseNumber_DecorationContract(71,"水电开户单据或门牌号、装修合同");
		
		private Integer key;
		private String name;
		
		private SUB_CLASSIFY(Integer key,String name){
			this.key=key;
			this.name=name;
		}

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
