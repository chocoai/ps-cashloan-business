package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.PayLog;
import com.adpanshi.cashloan.business.cl.mapper.BorrowProgressMapper;
import com.adpanshi.cashloan.business.cl.mapper.PayLogMapper;
import com.adpanshi.cashloan.business.cl.model.ManagePayLogModel;
import com.adpanshi.cashloan.business.cl.model.PayLogModel;
import com.adpanshi.cashloan.business.cl.service.LoanCityLogService;
import com.adpanshi.cashloan.business.cl.service.PayLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 支付记录ServiceImpl
 * 

 * @version 1.0.0
 * @date 2017-03-07 16:18:56
 *
 *
 * 
 *
 */
 
@Service("payLogService")
public class PayLogServiceImpl extends BaseServiceImpl<PayLog, Long> implements PayLogService {
	@Resource
	private PayLogMapper payLogMapper;
	@Resource
	private BorrowProgressMapper borrowProgressMapper;
	@Resource
	private BorrowMainMapper borrowMainMapper;
	@Resource
	private LoanCityLogService loanCityLogService;


	@Override
	public BaseMapper<PayLog, Long> getMapper() {
		return payLogMapper;
	}
	
	@Override
	public boolean save(PayLog payLog) {
		payLog.setCreateTime(DateUtil.getNow());
		int result = payLogMapper.save(payLog);
		if (result > 0L) {
			return true;
		}
		return false;
	}

	@Override
	public Page<ManagePayLogModel> page(int current, int pageSize,
			Map<String, Object> searchMap) {
		PageHelper.startPage(current, pageSize);
		if (null != searchMap && searchMap.size() > 0)
		{
			String payState = (String)searchMap.get("state") ;
			if (PayLogModel.STATE_PENDING_REVIEW.equals(payState)){
				searchMap.put("isVerify",true);
			}
		}
		Page<ManagePayLogModel> page = (Page<ManagePayLogModel>) payLogMapper.page(searchMap);
		return page;
	}

	@Override
	public PayLog findByOrderNo(String orderNo) {
		/*Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo", orderNo);
		return payLogMapper.findSelective(paramMap);*/
		return payLogMapper.findPayLogByLastOrderNoWithBorrowId(orderNo, null);
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap){
		int result  = payLogMapper.updateSelective(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}

	@Override
	public PayLog findSelective(Map<String, Object> paramMap) {
		return payLogMapper.findSelective(paramMap);
	}
	

	@Override
	public PayLog findSelectiveOne(Map<String, Object> paramMap) {
		return payLogMapper.findSelectiveOne(paramMap);
	}

	/**
	 * 条件查询 -> 查询当前贷款单下有多少支付记录
	 * @date : 20170719
	 * @author : nmnl
	 * @param paramMap
	 * @return
	 */
	public List<PayLog> findSelectiveMore(Map<String, Object> paramMap){
		return payLogMapper.listSelective(paramMap);
	}

	@Override
	public PayLog getPayLogByLateOrderNo(Map<String, Object> paramMap) {
		return payLogMapper.getPayLogByLateOrderNo(paramMap);
	}

	@Override
	public PayLog findPayLogByLastOrderNoWithBorrowId(String orderNo, String borrowId) {
		if(StringUtil.isBlank(orderNo)) return null;
		return payLogMapper.findPayLogByLastOrderNoWithBorrowId(orderNo, borrowId);
	}

}
