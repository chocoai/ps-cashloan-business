package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowMainProgress;
import com.adpanshi.cashloan.business.cl.mapper.BorrowMainProgressMapper;
import com.adpanshi.cashloan.business.cl.service.BorrowMainProgressService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.Borrow;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.BorrowMainModel;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.model.BorrowModel;
import com.adpanshi.cashloan.business.rule.domain.BankCard;
import com.adpanshi.cashloan.business.rule.mapper.BankCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 借款主信息表ServiceImpl
 * 
 * @author yecy
 * @version 1.0.0
 * @date 2017-12-08 16:09:17

 *
 *
 */
 
@Service("borrowMainProgressService")
public class BorrowMainProgressServiceImpl extends BaseServiceImpl<BorrowMainProgress, Long> implements BorrowMainProgressService {

	private static final Logger logger = LoggerFactory.getLogger(BorrowMainProgressServiceImpl.class);

	@Resource
	private BorrowMainProgressMapper borrowMainProgressMapper;

	@Resource
	private BorrowMainMapper borrowMainMapper;

	@Resource
	private BankCardMapper bankCardMapper;

	@Override
	public BaseMapper<BorrowMainProgress, Long> getMapper() {
		return borrowMainProgressMapper;
	}


	/**
	 * 添加借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	@Override
	public void savePressState(BorrowMain borrow, String state) {
		BorrowMainProgress borrowProgress = new BorrowMainProgress();
		borrowProgress.setBorrowId(borrow.getId());
		borrowProgress.setUserId(borrow.getUserId());
		if (state.equals(BorrowModel.STATE_PRE)) {
			borrowProgress.setRemark("Borrowing ₹"
					+ StringUtil.isNull(borrow.getAmount())
					+ ",Tenure of lending "
					+ borrow.getTimeLimit()
					+ " days,Comprehensive cost ₹"
					+ StringUtil.isNull(borrow.getFee()));
		} else {
			borrowProgress.setRemark(BorrowModel.convertBorrowRemark(state));
		}
		borrowProgress.setState(state);
		borrowProgress.setCreateTime(DateUtil.getNow());
		borrowMainProgressMapper.save(borrowProgress);
	}


	/**
	 * 更新最近的借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	@Override
	public void updateNearestPressState(BorrowMain borrow, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", borrow.getUserId());
		paramMap.put("borrowId", borrow.getId());
		paramMap.put("orderByDesc", " `id` desc ");
		paramMap.put("limit", 1);
		BorrowMainProgress borrowProgress = borrowMainProgressMapper.findSelective(paramMap);
		if (state.equals(BorrowModel.STATE_PRE)) {
			borrowProgress.setRemark("借款"
					+ StringUtil.isNull(borrow.getAmount())
					+ "元，期限"
					+ borrow.getTimeLimit()
					+ "天，综合费用"
					+ StringUtil.isNull(borrow.getFee()) + "元，"
					+ BorrowModel.convertBorrowRemark(state));
		} else {
			borrowProgress.setRemark(BorrowModel.convertBorrowRemark(state));
		}
		borrowProgress.setState(state);
		borrowProgress.setCreateTime(DateUtil.getNow());
		borrowMainProgressMapper.update(borrowProgress);
	}

	@Override
	public List<BorrowMainProgress> getProcessByMainId(Long borrowMainId) {
		if (borrowMainId == null) {
			return new ArrayList<>();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("borrowId", borrowMainId);
		return borrowMainProgressMapper.listSelective(paramMap);
	}

	@Override
	public List<BorrowMainProgress> getProcessById(Long borrowMainId, Borrow borrow) {

		List<BorrowMainProgress> list = new ArrayList<BorrowMainProgress>();
		Map<String, Object> bpMap = new HashMap<String, Object>();
		if(borrow == null){
			BorrowMain borrowMain = borrowMainMapper.findById(borrowMainId);
			bpMap.put("borrowId", borrowMain.getId());
			List<BorrowMainProgress> pgList;

			if (StringUtil.isNotEmptys(borrowMain.getState())) {

				pgList = borrowMainProgressMapper.listSelective(bpMap);

				for (int i = pgList.size() - 1; i >= 0; i--) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(pgList.get(0).getCreateTime());
					cal.add(Calendar.SECOND, +1);
					BorrowMainProgress mainProgress = new BorrowMainProgress();
					mainProgress.setUserId(borrowMain.getUserId());
					mainProgress.setBorrowId(borrowMain.getId());
					mainProgress.setId(pgList.get(i).getId());
					mainProgress.setStr(pgList.get(i).getState());
					mainProgress.setState(mainProgress.getStr());
					mainProgress.setRemark(pgList.get(i).getRemark());
					if(pgList.get(i).getState().equals(BorrowModel.STATE_HANGUP)){
						//订单挂起的状态重新定义
						mainProgress.setRemark("Please wait patiently during the data review");
					}
					mainProgress.setCreateTime(cal.getTime());
					list.add(mainProgress);

				}
			}
		}
		return list;
	}

	@Override
	public Map<String,Object> result(Long borrowId) {

		Map<String,Object> result = new HashMap<>();
		//根据borrowId获取订单信息
		BorrowMain borrowMain = borrowMainMapper.findByPrimary(borrowId);
		if(borrowMain!=null){
			BorrowMainModel borrowMainModel = new BorrowMainModel();
			BeanUtils.copyProperties(borrowMain,borrowMainModel);
			//根据用户id获取银行卡信息
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", borrowMain.getUserId());
			BankCard card = bankCardMapper.findSelective(paramMap);
			borrowMainModel.setCardNo(card.getCardNo());
			borrowMainModel.setBank(card.getBank());

			List<BorrowMainModel> brList = new ArrayList<BorrowMainModel>();
			brList.add(borrowMainModel);
			result.put("borrow", brList);
		}
		return result;
	}
}
