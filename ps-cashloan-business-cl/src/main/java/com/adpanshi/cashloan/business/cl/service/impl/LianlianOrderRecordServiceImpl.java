package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.LianlianOrderRecord;
import com.adpanshi.cashloan.business.cl.domain.PayLog;
import com.adpanshi.cashloan.business.cl.enums.InstantResultPayEnum;
import com.adpanshi.cashloan.business.cl.enums.LianlianPayEnum;
import com.adpanshi.cashloan.business.cl.enums.LianlianPayTypeEnum;
import com.adpanshi.cashloan.business.cl.enums.StagesResultPayEnum;
import com.adpanshi.cashloan.business.cl.mapper.LianlianOrderRecordMapper;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.BasePaymentModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.InstantOrderRecordModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.OrderRecodeMode;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.StagesOrderRecordModel;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.constant.LianLianConstant;
import com.adpanshi.cashloan.business.cl.model.pay.lianlian.util.LianLianHelper;
import com.adpanshi.cashloan.business.cl.service.LianlianOrderRecordService;
import com.adpanshi.cashloan.business.cl.service.PayLogService;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.EnumUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-14 10:08:24
 * @history
 */
@Service("lianlianOrderRecordService")
public class LianlianOrderRecordServiceImpl extends BaseServiceImpl<LianlianOrderRecord,Long> implements LianlianOrderRecordService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private LianlianOrderRecordMapper lianlianOrderRecordMapper;

	@Resource
	private PayLogService payLogService;
	
	@Override
	public BaseMapper<LianlianOrderRecord, Long> getMapper() {
		return lianlianOrderRecordMapper;
	}

	@Override
	public OrderRecodeMode queryOrderNoByLianLian(LianlianPayTypeEnum payType,String orderNo) {
		OrderRecodeMode model=new OrderRecodeMode();
		try {
			//这里须要注意的是、这里的orderNo、与请求连连时的no_order不一样、需要根据orderNo 去cl_pay_log找到 no_order
			Map<String,Object> paramMap=new HashMap<>();
			paramMap.put("orderNo", orderNo);
			PayLog payLog=payLogService.getPayLogByLateOrderNo(paramMap);
			if(null==payLog){
				logger.error("----------------------->查询的订单号:{}错误，支付订单中不存在.",new Object[]{orderNo});
				model.setResultPay(InstantResultPayEnum.NOT_FOUND.getCode());
				model.setReturnParams("{ret_code:'4040',ret_msg:'请确认订单号是否存在'}");
				model.setRet_code("4040");
				model.setRet_msg("请确认订单号是否存在");
				return model;
			}
			LianLianHelper helper = new LianLianHelper();
			Date reqDate=new Date();
			if(payType.getKey().equals(LianlianPayTypeEnum.STAGES_PAY.getKey())){
				StagesOrderRecordModel queryRepayment = new StagesOrderRecordModel(orderNo,payLog.getOrderNo());
				//@remark:注销,该代码存在上线风险 @date:20171024 @author:nmnl
				//queryRepayment.setSubUrl("http://proxy.xiaoeqiandai.com/orderquery.htm");// TODO 生产环境、请注释掉此行
				BasePaymentModel baseModel= helper.orderQuery(queryRepayment);
				BeanUtils.copyProperties(model,baseModel);
				model.setReturnParams(queryRepayment.getReturnParams());
				model.setResultPay(queryRepayment.getResult_pay());
			}else{
				InstantOrderRecordModel queryRepayment = new InstantOrderRecordModel(orderNo,payLog.getOrderNo());
				//@remark:注销,该代码存在上线风险 @date:20171024 @author:nmnl
				//queryRepayment.setSubUrl("http://proxy.xiaoeqiandai.com/paymentapi/queryPayment.htm"); //TODO 生产环境、请注释掉此行
				BasePaymentModel baseModel= helper.orderQuery(queryRepayment);
				BeanUtils.copyProperties(model,baseModel);
				model.setReturnParams(queryRepayment.getReturnParams());
				model.setResultPay(queryRepayment.getResult_pay());
			}
			if(!StringUtil.isEmpty(payLog.getUpdateTime())){
				//这里还有一种情况、就是连连返回:8901 没有记录的情况、在没有明确订单失败的情况下不能直接把订单看成是已失败的、连连给出的处理方案是:
				//支付请求时间与这次订单查询时间时间差>=30分钟并且连连返回的还是:8901 没有记录的情况下，订单才能算是失败的，
				int minute=DateUtil.minuteBetween(payLog.getUpdateTime(),reqDate);
				if(minute>=Integer.parseInt(LianlianPayEnum.INTERVAL_MINUTE.getCode())){
					model.setRequestAgain(LianlianPayEnum.REQUEST_AGAIN.getCode());
					model.setIntervalMinute(minute);
				}
			}
		}catch (Exception e){
			logger.error("",e);
		}
		return model;
	}

	@Override
	public List<LianlianOrderRecord> queryByOrderNo(Integer payType,String orderNo) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("payType", payType);
		return lianlianOrderRecordMapper.listSelective(map);
	}

	@Override
	public LianlianOrderRecord queryByLastOrderNo(Integer payType,String orderNo) {
		Map<String,Object> params=new HashMap<>();
		params.put("payType", payType);
		params.put("orderNo", orderNo);
		return lianlianOrderRecordMapper.queryByLastOrderNo(params);
	}

	@Override
	public Boolean getQueryLocalInLianlianByOrderNo(LianlianPayTypeEnum payType,SysUser sysUser,String orderNo,Boolean payAgain) {
		if(StringUtil.isBlank(orderNo)) {
			logger.error("--------->订单号:{}不能为空.",new Object[]{orderNo});
			return false;
		}
		LianlianOrderRecord record= queryByLastOrderNo(payType.getKey(),orderNo);
		boolean flag=isPayAgainByParams(payType, record,payAgain);
		if(!flag) return flag;
		//----如果查询的订单号不存在且订单号已经在本地库中已经存在，则没有必要在插入一条重复的数据了...
		if(null!=record && InstantResultPayEnum.NOT_FOUND.getCode().equals(record.getResultPay())){
			logger.info("-------------->订单号，未在支付记录中找到记录，跳过后续逻辑.");
			return false;
		}
		//去连连查询
		logger.info("------------------>开始请求连连{}订单查询接口，订单号={}.",new Object[]{payType.getName(),orderNo});
		OrderRecodeMode repayment= queryOrderNoByLianLian(payType, orderNo);
		
		//如果支付时间-与请求时间、间隔分钟>=30分钟那么，是可以允许再次支付的(这里是为了每次请求连连订单时，都需要记录入库.)
		Boolean payAginFlag=Boolean.FALSE;
		if(payAgain){
			if(null!=repayment && LianLianConstant.RESPONSE_UN_RECORD_CODE.equals(repayment.getRet_code())){
				//这里还有一种情况、就是连连返回:8901 没有记录的情况、在没有明确订单失败的情况下不能直接把订单看成是已失败的、连连给出的处理方案是:
				//支付请求时间与这次订单查询时间时间差>=30分钟并且连连返回的还是:8901 没有记录的情况下，订单才能算是失败的，
				if(LianlianPayEnum.REQUEST_AGAIN.getCode().equals(repayment.getRequestAgain())){
					logger.info("---------------->此次订单查询结果retCode={},retMsg={},且时间相隔{} > 30分钟,可以进行再次支付.",
							new Object[]{repayment.getRet_code(),repayment.getRet_msg(),repayment.getIntervalMinute()}
							);
					payAginFlag= true;
				}
			}
			else if(!StringUtil.isEmpty(record) && null!=record.getRetCode()&&LianLianConstant.RESPONSE_UN_RECORD_CODE.equals(record.getRetCode()) && LianLianConstant.RESPONSE_UN_RECORD_CODE.equals(repayment.getRet_code())){
				int minute=DateUtil.minuteBetween(record.getGmtCreateTime(), new Date());
				if(minute>=Integer.parseInt(LianlianPayEnum.INTERVAL_MINUTE.getCode())){
					logger.info("---------------->上次查询结果retCode={},retMsg={}，此次订单查询结果retCode={},retMsg={},且时间相隔{} > 30分钟,可以进行再次支付.",
							new Object[]{record.getRetCode(),record.getRetMsg(),repayment.getRet_code(),repayment.getRet_msg(),minute}
							);
					payAginFlag= true;
				}
			}
		}
		//每次用户查询、都需要记录入库.
		LianlianOrderRecord saveRecord=new LianlianOrderRecord();
		logger.info("---------------->请求的订单号:{}，请求结束，开始记录请求日记.",new Object[]{orderNo});
		saveRecord.setOrderNo(orderNo);
		saveRecord.setNoOrder(repayment.getNo_order());
		saveRecord.setUserId(sysUser.getId());
		saveRecord.setUserName(sysUser.getName());
		saveRecord.setRetCode(repayment.getRet_code());
		saveRecord.setRetMsg(repayment.getRet_msg());
		saveRecord.setResultPay(repayment.getResultPay());
        saveRecord.setReturnParams(repayment.getReturnParams());
        saveRecord.setPayType(payType.getKey());
        //判断支付方式(分期付、实时付)
        Boolean stages=payType.getKey().equals(LianlianPayTypeEnum.STAGES_PAY.getKey());
        saveRecord.setRemarks(EnumUtil.getNameByCode(stages?StagesResultPayEnum.class:InstantResultPayEnum.class,repayment.getResultPay()));
		lianlianOrderRecordMapper.save(saveRecord);
		logger.info("---------------->请求记录，已记录入库。");
		if(payAgain && payAginFlag) return payAginFlag;//如果是再次支付逻辑、并且当前订单查询时间与支付时间差>=30分钟、则可以允许再次支付.
		boolean requestFlag= isPayAgainByParams(payType, saveRecord,payAgain);
		return requestFlag;
	}

	/**
	 * <p>根据给定条件查询是否需要重新发起请求</p>
	 * @param payType 支付类型
	 * @param record 待处理的对象
	 * @param payAgain  (true:再次支付逻辑、false:订单查询逻辑)
	 * @return boolean (true:重新发起支付、false:不需要)
	 * */
	private boolean isPayAgainByParams(LianlianPayTypeEnum payType,LianlianOrderRecord record,Boolean payAgain){
		
		if(null!=record && LianLianConstant.RESPONSE_SUCCESS_CODE.equals(record.getRetCode()) && !isreQueryRequest(payType,record.getResultPay(),payAgain)){
			logger.info("------------------>订单在本地库中已存在，支付结果:{}",new Object[]{getResultPayToName(payType,record.getResultPay())});
			return false;
		}
		return true;
	}
	
	/**
	 * <p>根据付款类型、付款结果判断是否须要请求连连</p>
	 * @param payType 支付方式(1.分期付款、2.实时付款)
	 * @param resultPay 付款结果 
	 * @param payAgain  (true:再次支付逻辑、false:订单查询逻辑)
	 * @return boolean
	 * */
	private boolean isreQueryRequest(LianlianPayTypeEnum payType,String resultPay,Boolean payAgain){
		if(payType.getKey().equals(LianlianPayTypeEnum.STAGES_PAY.getKey())){
			return payAgain?StagesResultPayEnum.isPayAgainForRequest(resultPay):StagesResultPayEnum.isReRequest(resultPay);
		}
		return payAgain?InstantResultPayEnum.isPayAgainForRequest(resultPay):InstantResultPayEnum.isReRequest(resultPay);
	}
	
	/**
	 * <p>根据给定条件对字段进行解释</p>
	 * @param payType
	 * @param resultPay
	 * @return String
	 * */
	private String getResultPayToName(LianlianPayTypeEnum payType,String resultPay){
		if(payType.getKey().equals(LianlianPayTypeEnum.STAGES_PAY.getKey()))return EnumUtil.getNameByCode(StagesResultPayEnum.class,resultPay);
		return EnumUtil.getNameByCode(InstantResultPayEnum.class, resultPay);
	}

}