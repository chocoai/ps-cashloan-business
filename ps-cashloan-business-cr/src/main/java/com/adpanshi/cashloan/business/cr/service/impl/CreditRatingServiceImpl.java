package com.adpanshi.cashloan.business.cr.service.impl;

import com.adpanshi.cashloan.business.core.common.exception.CreditException;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.cr.domain.CrResult;
import com.adpanshi.cashloan.business.cr.domain.CrResultDetail;
import com.adpanshi.cashloan.business.cr.domain.Rank;
import com.adpanshi.cashloan.business.cr.domain.RankDetail;
import com.adpanshi.cashloan.business.cr.mapper.*;
import com.adpanshi.cashloan.business.cr.model.CreditRatingModel;
import com.adpanshi.cashloan.business.cr.model.CreditTypeModel;
import com.adpanshi.cashloan.business.cr.model.FactorModel;
import com.adpanshi.cashloan.business.cr.model.srule.client.RulesExecutorUtil;
import com.adpanshi.cashloan.business.cr.model.srule.model.SimpleRule;
import com.adpanshi.cashloan.business.cr.service.CreditRatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tool.util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 信用评级及结果操作

 * @version 1.0.0
 * @date 2017年1月6日 上午10:46:51
 * Copyright 粉团网路 creditrank  All Rights Reserved
 *
 * 
 *
 */
@Service("creditRatingService")
public class CreditRatingServiceImpl extends BaseServiceImpl<CrResult, Long>  implements CreditRatingService {
	
	
	@Resource
	private CrResultMapper crResultMapper;
	@Resource
	private RankMapper rankMapper;
	@Resource
	private RankDetailMapper rankDetailMapper;
	@Resource
	private CrResultDetailMapper crResultDetailMapper;
	@Resource
	private CreditTypeMapper creditTypeMapper;
	@Resource
	private CardMapper cardMapper;

	/**
	 * 自动评分，返回结果包含额度totalAmount
	 * 如果result为null
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
	public CrResult saveCreditRating(String consumerNo,Long borrowTypeId) throws CreditException  {
		//判断用户是否进行过评分操作
		//1、根据借款类型找到额度类型,查看该额度类型是否有进行评级
		CreditTypeModel creditType = null;
		if(borrowTypeId!=null){
			creditType = creditTypeMapper.findByBorrowTypeId(borrowTypeId);
		}
		
		CrResult result = null;
		if(creditType!=null && consumerNo!=null){
			result = crResultMapper.findCreditTypeResult(consumerNo, creditType.getId());
		}else{
			throw new CreditException("评分失败，请联系管理员配置借款类型对应的额度类型");
		}
		
		//如果没有该额度类型的评级记录，重新进行评级，如果有记录，则不进行评级
		if(result==null && creditType!=null){
			cardMapper.updateType(Long.valueOf(creditType.getCardId()));
			Integer totalScore = 0;
			BigDecimal totalAmount = BigDecimal.valueOf(0.00);
			//保存一条评分结果总记录，总分数和总额度暂时为0，后续统计完成之后更新
			result = new CrResult();
			result.setConsumerNo(consumerNo);
			result.setCreditTypeId(creditType.getId());
			result.setTotalScore(totalScore);
			result.setTotalAmount(totalAmount);
			result.setAddTime(new Date());
			//先保存result，能够获取到creditrank_cr_result表的主键ID，方便后续保存评分详情creditrank_cr_result_detail能够取到关联creditrank_cr_result的主键ID
			crResultMapper.saveRecord(result);
			
			//评分操作start
			//查询用户对应的所有到因子参数级别的评分基础信息，包含表名称，列名称，参数表达式，取值范围等
			List<CreditRatingModel> crList = crResultMapper.queryCreditRating(borrowTypeId,FactorModel.FACTOR_TYPE_SYSTEM);
			if (crList != null && !crList.isEmpty()) {
				for(int i=0;i<crList.size();i++){
					CreditRatingModel crModel = crList.get(i);
					CrResultDetail detail = new CrResultDetail(result.getId(), crModel.getCardId(), crModel.getItemId(), crModel.getFactorId(), crModel.getParamId(), crModel.getColName(), crModel.getParamScore(), crModel.getFormula(), crModel.getRange());
					String statement = "select "+crModel.getColName()+" from "+crModel.getTabName()+" where consumer_no = "+consumerNo;
		    		//从指定的数据库中找到需要评定的真实值
					String value = crResultMapper.findValidValue(statement);
		    		detail.setValue(value);
		    		//进行表达式匹配操作
					SimpleRule simpleRule = RulesExecutorUtil.singleRuleResult(crModel.getParamId(), crModel.getColName(), crModel.getFormula(), crModel.getRange(), value, crModel.getType(), "");
					//如果匹配通过，那么分数赋值为对应规则指定的分数
					if(SimpleRule.COMPAR_PASS.equals(simpleRule.getComparResult())){
						detail.setScore(crModel.getParamScore());
						totalScore += crModel.getParamScore();
					}else{
						//如果匹配不通过，则为0分
						detail.setScore(0);
					}
					
					//设置结果的级别为因子参数级别
					detail.setLevel(CrResultDetail.LEVEL_FACTOR_PARAM);
					detail.setAddTime(DateUtil.getNow());
					crResultDetailMapper.save(detail);
				}
			}else{
				//没有进行评分，抛出异常,事物回滚
				throw new CreditException("评分失败，没有找到对应的系统配置");
			}
			
			
			
			List<CrResultDetail> scoreList = new ArrayList<CrResultDetail>();
			
			//按照因子级别统计因子得分并加入scoreList，方便保存信息
			List<CrResultDetail> tempList = crResultDetailMapper.countFactorScore(result.getId());
			scoreList.addAll(tempList);
			
			//按照项目级别统计因子得分并加入scoreList，方便保存信息
			tempList = crResultDetailMapper.countItemScore(result.getId());
			scoreList.addAll(tempList);
			
			//按照评分卡级别统计因子得分并加入scoreList，方便保存信息
			tempList = crResultDetailMapper.countCardScore(result.getId());
			Rank rank = rankMapper.findByPrimary(Long.valueOf(creditType.getRankId()));
			
			for(int i=0;i<tempList.size();i++){
				if(rank!=null){
					CrResultDetail temp = tempList.get(i);
					RankDetail detail = rankDetailMapper.findByParentIdAndScore(Long.valueOf(creditType.getRankId()),temp.getScore());
					totalAmount = totalAmount.add(detail.getAmountMin());
					temp.setAmount(detail.getAmountMin());
					tempList.set(i, temp);
				}
			}
			scoreList.addAll(tempList);
			
			
			//保存评分卡的因子、项目、评分卡统计结果
			crResultDetailMapper.saveCountScore(scoreList);
			
			//评分操作end
			
			//设置此次评分总分数,统计借款类型的总分数
			result.setTotalScore(totalScore);
			//设置此次评分总额度
			result.setTotalAmount(totalAmount);
			//更新一次总分数和总额度即可
			crResultMapper.update(result);
		}
		
		return result;
	}

	@Override
	public BaseMapper<CrResult, Long> getMapper() {
		return crResultMapper;
	}

}
