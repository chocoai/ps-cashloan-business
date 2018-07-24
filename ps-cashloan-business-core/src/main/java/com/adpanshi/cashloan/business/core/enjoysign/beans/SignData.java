package com.adpanshi.cashloan.business.core.enjoysign.beans;
/***
 ** @category 待签章的数据(变量)...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月25日下午7:38:54
 **/
public class SignData {

	/**待签章的标签id(下标从0开始)*/
	private String id; 
	
	/**待签章的标签的key(上传合同后、已选定标签且拖定到合同上的标签、key可以自定义但必须保证唯一)*/
    private String key;
    
    /**待签章的标签的type(上传合同后、已选定标签且拖定到合同上的标签、每个标签都有对应的类型)*/
    private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
