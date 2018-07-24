package com.adpanshi.cashloan.business.cl.extra.credits;

import com.adpanshi.cashloan.business.cl.extra.ExtraBase;

/***
 ** @category 记录临时额度提升各阶段...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年11月3日下午18:16:47
 **/
@SuppressWarnings("serial")
public class CreditsExtra extends ExtraBase{
	@Override
	public String getRoot() {
		return "TmpCredits";
	}

}
