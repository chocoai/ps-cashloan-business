package com.adpanshi.cashloan.business.cr.model;

import com.adpanshi.cashloan.business.cr.domain.Info;

import java.util.List;

/** 
 * @author
 * @version 1.0
 * @date 2017-1-9 下午9:05:58
 * Copyright 粉团网路 资产风控系统  All Rights Reserved
 *
 * 
 *
 */
@SuppressWarnings("serial")
public class InfoModel extends Info{

	@SuppressWarnings("rawtypes")
	private List children;

	/**
	 * 获取children
	 * @return children
	 */
	@SuppressWarnings("rawtypes")
	public List getChildren() {
		return children;
	}

	/**
	 * 设置children
	 * @param children
	 */
	@SuppressWarnings("rawtypes")
	public void setChildren(List children) {
		this.children = children;
	}

}
