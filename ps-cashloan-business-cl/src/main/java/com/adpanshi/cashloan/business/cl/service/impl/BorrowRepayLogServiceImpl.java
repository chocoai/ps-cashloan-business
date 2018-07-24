package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowRepayLog;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayLogMapper;
import com.adpanshi.cashloan.business.cl.model.BorrowRepayLogModel;
import com.adpanshi.cashloan.business.cl.model.ManageBRepayLogModel;
import com.adpanshi.cashloan.business.cl.service.BorrowRepayLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 还款记录ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 13:46:12
 *
 *
 * 
 *
 */
 
@Service("borrowRepayLogService")
public class BorrowRepayLogServiceImpl extends BaseServiceImpl<BorrowRepayLog, Long> implements BorrowRepayLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowRepayLogServiceImpl.class);
   
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;


	@Override
	public BaseMapper<BorrowRepayLog, Long> getMapper() {
		return borrowRepayLogMapper;
	}

	@Override
	public int save(BorrowRepayLog brl) {
		return borrowRepayLogMapper.save(brl);
	}

	@Override
	public Page<BorrowRepayLogModel> page(Map<String, Object> searchMap,
			int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<BorrowRepayLogModel> list = borrowRepayLogMapper.listSelModel(searchMap);
		return (Page<BorrowRepayLogModel>)list;
	}
	
	@Override
	public Page<ManageBRepayLogModel> listModel(Map<String, Object> params,
			int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		if (null != params && params.size() > 0)
		{
			// 集合不为空则开始递归去除字符串的空格
			for(Map.Entry<String, Object>  entry : params.entrySet())
			{
				if(null != params.get(entry.getKey()) && params.get(entry.getKey()) != "")
					params.put(entry.getKey(), params.get(entry.getKey()).toString().trim());
			}
		}
		List<ManageBRepayLogModel> list = borrowRepayLogMapper.listModel(params);
		return (Page<ManageBRepayLogModel>)list;
	}
	
	@Override
	public BorrowRepayLog findSelective(Map<String, Object> paramMap) {
		BorrowRepayLog borrowRepayLog = null;
		try {
			borrowRepayLog = borrowRepayLogMapper.findSelective(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return borrowRepayLog;
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap) {
		int result  =  borrowRepayLogMapper.updateSelective(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}

	@Override
	public boolean refundDeduction(Map<String, Object> paramMap){
		int result  =  borrowRepayLogMapper.refundDeduction(paramMap);
		if(result > 0L){
			return true;
		}
		return false;
	}
}
