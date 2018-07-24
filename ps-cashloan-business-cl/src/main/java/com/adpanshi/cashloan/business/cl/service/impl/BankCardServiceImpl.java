package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.mapper.UserAuthMapper;
import com.adpanshi.cashloan.business.cl.service.BankCardService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.mapper.UserMapper;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.business.rule.mapper.ClBorrowMapper;
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
    
    @Resource
    private UserMapper userMapper;

    @Resource
	private ClBorrowMapper clBorrowMapper;

    @Resource
	private UserAuthMapper userAuthMapper;
    
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

	/**
	 * 解除银行卡的绑定
	 * 通过修改数据而不是第三方的方式解约
	 * @param userId
	 * @return
	 */
	@Override
	public int unBindBankCard(Long userId) {
		try {
			//根据用户Id获取用户最近一条订单
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId",userId);
			Borrow borrow = clBorrowMapper.findRepayBorrow(paramMap);
			if(borrow != null && (!borrow.getState().equals("20")&&!borrow.getState().equals("26")&&!borrow.getState().equals("31"))){
				logger.info("用户解除银行卡绑定userId:"+userId+"=="+ DateUtil.getNow());
				paramMap.put("bankCardState","10");
				return userAuthMapper.updateByUserId(paramMap);
			}
			paramMap.put("userId", userId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return 0;
	}
	
}
