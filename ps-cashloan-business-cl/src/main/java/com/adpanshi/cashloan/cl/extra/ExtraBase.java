package com.adpanshi.cashloan.cl.extra;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.util.StringUtil;

import java.util.LinkedHashMap;

/***
 ** @category 用来更新json串、有序存储且进行追加...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月3日下午18:07:20
 **/
@SuppressWarnings("serial")
public abstract class ExtraBase extends LinkedHashMap<String, Object> implements IExtraBase{

	public ExtraBase(){}
	
	/**
	 * <p>新增</p>
	 * <p>根据给定的数据进行转换并添加至默认根节点中</p>
	 * @param extraData 待添加的数据
	 * @return void
	 * */
	public void addData(ExtraData extraData){
		if(null==extraData) return ;
		JSONArray array=new JSONArray();
		array.add(extraData);
		super.put(getRoot(), array);
	}
	
	/**
	 * <p>更新</p>
	 * @param extraData 待追加的数据
	 * @param infoJson  待更新的数据
	 * @return extraData
	 * */
	public void updateData(ExtraData extraData,String infoJson){
		if(StringUtil.isEmpty(extraData,infoJson)) return ;
		JSONArray array=getJSONArray(infoJson);
		if(null!=array){
			array.add(extraData);
		}else{
			array =new JSONArray();
			array.add(extraData);
		}
		super.put(getRoot(), array);
	}
	
	/**
	 * <p>转换成json串</p>
	 * */
	public String toJSON(){
		return JSONObject.toJSONString(this);
	}
	
	/**
	 * <p>json串处理</p>
	 * @param infoJson 待处理的json串
	 * @return  JSONArray
	 * */
	private JSONArray getJSONArray(String infoJson){
		JSONObject jsonObj= JSONObject.parseObject(infoJson);
		if(StringUtil.isEmpty(jsonObj)){
			return null;
		}
		String text=jsonObj.getString(getRoot());
		return JSONObject.parseArray(text);
	}
}
