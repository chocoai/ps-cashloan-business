package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.service.BankCardService;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.rule.domain.BankCard;
import com.adpanshi.cashloan.rule.mapper.BankCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 银行卡ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-15 14:37:14
 *
 *
 * 
 *
 */
 
@Service("bankCardService")
public class BankCardServiceImpl extends BaseServiceImpl<BankCard, Long> implements BankCardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BankCardServiceImpl.class);
   
    @Resource
    private BankCardMapper bankCardMapper;
    
	@Override
	public BaseMapper<BankCard, Long> getMapper() {
		return bankCardMapper;
	}

	@Override
	public boolean save(BankCard bankCard) {
		int result = bankCardMapper.save(bankCard);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public BankCard getBankCardByUserId(Long userId) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			return bankCardMapper.findSelective(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public BankCard findSelective(Map<String, Object> paramMap) {
		return bankCardMapper.findSelective(paramMap);
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap) {
		int result = bankCardMapper.updateSelective(paramMap);
		if (result > 0L) {
			return true;
		}
		return false;
	}

}
