package com.adpanshi.cashloan.rule.mapper;

import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.mapper.RDBatisDao;
import com.adpanshi.cashloan.rule.domain.BankCard;

/**
 * 银行卡Dao
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-15 14:37:14
 *
 */
@RDBatisDao
public interface BankCardMapper extends BaseMapper<BankCard,Long> {


}
