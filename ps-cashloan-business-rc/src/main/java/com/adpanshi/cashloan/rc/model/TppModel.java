package com.adpanshi.cashloan.rc.model;

import com.adpanshi.cashloan.rc.domain.Tpp;
import com.adpanshi.cashloan.rc.domain.TppBusiness;

import java.util.List;

/**
 * 第三方信息model

 * @version 1.0
 * @date 2017年3月14日 下午1:59:17
 * Copyright 粉团网路 arc All Rights Reserved
 *
 *
 *
 */
public class TppModel extends Tpp {

	private static final long serialVersionUID = 1L;

	/**
	 * 接口集合
	 */
	private List<TppBusiness> businessList;
	
	/**
	 * 获取 接口集合
	 * @return 
	 */
	public List<TppBusiness> getBusinessList() {
		return businessList;
	}

	/**
	 * 设置 接口集合
	 * @param 
	 */
	public void setBusinessList(List<TppBusiness> businessList) {
		this.businessList = businessList;
	}
}
