package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.UserBankBill;
import com.adpanshi.cashloan.business.cl.mapper.UserBankBillMapper;
import com.adpanshi.cashloan.business.cl.service.UserBankBillService;
import com.adpanshi.cashloan.business.core.common.enums.TableDataEnum;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.ShardTableUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-07-14 10:36:53
 * Copyright 粉团网路 arc All Rights Reserved
 *
 *
 */
@Service("userBankBillService")
public class UserBankBillServiceImpl extends BaseServiceImpl<UserBankBill, Long> implements UserBankBillService {

	@Resource
	private UserBankBillMapper userBankBillMapper;

	@Override
	public BaseMapper<UserBankBill, Long> getMapper() {
		return userBankBillMapper;
	}

	@Override
	public Page<UserBankBill> page(Long userId , int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("tableName",ShardTableUtil.getTableNameByParam(userId , TableDataEnum.TABLE_DATA.CL_USER_BANK_BILL));
		paramMap.put("userId",userId);
		return (Page<UserBankBill>) userBankBillMapper.listSelective(paramMap);
	}

}
