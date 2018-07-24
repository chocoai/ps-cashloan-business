package com.adpanshi.cashloan.business.core.enjoysign.enums;

import com.adpanshi.cashloan.business.core.common.enums.ICommonEnum;

/***
 ** @category 1号签的一些配置信息...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午8:11:23
 **/
public enum EnjoysignConfigEnum implements ICommonEnum{
	
	/**1号签的code(对应sys_config)、name(对应1号签)*/
	APPID("enjoysign_appId","appId"),
	/**1号签模板ID(对应sys_config)、name(对应1号签)*/
	TEMPLATE_ID("enjoysign_template_id","templateId"),
	/**1号签版本号(对应sys_config)、name(对应1号签)*/
	API_VERSION("enjoysign_api_version","version"),
	
	
	/**1号签Server地址*/
	API_SERVER_URI("enjoysign_api_server_uri","http://open.enjoysign.com/api"),
	/**1号签AppSecret秘钥*/
	APP_SECRET("enjoysign_app_secret","096893a18ec6773f2017f1a35568a36c2bf98d88"),
	/**1号签导出模板签署人签署控件列表接口*/
	EXPORTWIDGETS_URI("enjoysign_exportwidgets_uri","/exportwidgets.shtml"),
	/**生成合同后做自动后台签署*/
	STARTSIGN("enjoysign_startsign","/startsign.shtml"),
	/**1号签-模板控件-数据集(甲乙丙三方需要签署的控件都包含在该数据集中)*/
	TEMPLATE_DATA("enjoysign_template_data","1号签-模板控件-数据集"),
	/**下载合同接口*/
	DOWNLOADPDF("enjoysign_downloadpdf","/downloadpdf.shtml"),
	/**在线查看合同接口**/
	VIEW_URI("view_uri","/view.shtml");
	
	
	private String code;
	
	private String name;
	
	/**
	 * code(对应sys_config)、name(对应1号签)
	 * @param code 对应 sys_config
	 * @param name 对应 1号签
	 * */
	private EnjoysignConfigEnum(String code,String name){
		this.code=code;
		this.name=name;
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
