package com.adpanshi.cashloan.business.cr.model;

import com.adpanshi.cashloan.business.cr.domain.Rank;


/** 
 * @author
 * @version 1.0
 * @date 2017-1-16 下午4:35:03
 * Copyright 粉团网路 资产风控系统  All Rights Reserved
 *
 * 
 *
 */
@SuppressWarnings("serial")
public class RankModel extends Rank{

	private String num;

	/**
	 * 获取num
	 * @return num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * 设置num
	 * @param num
	 */
	public void setNum(String num) {
		this.num = num;
	}
}
