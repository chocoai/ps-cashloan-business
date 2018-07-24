package com.adpanshi.cashloan.business.cl.service.impl;

import com.adpanshi.cashloan.business.cl.domain.BorrowAgreement;
import com.adpanshi.cashloan.business.cl.domain.BorrowRepay;
import com.adpanshi.cashloan.business.cl.domain.EnjoysignRecord;
import com.adpanshi.cashloan.business.cl.enums.StagesFieldEnum;
import com.adpanshi.cashloan.business.cl.enums.SysDictEnum;
import com.adpanshi.cashloan.business.cl.extra.BorrowIntent;
import com.adpanshi.cashloan.business.cl.extra.HandleBorrowIntent;
import com.adpanshi.cashloan.business.cl.mapper.BorrowRepayMapper;
import com.adpanshi.cashloan.business.cl.mapper.EnjoysignRecordMapper;
import com.adpanshi.cashloan.business.cl.model.TemplateInfoModel;
import com.adpanshi.cashloan.business.cl.service.BorrowAgreementService;
import com.adpanshi.cashloan.business.cl.service.EnjoysignRecordService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.*;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.enjoysign.EnjoysignUtil;
import com.adpanshi.cashloan.business.core.enjoysign.beans.*;
import com.adpanshi.cashloan.business.core.enjoysign.constants.EnjoysignConstant;
import com.adpanshi.cashloan.business.core.enjoysign.enums.EnjoysignConfigEnum;
import com.adpanshi.cashloan.business.core.enjoysign.enums.InterfaceTypeEnum;
import com.adpanshi.cashloan.business.core.enjoysign.enums.StatusEnum;
import com.adpanshi.cashloan.business.core.mapper.BorrowMainMapper;
import com.adpanshi.cashloan.business.core.umeng.beans.Extra;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.adpanshi.cashloan.business.system.mapper.SysAttachmentMapper;
import com.adpanshi.cashloan.business.system.mapper.SysDictDetailMapper;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-09-28 14:55:17
 * @history
 */
