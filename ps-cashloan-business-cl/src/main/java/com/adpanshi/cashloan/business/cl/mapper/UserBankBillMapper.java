package com.adpanshi.cashloan.business.cl.mapper;


import com.adpanshi.cashloan.business.cl.domain.UserBankBill;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.mapper.RDBatisDao;
import org.apache.ibatis.annotations.Param;

@RDBatisDao	
public interface UserBankBillMapper extends BaseMapper<UserBankBill, Long> {
	 int saveUserBankBill(@Param("tableName") String tableName, @Param("item") UserBankBill userBankBill);
	 
}

