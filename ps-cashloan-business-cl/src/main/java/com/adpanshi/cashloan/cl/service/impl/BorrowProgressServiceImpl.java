package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.cl.domain.BorrowProgress;
import com.adpanshi.cashloan.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.cl.domain.BorrowRepayLog;
import com.adpanshi.cashloan.cl.mapper.BorrowProgressMapper;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayLogMapper;
import com.adpanshi.cashloan.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.cl.model.BorrowProgressModel;
import com.adpanshi.cashloan.cl.model.BorrowRepayModel;
import com.adpanshi.cashloan.cl.model.ManageBorrowProgressModel;
import com.adpanshi.cashloan.cl.service.BorrowProgressService;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.domain.Borrow;
import com.adpanshi.cashloan.core.model.BorrowModel;
import com.adpanshi.cashloan.rule.domain.BankCard;
import com.adpanshi.cashloan.rule.mapper.BankCardMapper;
import com.adpanshi.cashloan.rule.model.ClBorrowModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tool.util.DateUtil;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 借款进度表ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-02-14 10:33:38
 *
 */
 
@Service("borrowProgressService")
public class BorrowProgressServiceImpl extends BaseServiceImpl<BorrowProgress, Long> implements BorrowProgressService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BorrowProgressServiceImpl.class);
   
    @Resource
    private BorrowProgressMapper borrowProgressMapper;
    @Resource
    private BorrowRepayMapper borrowRepayMapper;
    @Resource
    private BorrowRepayLogMapper borrowRepayLogMapper;
    @Resource
    private BankCardMapper bankCardMapper;

	@Override
	public BaseMapper<BorrowProgress, Long> getMapper() {
		return borrowProgressMapper;
	}


	@Override
	public Map<String,Object> result(Borrow borrow) {
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("borrowId", borrow.getId());
		BorrowRepayLog log = borrowRepayLogMapper.findSelective(searchMap);

		List<BorrowRepayModel> repay = borrowRepayMapper.listSelModel(searchMap);
		Map<String,Object> result = new HashMap<>();
		ClBorrowModel clBorrowModel = new ClBorrowModel();
		BeanUtils.copyProperties(borrow, clBorrowModel);
		clBorrowModel.setCreditTimeStr(DateUtil.dateStr(clBorrowModel.getCreateTime(),"yyyy-M-d"));
		clBorrowModel.setPenalty("20");
		if (!repay.isEmpty()) {
			clBorrowModel.setPenaltyAmount(repay.get(0).getPenaltyAmout());
			if (repay.get(0).getPenaltyAmout()>0) {
				clBorrowModel.setPenalty("10");
				clBorrowModel.setOverdueAmount(String.valueOf(clBorrowModel.getAmount()+clBorrowModel.getPenaltyAmount()));
			}
		}else{
			clBorrowModel.setOverdueAmount(String.valueOf(clBorrowModel.getAmount()));
		}
		searchMap.clear();
		searchMap.put("userId", borrow.getUserId());
		BankCard card = bankCardMapper.findSelective(searchMap);
		if(StringUtil.isNotBlank(searchMap)){
			logger.info("用户银行卡信息——银行:"+card.getBank()+",银行卡号为:"+card.getCardNo());
			clBorrowModel.setCardNo(card.getCardNo());
			clBorrowModel.setBank(card.getBank());
		}

		List<ClBorrowModel> brList = new ArrayList<ClBorrowModel>();
		brList.add(clBorrowModel);
		result.put("borrow", brList);
		if(brList.size()>0) {
			logger.info("借款订单的子订单详请为:" + brList.get(0));
		}
		for (BorrowRepayModel borrowRepayModel : repay) {
			logger.info("用户还款计划信息为"+repay.get(0));
			borrowRepayModel.setRepayTimeStr(DateUtil.dateStr(borrowRepayModel.getRepayTime(),"yyyy-M-d"));
			//@remarks: 实际还款金额(本金)+逾期金额.@date: 20170921 @author:nmnl
			borrowRepayModel.setAmount(borrowRepayModel.getAmount()+borrowRepayModel.getPenaltyAmout());
		}
		if (StringUtil.isNotBlank(log)) {
			for (BorrowRepayModel repayModel : repay) {
				repayModel.setRealRepayTime(DateUtil.dateStr(log.getRepayTime(),"yyyy-M-d"));
				//@remarks: 实际还款金额(本金)+逾期金额.@date: 20170921 @author:nmnl
				repayModel.setRealRepayAmount(String.valueOf(log.getAmount()+log.getPenaltyAmout()));
				logger.info("用户实际还款金额为:"+repayModel.getRealRepayAmount());
			}
		}
		result.put("repay", repay);
		return result;
	}
	
	@Override
	public Page<ManageBorrowProgressModel> listModel(Map<String, Object> params, int currentPage,
                                                     int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<ManageBorrowProgressModel> list = borrowProgressMapper.listModel(params);
		return (Page<ManageBorrowProgressModel>)list;
	}

	@Override
	public boolean save(BorrowProgress borrowProgress){
		int result = borrowProgressMapper.save(borrowProgress);
		if(result > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<BorrowProgress> listSeletetiv(Map<String, Object> map) {
		return borrowProgressMapper.listSelective(map);
	}


	/**
	 * 添加借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	@Override
	public void savePressState(Borrow borrow, String state) {
		BorrowProgress borrowProgress = new BorrowProgress();
		borrowProgress.setBorrowId(borrow.getId());
		borrowProgress.setUserId(borrow.getUserId());
		if (state.equals(BorrowModel.STATE_PRE)) {
			borrowProgress.setRemark("借款"
					+ com.adpanshi.cashloan.core.common.util.StringUtil.isNull(borrow.getAmount())
					+ "元，期限"
					+ borrow.getTimeLimit()
					+ "天，综合费用"
					+ com.adpanshi.cashloan.core.common.util.StringUtil.isNull(borrow.getFee()) + "元，"
					+ BorrowModel.convertBorrowRemark(state));
		} else {
			borrowProgress.setRemark(BorrowModel.convertBorrowRemark(state));
		}
		borrowProgress.setState(state);
		borrowProgress.setCreateTime(com.adpanshi.cashloan.core.common.util.DateUtil.getNow());
		borrowProgressMapper.save(borrowProgress);
	}


	/**
	 * 更新最近的借款进度
	 *
	 * @param borrow
	 * @param state
	 */
	@Override
	public void updateNearestPressState(Borrow borrow, String state) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId",borrow.getUserId());
		paramMap.put("borrowId",borrow.getId());
		paramMap.put("orderByDesc"," `id` desc ");
		paramMap.put("limit",1);
		BorrowProgress borrowProgress=borrowProgressMapper.findSelective(paramMap);
		if (state.equals(BorrowModel.STATE_PRE)) {
			borrowProgress.setRemark("借款"
					+ com.adpanshi.cashloan.core.common.util.StringUtil.isNull(borrow.getAmount())
					+ "元，期限"
					+ borrow.getTimeLimit()
					+ "天，综合费用"
					+ com.adpanshi.cashloan.core.common.util.StringUtil.isNull(borrow.getFee()) + "元，"
					+ BorrowModel.convertBorrowRemark(state));
		} else {
			borrowProgress.setRemark(BorrowModel.convertBorrowRemark(state));
		}
		borrowProgress.setState(state);
		borrowProgress.setCreateTime(com.adpanshi.cashloan.core.common.util.DateUtil.getNow());
		borrowProgressMapper.update(borrowProgress);
	}

	/**
	 * 借款进度显示
	 *
	 * @param borrow
	 * @param pageFlag
	 *            detail代表详情页，index首页，首页不显示审核不通过和放款成功的进度，显示可以借款的信息
	 * @return
	 */
	@Override
	public List<BorrowProgressModel> borrowProgress(Borrow borrow,
													String pageFlag) {
		List<BorrowProgressModel> list = new ArrayList<BorrowProgressModel>();
		Map<String, Object> bpMap = new HashMap<String, Object>();
		bpMap.put("borrowId", borrow.getId());
		List<BorrowProgressModel> pgList;

		// 待审核(订单挂起的状态25追加)
		if (BorrowModel.STATE_PRE.equals(borrow.getState())
				|| BorrowModel.STATE_NEED_REVIEW.equals(borrow.getState()) || BorrowModel.STATE_HANGUP.equals(borrow.getState())) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);
			Calendar cal = Calendar.getInstance();
			cal.setTime(pgList.get(0).getCreateTime());
			cal.add(Calendar.SECOND, +1);
			BorrowProgressModel progress = new BorrowProgressModel();
			progress.setUserId(borrow.getUserId());
			progress.setBorrowId(borrow.getId());
			progress.setRemark("Please wait patiently during the data review");
			progress.setStr("UnderReview");
			progress.setState(progress.getStr());
			progress.setType("10");
			progress.setCreateTime(cal.getTime());
			list.add(progress);

			progress = pgList.get(0);
			progress.setStr(progress.getState());
			progress.setState(progress.getStr());
			progress.setType("10");
			list.add(progress);
		}

		// 继续申请
		if (BorrowModel.STATE_TEMPORARY_AUTO_PASS.equals(borrow.getState())
				|| BorrowModel.STATE_TEMPORARY_NEED_REVIEW.equals(borrow.getState())) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);
			Calendar cal = Calendar.getInstance();
			cal.setTime(pgList.get(0).getCreateTime());
			cal.add(Calendar.SECOND, +1);
			BorrowProgressModel progress = new BorrowProgressModel();
			progress.setUserId(borrow.getUserId());
			progress.setBorrowId(borrow.getId());
			progress.setRemark("已进入风控审核状态，请继续申请。");
			progress.setStr("审核中");
			progress.setState(progress.getStr());
			progress.setType("10");
			progress.setCreateTime(cal.getTime());
			list.add(progress);

			progress = pgList.get(0);
			progress.setStr(progress.getState());
			progress.setState(progress.getStr());
			progress.setType("10");
			list.add(progress);
		}

		// 审核不通过 （自动审核不通过，人工复审不通过）
		if ("detail".equals(pageFlag)
				&& (BorrowModel.STATE_AUTO_REFUSED.equals(borrow.getState()) || BorrowModel.STATE_REFUSED
				.equals(borrow.getState()))) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);

			int size = pgList.size();
			BorrowProgressModel progress = pgList.get(size - 1);
			progress.setStr(progress.getState());
			progress.setState(progress.getStr());
			progress.setType("20");
			list.add(progress);

			progress = pgList.get(0);
			progress.setStr(progress.getState());
			progress.setState(progress.getStr());
			progress.setType("10");
			list.add(progress);
		}

		// 打款中（放款失败）
		if (BorrowModel.STATE_REPAY_FAIL.equals(borrow.getState())
				|| BorrowModel.STATE_AUTO_PASS.equals(borrow.getState())
				|| BorrowModel.STATE_PASS.equals(borrow.getState())) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);
			boolean passFlag = true;
			for (int i = pgList.size() - 1; i >= 0; i--) {
				BorrowProgressModel progress = pgList.get(i);
				progress.setType("10");
				if (i == 0) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}

				if (passFlag
						&& (BorrowProgressModel.PROGRESS_AUTO_PASS.equals(progress.getState())
						|| BorrowProgressModel.PROGRESS_PERSON_PASS.equals(progress.getState())
				)) {

					Calendar cal = Calendar.getInstance();
					cal.setTime(progress.getCreateTime());
					cal.add(Calendar.SECOND, +1);
					BorrowProgressModel progress2 = new BorrowProgressModel();
					progress2.setUserId(Long.valueOf(borrow.getUserId()));
					progress2.setBorrowId(borrow.getId());
					progress2.setStr("Making payment");
					progress2.setState("Making payment");
					progress2.setMsg("Please wait patiently while making payment");
					progress2.setRemark("Please wait patiently while making payment");
					progress2.setType("10");
					progress2.setCreateTime(cal.getTime());
					list.add(progress2);

					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);

				}

				if (BorrowProgressModel.PROGRESS_LOAN_FAIL.equals(progress
						.getState())) {
					progress.setMsg("Please wait patiently while making payment");
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}
			}
		}

		// 待还款（放款成功）
		if (BorrowModel.STATE_REPAY.equals(borrow.getState())) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);
			boolean passFlag = true;
			for (int i = pgList.size() - 1; i >= 0; i--) {
				BorrowProgressModel progress = pgList.get(i);
				progress.setType("10");
				if (i == 0) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}

				if (passFlag && (BorrowProgressModel.PROGRESS_AUTO_PASS
						.equals(progress.getState()) || BorrowProgressModel.PROGRESS_PERSON_PASS
						.equals(progress.getState()))) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
					passFlag = false;
				}

				if (BorrowProgressModel.PROGRESS_LOAN_SUCCESS.equals(progress.getState())) {
					double repayAmount = borrow.getRealAmount() + borrow.getFee();

					Calendar cal = Calendar.getInstance();
					cal.setTime(progress.getCreateTime());
					cal.add(Calendar.SECOND, +1);
					progress = new BorrowProgressModel();
					progress.setUserId(Long.valueOf(borrow.getUserId()));
					progress.setBorrowId(borrow.getId());
					progress.setStr("Waiting for payment");
					progress.setRemark("You need to repay ₹" + repayAmount + " in "+ borrow.getTimeLimit() + "days");
					Map<String,Object> paramMap = new HashMap<>();
					paramMap.put("borrowId", borrow.getId());
					BorrowRepay repay = borrowRepayMapper.findSelective(paramMap);
					if (repay!=null) {
						int day = com.adpanshi.cashloan.core.common.util.DateUtil.daysBetween(new Date(),
								repay.getRepayTime());
						if (day > 0) {
							progress.setRemark("You need to repay ₹" + repayAmount + " in "+ day + "days");
						} else if (day == 0) {
							progress.setRemark("You need to repay ₹" + repayAmount + " today" );
						}
					}

					if ("1".equals(borrow.getTimeLimit())) {
						progress.setRemark("You need to repay ₹" + repayAmount + " today");
					}
					progress.setState("Waiting for payment");
					progress.setType("10");
					progress.setCreateTime(cal.getTime());
					list.add(progress);

					progress = pgList.get(i);
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}
			}
		}

		// 还款成功
		if ("detail".equals(pageFlag)
				&& (BorrowModel.STATE_FINISH.equals(borrow.getState()) || BorrowModel.STATE_REMISSION_FINISH
				.equals(borrow.getState()))) {
			bpMap.put("state", borrow.getState());
			pgList = borrowProgressMapper.listProgress(bpMap);
			boolean passFlag = true;
			for (int i = pgList.size() - 1; i >= 0; i--) {
				BorrowProgressModel progress = pgList.get(i);
				progress.setType("10");
				if (i == 0) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}

				if (passFlag
						&& (BorrowProgressModel.PROGRESS_AUTO_PASS
						.equals(progress.getState()) || BorrowProgressModel.PROGRESS_PERSON_PASS
						.equals(progress.getState()))) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
					passFlag = false;
				}

				if (BorrowProgressModel.PROGRESS_LOAN_SUCCESS.equals(progress
						.getState())
						|| BorrowProgressModel.PROGRESS_REPAY_SUCCESS
						.equals(progress.getState())
						|| BorrowProgressModel.PROGRESS_REPAY_REMISSION_SUCCESS
						.equals(progress.getState())) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}
			}
		}

		// 逾期
		int brState = Integer.parseInt(borrow.getState());
		int signState = Integer.parseInt(BorrowModel.STATE_FINISH);
		if (brState > signState
				&& !BorrowModel.STATE_REMISSION_FINISH
				.equals(borrow.getState())) {
			bpMap.put("state", BorrowModel.STATE_BAD);
			pgList = borrowProgressMapper.listProgress(bpMap);
			boolean passFlag = true;
			boolean overdueFlag = true;

			for (int i = pgList.size() - 1; i >= 0; i--) {
				BorrowProgressModel progress = pgList.get(i);
				progress.setType("10");
				int prState = Integer.parseInt(progress.getState());
				if (i == 0) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}

				if (passFlag
						&& (BorrowProgressModel.PROGRESS_AUTO_PASS
						.equals(progress.getState()) || BorrowProgressModel.PROGRESS_PERSON_PASS
						.equals(progress.getState()))) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
					passFlag = false;
				}

				if (BorrowProgressModel.PROGRESS_LOAN_SUCCESS.equals(progress
						.getState())) {
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());
					list.add(progress);
				}

				if (overdueFlag && prState > signState) {
					progress = pgList.get(pgList.size() - 1);
					progress.setStr(progress.getState());
					progress.setState(progress.getStr());

					Calendar cal = Calendar.getInstance();
					cal.setTime(progress.getCreateTime());
					cal.add(Calendar.SECOND, +1);
					progress.setRemark("Your loan has been overdue,Please make the repayment as soon as possible");
					progress.setState(progress.getStr());
					progress.setType("10");
					progress.setCreateTime(cal.getTime());
					list.add(progress);
					overdueFlag = false;
				}
			}
		}
		return list;
	}

	@Override
	public List<BorrowProgress> findProcessByState(Long borrowId, Collection<String> stateList) {
		if (CollectionUtils.isEmpty(stateList) || borrowId == null){
			return new ArrayList<>();
		}
		return borrowProgressMapper.findProcessByState(borrowId,stateList);
	}

	@Override
	public int saveAll(List<BorrowProgress> processList) {
		if (CollectionUtils.isEmpty(processList)){
			return 0;
		}
		return borrowProgressMapper.saveAll(processList);
	}
}
