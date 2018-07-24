package com.adpanshi.cashloan.business.core.umeng.beans;

/***
 ** @category 数据结构 @1...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月30日下午6:08:58
 **/
public class DataStruct {
	
	private String appkey;          // 必填 应用唯一标识
	private String timestamp;       // 必填 时间戳，10位或者13位均可，时间戳有效期为10分钟
	
	/**
	 * <p> 参考:UmEnum.Type </p>
	 * 消息发送类型,其值可以为:
          unicast-单播
          listcast-列播(要求不超过500个device_token)
          filecast-文件播 (多个device_token可通过文件形式批量发送）
          broadcast-广播
          groupcast-组播(按照filter条件筛选特定用户群, 具体请参照filter参数)
          customizedcast(通过开发者自有的alias进行推送)包括两种case(alias:对单个或者多个alias进行推送,file_id:将alias存放到文件后,根据file_id来推送)
	 * **/
	private String type;             
	
	/**
	 * 必填 消息内容(Android最大为1840B), 包含参数说明如下(JSON格式):
	 * */
	private Payload payload;		
	
	/**
	 * 可选 设备唯一表示
                  当type=unicast时,必填, 表示指定的单个设备
                  当type=listcast时,必填,要求不超过500个,
                  多个device_token以英文逗号间隔
	 * */
	private String device_tokens;
	
	/**
	 * 可选  : 正式/测试模式
	 * */
	private Boolean production_mode;
	
	/**可选 发送消息描述，建议填写。*/
	private String description;     
	
	/**可选(发送策略)，当policy为空时，消息有效期默认为3天*/
	private Policy policy;
	
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public String getDevice_tokens() {
		return device_tokens;
	}

	public void setDevice_tokens(String device_tokens) {
		this.device_tokens = device_tokens;
	}

	public Boolean getProduction_mode() {
		return production_mode;
	}

	public void setProduction_mode(Boolean production_mode) {
		this.production_mode = production_mode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
	
}
