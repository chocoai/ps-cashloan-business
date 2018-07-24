package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.ProfitAmount;
import com.adpanshi.cashloan.business.cl.domain.ProfitCashLog;
import com.adpanshi.cashloan.business.cl.mapper.ProfitAmountMapper;
import com.adpanshi.cashloan.business.cl.mapper.ProfitCashLogMapper;
import com.adpanshi.cashloan.business.cl.service.ProfitAmountService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 分润资金ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-18 16:29:51
 *
 *
 * 
 *
 */
 
@Service("profitAmountService")
public class ProfitAmountServiceImpl extends BaseServiceImpl<ProfitAmount, Long> implements ProfitAmountService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ProfitAmountServiceImpl.class);
   
    @Resource
    private ProfitAmountMapper profitAmountMapper;
    @Resource
    private ProfitCashLogMapper profitCashLogMapper;

	@Override
	public BaseMapper<ProfitAmount, Long> getMapper() {
		return profitAmountMapper;
	}



	@Override
	public int cash(long userId, double money) {
		int msg = 0;
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		ProfitAmount pa = profitAmountMapper.findSelective(map);
		if (StringUtil.isNotBlank(pa)) {
			map.put("noCashed", pa.getNoCashed()-money);
			map.put("cashed", pa.getCashed()+money);
			map.put("id", pa.getId());
			msg = profitAmountMapper.updateSelective(map);
		}
		if (msg>0) {//保存提现记录
			ProfitCashLog pcl = new ProfitCashLog();
			pcl.setUserId(userId);
			pcl.setAmount(money);
			pcl.setAddTime(new Date());
			msg = profitCashLogMapper.save(pcl);
		}
		return msg;
	}


	@Override
	public ProfitAmount find(long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		return profitAmountMapper.findSelective(map);
	}
	

}