@Service("enjoysignRecordService")
public class EnjoysignRecordServiceImpl extends BaseServiceImpl<EnjoysignRecord,Long> implements EnjoysignRecordService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private EnjoysignRecordMapper enjoysignRecordMapper;
	
	@Resource
	private BorrowAgreementService borrowAgreementService;
	
	@Resource
	private SysAttachmentMapper sysAttachmentMapper;
	
	@Resource
	private SysDictDetailMapper sysDictDetailMapper;
	
	@Resource
	private BorrowRepayMapper borrowRepayMapper;
	
	@Resource
	BorrowMainMapper borrowMainMapper;
	
	@Override
	public BaseMapper<EnjoysignRecord, Long> getMapper() {
		return enjoysignRecordMapper;
	}

	@Override
	public int startSignWithAutoSilentSign(BorrowMain borrowMain, String orderId) {
		boolean swithOpen=EnjoysignUtil.isSwithOpen();
		if(!swithOpen){
			return 0;
		}
		if(StringUtil.isEmpty(borrowMain,orderId)){
			logger.error("---------------------->签章前置参数不能为空,borrow、orderId={}.",new Object[]{orderId});
			return 0;
		}
		//先拿到模板控件数据集(甲乙丙三方需要签的控件)
		String templateDataJSON=Global.getValue(EnjoysignConfigEnum.TEMPLATE_DATA.getCode());
		if(StringUtil.isEmpty(templateDataJSON)) {
			logger.info("--------------->模板控件数据集未存在或未启用,sysConfig.code={}.",new Object[]{EnjoysignConfigEnum.TEMPLATE_DATA.getCode()});
			return 0;
		}
		//模板控件(甲乙丙三方需要签署的控件列表)
		SignatoryResult templateDataResult= EnjoysignUtil.getServiceResult(templateDataJSON,SignData.class);
		if(null==templateDataJSON || !templateDataResult.getIsSuccess() || StringUtil.isEmpty(templateDataResult.getObj())){
			logger.info("--------------->模板控件数据集格式错误,sysConfig.code={}.",new Object[]{EnjoysignConfigEnum.TEMPLATE_DATA.getCode()});
			return 0;
		}
		int count=0;
		try {
			BorrowAgreement borrowAgreement = borrowAgreementService.findByBowMainIdWithUserId(borrowMain.getId());
			if(null==borrowAgreement){
				logger.info("--------------->调用borrowAgreementService.borrowAgreement(userId={},borrowId={}),未查询到数据.",
						new Object[]{borrowMain.getUserId(),borrowMain.getId()});
				return 0;
			}
			//查询这个借款用户是否已经签署过
			EnjoysignRecord iExist= enjoysignRecordMapper.getEnjoysignRecordByBorId(borrowMain.getId());
			if(null==iExist){
				//--------------------------> 初始化签名数据   <--------------------------
				PartyData partyData=initPartyData(borrowMain, borrowAgreement, orderId);
				if(null==partyData){
					logger.error("------------------------>发起自动签署前、初始化三方签署数据时、出错、跳过自动签章逻辑....");
					return 0;
				}
				//组装请求参数、根据传入的参数计算安全签名
				Map<String,Object> map=new HashMap<>();
				boolean flag=EnjoysignUtil.getRequiredParameter(templateDataResult, partyData, map, orderId);
				if(!flag){
					logger.error("--------------------------->请求参数解释有异常、跳过自动签章流程....");
					return 0;
				}
				//后台签章前、先把请求记录入库...
				EnjoysignRecord  record=new EnjoysignRecord ();
				record.setBorId(borrowMain.getId());
				record.setBorName(borrowAgreement.getRealName());
				record.setInterfaceType(InterfaceTypeEnum.STARTSIGN.getKey());
				record.setOrderId(orderId);
				record.setStatus(StatusEnum.WAIT_RESPONSE.getKey());
				saveEnjoysignRecord(record);
				logger.info("------------------------>开始发起签署并进行后台自动签章请求...");
				SignatoryResult signatoryResult= EnjoysignUtil.startSignWithAutoSilentSign(templateDataResult,map);
				logger.info("------------------------>发起签署后台自动签章请求结束，返回结果:{}.",new Object[]{JSONObject.toJSONString(signatoryResult)});
				String resParameter=null;
				boolean isReturnData=null!=signatoryResult;
				//更新、状态
				EnjoysignRecord updateRecord=new EnjoysignRecord();
				updateRecord.setId(record.getId());
				Integer status=StatusEnum.FAIL.getKey();
				if(isReturnData){
					if(!StringUtil.isEmpty(signatoryResult.getObj())){
						resParameter=JSONObject.toJSONString(signatoryResult.getObj());
					}
					Extra extra=new Extra();
					extra.put("resultCode", signatoryResult.getResultCode());
					extra.put("resultMsg", signatoryResult.getResultMsg());
					updateRecord.setRemarks(JSONObject.toJSONString(extra));
					if(signatoryResult.getIsSuccess()){
						status=StatusEnum.SUCCESS.getKey();
					}
				}
				updateRecord.setResParameter(resParameter);
				updateRecord.setStatus(status);
				logger.info("------------------------>开始更新发起签署后台自动签章请求记录...");
				count=enjoysignRecordMapper.updateSelective(updateRecord);
				logger.info("------------------------>更新发起签署后台自动签章请求记录结束...");
			}else{
				if(StatusEnum.SUCCESS.getKey().equals(iExist.getStatus()) 
						|| StatusEnum.DOWNLOAD_FAIL.getKey().equals(iExist.getStatus()) 
						|| StatusEnum.DOWNLOAD_SUCCESS.getKey().equals(iExist.getStatus())
						){
					logger.info("--------------------->borId={}的三方合同已于 {} 签署完毕，请勿重复签署...",
							new Object[]{borrowMain.getId(),DateUtil.dateToString(iExist.getGmtUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS)}
					);
					return 0;
				}
				/**
				 * <p>如果该borId在库中已有记录、这里分以下几个步骤</p>
				 * 状态:1.待响应、2.签章失败、3.签章成功、4.下载失败、5.下载成功.
				 * @1.如果状态是: 1.待响应的(三方都签章失败)则需要拿该订单号重新发起后台签章
				 * @2.如果状态是: 2.签章失败(有可能三方签署中有的签章成功、有的签章失败)
				 *   2.1.查询签章失败的签署方、拿该订单号重新进行签章.
				 * */
//				orderId=iExist.getOrderId();//订单号改成上次签章失败的订单号
				orderId = "";
				if(StatusEnum.WAIT_RESPONSE.getKey().equals(iExist.getStatus())){
					//1.待响应的
					count=requestAutoSignAgain(borrowMain, borrowAgreement, templateDataResult, iExist, orderId);
				}else if(StatusEnum.FAIL.getKey().equals(iExist.getStatus())){
				  //2.部分失败、还是全部失败、如果全部失败、也还是要走状态为待响应(WAIT_RESPONSE)的流程....  
					count=requestAutoSignAgain(borrowMain, borrowAgreement, templateDataResult, iExist, orderId);
				}
			}
		} catch (Exception e) {
			logger.error("---------------------->调用自动签章接口出错，方法名:startSignWithAutoSilentSign出错原因:",e);
		}
		return count;
	}
	
	/***
	 * <p>再次发起自动签章请求</p>
	 * @param borrowMain
	 * @param borrowAgreement
	 * @param templateDataResult
	 * @param iExist
	 * @param orderId
	 * @return int 
	 * */
	private int requestAutoSignAgain(BorrowMain borrowMain,BorrowAgreement borrowAgreement,SignatoryResult
			templateDataResult,EnjoysignRecord iExist,String orderId){
		//-------------------------->  -初始化签名数据   <--------------------------
		PartyData partyData=initPartyData(borrowMain, borrowAgreement, orderId);
		if(null==partyData){
			logger.error("------------------------>发起自动签署前、初始化三方签署数据时、出错、跳过自动签章逻辑....");
			return 0;
		}
		//组装请求参数、根据传入的参数计算安全签名
		Map<String,Object> map=new HashMap<>();
		boolean flag=EnjoysignUtil.getRequiredParameter(templateDataResult, partyData, map, orderId);
		if(!flag){
			logger.error("--------------------------->请求参数解释有异常、跳过自动签章流程....");
			return 0;
		}
		logger.info("------------------------>borId={},再次发起三方签章请求...",new Object[]{orderId});
		SignatoryResult signatoryResult= EnjoysignUtil.startSignWithAutoSilentSign(templateDataResult,map);
		logger.info("------------------------>再次发起签署结果:{}.",new Object[]{JSONObject.toJSONString(signatoryResult)});
		String resParameter=null;
		logger.info("------------------------>发起签署并进行后台自动签章请求结束...");
		boolean isReturnData=null!=signatoryResult;
		//更新、状态
		EnjoysignRecord updateRecord=new EnjoysignRecord();
		updateRecord.setId(iExist.getId());
		Integer status=StatusEnum.FAIL.getKey();
		if(isReturnData){
			resParameter=JSONObject.toJSONString(signatoryResult.getObj());
			Extra extra=new Extra();
			extra.put("resultCode", signatoryResult.getResultCode());
			extra.put("resultMsg", signatoryResult.getResultMsg());
			updateRecord.setRemarks(JSONObject.toJSONString(extra));
			if(signatoryResult.getIsSuccess()||EnjoysignConstant.REPEATED_SIGNATURE_STATUS.equals(signatoryResult.getResultCode())){
				//这里如果1号签返回resultCode=40004表示已经此订单已经在1号签存在、且已经成功签署成功也当做成功处理(避免每次调用接口都要重新处理)
				status=StatusEnum.SUCCESS.getKey();
			}
			
		}
		updateRecord.setResParameter(resParameter);
		updateRecord.setStatus(status);
		logger.info("------------------------>开始更新再次发起签署后台自动签章请求记录...");
		return enjoysignRecordMapper.updateSelective(updateRecord);
	}
	
	/**
	 * <p>根据给定参数初始化三方签署需要签章的数据集</p>
	 * @param borrowAgreement
	 * @param orderId
	 * @return PartyData
	 * */
	private PartyData initPartyData(BorrowMain borrowMain,BorrowAgreement borrowAgreement,String orderId){
		PartyData partyData=null;
		if(null==borrowMain || null==borrowAgreement || StringUtil.isEmpty(orderId))return partyData;
		//签署日期(YYYY年-MM月-DD日)
		Date curDate=borrowMain.getLoanTime();//实际签署时间为放款时间（考虑到、针对签章失败、如果当天之后发起再次签章、那么这个签章时间就会有误!）
		String curDateStr= DateUtil.dateStr6(curDate);
		try {
			//--------------------------> 居间服务方(丙方) -初始化签名数据   <--------------------------
			PartyC partyC=new PartyC();
			partyC.setSignOrderNo(borrowAgreement.getOrderNo());
			partyC.setBorMoney(String.valueOf(borrowAgreement.getRealAmount()));
			partyC.setBorMoneyCapital(NumberConvertCNUtil.numberToCNMontray(borrowAgreement.getRealAmount()));
			partyC.setBorDays(borrowAgreement.getTimeLimit());
			//借款开始日期(YYYY年-MM月-DD日)
			Date borStartDate=DateUtil.parse(borrowAgreement.getLoanTime(), DateUtil.YYYY_MM_DD);
			String borStartDateStr=DateUtil.dateStr6(borStartDate);
			partyC.setBorStartDate(borStartDateStr);
			//借款到期日期(YYYY年-MM月-DD日)
			Date borEndDate= DateUtil.parse(borrowAgreement.getRepayTime(),DateUtil.YYYY_MM_DD);
			String borEndDateStr=DateUtil.dateStr6(borEndDate);
			partyC.setBorEndDate(borEndDateStr);
			//印章:(如果印章已经上传、这里只需要填写注册时的邮箱即可)
			String servicePartySignature=Global.getValue(Constant.COMPANY_EMAIL);  
			partyC.setServicePartySignature(servicePartySignature);
			partyC.setServicePartySignatureDate(curDateStr);
			Integer borType=borrowMain.getBorrowType();
			//借款人用途-borMain中取-需要查询字典-进行转换
			SysDictDetail dictDetail= sysDictDetailMapper.getDetailByTypeCodeWithItemCode(SysDictEnum.TYPE_CODE.BORROW_TYPE.getCode(), borType+"");
			BorrowIntent borrowIntent= HandleBorrowIntent.dictDetailTransformBorrowIntent(dictDetail);
			partyC.setBorIntent(null==borrowIntent?"":borrowIntent.getData().getTitle());
			partyC.setBorAnnualRate(Global.getValue(EnjoysignConstant.BOR_ANNUAL_RATE));//借款年化
			partyC.setBorServiceFee(Global.getValue(EnjoysignConstant.BOR_SERVICE_FEE));//借款人服务费
			partyC.setPenaltyRate(Global.getValue(EnjoysignConstant.PENALTY_RATE));//罚款利率
			TemplateInfoModel template=new TemplateInfoModel().parseTemplateInfo(borrowMain.getAmount(),borrowMain
					.getTemplateInfo());
			partyC.setTotalStages(template.getStages()+"");//总分期数 -borMain
			partyC.setStagesDays(template.getCycle()+"");//每期天数-borMain
			// ----------------------------------------> 分期明细 【】  ---------------------------------------->
			List<BorrowRepay>  borRepayList= borrowRepayMapper.queryBorrowRepayByBorMainId(borrowMain.getId());
			List<String> stageDateKeys= EnumUtil.getCodesByClz(StagesFieldEnum.STAGES_NUMBER.class, "StagesDate");
			List<String> stagesInterestKeys=EnumUtil.getCodesByClz(StagesFieldEnum.STAGES_NUMBER.class, "StagesInterest");
			List<Object> stagesDateValues=new LinkedList<>();//还款日
			List<Object> stagesInterestValues=new LinkedList<>();//还款本息
			for(BorrowRepay repay:borRepayList){
				stagesDateValues.add(DateUtil.dateStr6(repay.getRepayTime()));
				stagesInterestValues.add(repay.getAmount());
			}
			stagesDateValues=inFillByObjects(stagesDateValues, "\\", 12);
			stagesInterestValues=inFillByObjects(stagesInterestValues, "\\", 12);
			partyC=(PartyC) ReflectUtil.setObjectValue(partyC, stageDateKeys, null, stagesDateValues);
			partyC=(PartyC) ReflectUtil.setObjectValue(partyC, stagesInterestKeys, null, stagesInterestValues);
			//--------------------------> 出借方(乙方) -初始化签名数据  <--------------------------
			PartyB partyB=new PartyB();
			partyB.setLenderName(Global.getValue(Constant.BUSINESS_ENTITY));
			partyB.setLenderIDCard(Global.getValue(Constant.BUSINESS_ENTITY_IDCARD));
			partyB.setLenderPartySignatureDate(curDateStr);
			partyB.setLenderPartySign(Global.getValue(Constant.BUSINESS_ENTITY));
			//--------------------------> 借款方(甲方) -初始化签名数据  《--------------------------
			PartyA partyA=new PartyA();
			partyA.setBorName(borrowAgreement.getRealName());
			partyA.setBorIDCard(borrowAgreement.getIdNo());
			partyA.setBorPhone(borrowAgreement.getPhone());
			partyA.setBorEmail(borrowAgreement.getEmail());
			partyA.setBorSignatureDate(curDateStr);
			partyA.setBorSignature(borrowAgreement.getRealName());
			partyData=new PartyData(partyA,partyB,partyC);
		} catch (Exception e) {
			logger.error("------------------>初始化三方签署数据时出错.",e);
		}
		return partyData;
	}
	
	@Override
	public int saveEnjoysignRecord(EnjoysignRecord enjoysignRecord) {
		int count=enjoysignRecordMapper.save(enjoysignRecord);
		logger.info("------------------------>记录发起1号签签署后台自动签章请求入库结束，受影响的行数是:{}条...",new Object[]{count});
		return count;
	}

	@Override
	public int updateSelective(EnjoysignRecord enjoysignRecord) {
		if(null==enjoysignRecord) return 0;
		return enjoysignRecordMapper.updateSelective(enjoysignRecord);
	}

	@Override
	public String getOrderIdByBorrowIdWithStatus(List<Integer> statusList,String borrowMainId) {
		if(StringUtil.isEmpty(borrowMainId) || null==statusList || statusList.size()==0) return null;
		boolean swithOpen=EnjoysignUtil.isSwithOpen();
		if(!swithOpen){
			return null;
		}
		EnjoysignRecord enjoysign= enjoysignRecordMapper.getEnjoysignRecordByBorrowIdWithStatus(statusList, borrowMainId);
		if(null==enjoysign) return null;
//		return enjoysign.getOrderId();
		return "";
	}

	@Override
	public String getRemoteContractURLByOrderId(List<Integer> statusList, String borrowMainId) {
		boolean swithOpen=EnjoysignUtil.isSwithOpen();
		if(!swithOpen){
			return null;
		}
		String orderId=getOrderIdByBorrowIdWithStatus(statusList, borrowMainId);
		if(StringUtil.isEmpty(orderId)) return null;
		return EnjoysignUtil.getContractURLByOrderId(orderId);
	}

	/**
	 * <p>填充分期合同中的值</p>
	 * @param objects 待处理的对象
	 * @param fill 填充值
	 * */
	public static List<Object> inFillByObjects(List<Object> objects,String fill,int count){
		if(null==objects|| objects.size()==0 || count<=0) return objects;
		if(objects.size()<count){
			objects.add(fill);
			inFillByObjects(objects, fill, count);
		}
		return objects;
	}

}
