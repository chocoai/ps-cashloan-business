package com.adpanshi.cashloan.business.cl.extra;

import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;

/***
 ** @category 借款意图...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月8日上午9:53:13
 **/
public class BorrowIntent {

	private Long id;
	
	private String itemCode;
	
	private String itemValue;
	
	private String url;

	private Data data;
	
	public BorrowIntent(){}
	
	public BorrowIntent(Long id,String itemCode,String itemValue){
		this.id=id;
		this.itemCode=itemCode;
		this.itemValue=itemValue;
		if(StringUtil.isNotBlank(itemValue)){
			if(isJSON(itemValue)){
				this.data=JSONObject.parseObject(itemValue, Data.class);
				this.itemValue=null;
			}
		}
	}
	
	public BorrowIntent(Long id,String itemCode,String itemValue,String url){
		this(id, itemCode, itemValue);
		this.url=url;
	}
	
	private boolean isJSON(String str){
		try {
			JSONObject.parseObject(itemValue, Data.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data{
		/**标题*/
		private String title ;
		
		/**提示*/
		private String hint;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getHint() {
			return hint;
		}

		public void setHint(String hint) {
			this.hint = hint;
		}
		
	}
	
}
