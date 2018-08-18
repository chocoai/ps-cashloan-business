package com.adpanshi.cashloan.cl.extra;

import java.util.HashMap;
import java.util.Map;

/***
 ** @category 用做排序的扩展...
 ** @author qing.yunhui
 ** @email: 280672161@qq.com
 ** @createTime: 2017年12月22日下午10:07:07
 **/
public class OrderByExtra {
	
	private static OrderByExtra orderByExtra;
	
	/**
	 * 排序字段key 
	 * */
	private final String orderColumn="orderColumn";
	
	/**
	 * 排序-升序or降序
	 * */
	private final String orderDirection="orderDirection";
	
	private final String ASC="ASC";
	
	private final String DESC="DESC";
	
	public static OrderByExtra getInstance(){
    	if(orderByExtra == null){
    		synchronized (OrderByExtra.class) {
				if(orderByExtra == null) {orderByExtra = new OrderByExtra();}
			}
    	}
    	return orderByExtra;
    }
	
	/**
	 * <p>根据给定字段进行升序排序</p>
	 * @param field
	 * @return 
	 * */
	public Map<String,Object> orderByDESC(String field){
		return orderBy(field, DESC);
	}
	
	/**
	 * <p>根据给定字段进行升序排序</p>
	 * @param field
	 * @return 
	 * */
	public Map<String,Object> orderByASC(String field){
		return orderBy(field, ASC);
	}
	
	/**
	 * <p>根据给定字段排序方向进行排序</p>
	 * @param field 待排序的字段
	 * @param order 排序方向
	 * @return Map
	 * */
	 private Map<String,Object> orderBy(String field,String order){
		Map<String,Object> map=new HashMap<>();
		map.put(orderColumn, field);
		map.put(orderDirection, order);
		return map;
	}

}
