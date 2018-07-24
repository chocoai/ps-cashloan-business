package com.adpanshi.cashloan.business.core.umeng.beans;

import java.util.HashMap;

/***
 ** @category 请用一句话来描述其用途...
 *
 *<p>可选  : 正式/测试模式</p>
 *<p>测试模式下，只会将消息发给测试设备。</p>
 *<p>测试设备需要到web上添加。</p>
 *   	<p>Android:测试设备属于正式设备的一个子集。</p>
 *    	<p>iOS:测试模式对应APNs的开发环境(sandbox) 正式模式对应APNs的正式环境(prod), 正式、测试设备完全隔离。</p>
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年8月31日下午8:27:01
 **/
@SuppressWarnings("serial")
public class ProductionMode extends HashMap<String,Boolean>{
	public ProductionMode(Boolean value){
		super.put("production_mode",value);
	}
}
