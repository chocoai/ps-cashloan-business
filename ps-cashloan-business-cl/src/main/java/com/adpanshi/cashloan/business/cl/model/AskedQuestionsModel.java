package com.adpanshi.cashloan.business.cl.model;
/***
 ** @category 常见问题...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年4月4日上午10:18:54
 **/
public class AskedQuestionsModel {
	
	public AskedQuestionsModel(){}
	
	public AskedQuestionsModel(String name,String url){
		this.name=name;
		this.url=url;
	}
	
	private String name;
	
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
