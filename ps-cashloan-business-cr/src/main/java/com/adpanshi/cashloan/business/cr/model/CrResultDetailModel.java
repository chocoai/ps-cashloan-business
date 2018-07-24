package com.adpanshi.cashloan.business.cr.model;


import com.adpanshi.cashloan.business.cr.domain.CrResultDetail;

import java.util.List;

@SuppressWarnings("serial")
public class CrResultDetailModel extends CrResultDetail {
	
	
	public List<CrResultItemDetail> itemList;

	/** 
	 * 获取itemList
	 * @return itemList
	 */
	public List<CrResultItemDetail> getItemList() {
		return itemList;
	}

	/** 
	 * 设置itemList
	 * @param itemList
	 */
	public void setItemList(List<CrResultItemDetail> itemList) {
		this.itemList = itemList;
	}



	
}
