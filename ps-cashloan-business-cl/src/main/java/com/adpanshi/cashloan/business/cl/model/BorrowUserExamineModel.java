package com.adpanshi.cashloan.business.cl.model;

import com.adpanshi.cashloan.business.cl.domain.BorrowUserExamine;

public class BorrowUserExamineModel extends BorrowUserExamine{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单任务数
	 */
	private Long cont;

	public Long getCont() {
		return cont;
	}

	public void setCont(Long cont) {
		this.cont = cont;
	}
}
