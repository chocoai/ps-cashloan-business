package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.yincheng.YinChengBorrowOther;
import com.adpanshi.cashloan.business.cl.mapper.YinChengBorrowOtherMapper;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.BaseService;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 银程借款中其他相关字段存储ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2018-03-29 22:58:10

 *
 *
 */
 
@Service("yinChengBorrowOtherService")
public class YinChengBorrowOtherServiceImpl extends BaseServiceImpl<YinChengBorrowOther, Long> implements BaseService<YinChengBorrowOther, Long> {
	
    private static final Logger logger = LoggerFactory.getLogger(YinChengBorrowOtherServiceImpl.class);
   
    @Resource
    private YinChengBorrowOtherMapper yinChengBorrowOtherMapper;

	@Override
	public BaseMapper<YinChengBorrowOther, Long> getMapper() {
		return yinChengBorrowOtherMapper;
	}
	
}
