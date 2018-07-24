package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.ShardTableUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.domain.UserOtherInfo;
import com.adpanshi.cashloan.business.core.mapper.UserBaseInfoMapper;
import com.adpanshi.cashloan.business.core.mapper.UserOtherInfoMapper;
import com.adpanshi.cashloan.business.rc.domain.TppBusiness;
import com.adpanshi.cashloan.business.rule.domain.*;
import com.adpanshi.cashloan.business.rule.extra.tongdun.api.ExtraTdRiskApi;
import com.adpanshi.cashloan.business.rule.mapper.*;
import com.adpanshi.cashloan.business.rule.model.TongdunReqLogModel;
import com.adpanshi.cashloan.business.rule.model.srule.model.Formula;
import com.adpanshi.cashloan.business.rule.model.tongdun.model.PreloanApplyModel;
import com.adpanshi.cashloan.business.rule.service.BorrowRuleResultService;
import com.adpanshi.cashloan.business.rule.service.TongdunReqLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("tongdunReqLogService")
public class TongdunReqLogServiceImpl extends BaseServiceImpl<TongdunReqLog, Long> implements TongdunReqLogService {

	private static final Logger logger = LoggerFactory.getLogger(TongdunReqLogServiceImpl.class);

	@Resource
	private BorrowRuleResultService borrowRuleResultService;

	@Resource
	private TongdunReqLogMapper tongdunReqLogMapper;
	
	@Resource
	private UserEmerContactsMapper userEmerContactsMapper;
	
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	
	@Resource
	private ClBorrowMapper clBorrowMapper;
	
	@Resource
	private BankCardMapper bankCardMapper;

	@Resource
	private UserOtherInfoMapper userOtherInfoMapper;
	
	@Resource
	private UserEquipmentInfoMapper userEquipmentInfoMapper;
	
	@Resource
	private TongdunRespDetailMapper tongdunRespDetailMapper;

	@Override
	public BaseMapper<TongdunReqLog, Long> getMapper() {
		return null;
	}

	@Override
	public String extraTdRiskRequest(Long userId, BorrowMain borrow, TppBusiness bussiness, String mobileType, boolean
			reBorrowUser) {
		logger.info("TongdunReqLogServiceImpl-extraRiskRequest()-同盾决策引擎接口调用参数-userId："+userId+"-borrow:"+JSONObject.toJSONString(borrow)
		+"-bussiness:"+JSONObject.toJSONString(bussiness)+"-mobileType:"+mobileType+"-reBorrowUser:"+reBorrowUser);
		if(userId == 0 || borrow == null || bussiness == null){//校验参数
			logger.error("TongdunReqLogServiceImpl-extraRiskRequest()-同盾决策引擎接口调用参数不正确");
			return null;
		}
		// 组装请求参数
		Map<String,String> params = this.initExtraTdRiskRequestParams(userId, borrow,reBorrowUser);
		TongdunReqLog log = new TongdunReqLog();
		log.setOrderNo(OrderNoUtil.geUUIDOrderNo());
		log.setBorrowId(borrow.getId());
		log.setUserId(userId);
		log.setCreateTime(new Date());
		ExtraTdRiskApi api = new ExtraTdRiskApi();
		boolean testState=false;//默认正式环境地址
		if("dev".equals(Global.getValue("app_environment"))){
			testState = true;//测试环境
			logger.info("TongdunReqLogServiceImpl-extraTdRiskRequest()-测试环境-testState："+testState);
		}
		ExtraTdRiskApi.EnumMobileType enumTemp = ExtraTdRiskApi.EnumMobileType.WEB;
		if(mobileType.equals("1")){
			enumTemp = ExtraTdRiskApi.EnumMobileType.IOS;
		}else if(mobileType.equals("2")){
			enumTemp = ExtraTdRiskApi.EnumMobileType.ANDROID;
		}else{
			enumTemp = ExtraTdRiskApi.EnumMobileType.WEB;
		}
		//默认人审
		String resultFinal = BorrowRuleResult.RESULT_TYPE_REVIEW;
		try {
			String result = api.request(params, bussiness, testState,enumTemp);
			logger.info("借款borrowId："+borrow.getId()+"，同盾决策引擎审核，同步响应结果："+result);
			if (StringUtils.isNotEmpty(result)) {//同盾请求结果
				Map<String, Object> resultMap = JSONObject.parseObject(result,Map.class);
				String seqId = String.valueOf(resultMap.get("seq_id"));// 本次调用的请求id，用于事后反查事件
				String finalDecision = String.valueOf(resultMap.get("final_decision"));// 结果有三种：Accept无风险，通过；Review低风险，审查；Reject高风险，拒绝
				finalDecision = PreloanApplyModel.getMessage(finalDecision);//转换为中文字符
				log.setSubmitCode(String.valueOf(resultMap.get("success")));//  本次调用是否成功，true表示成功调用
				log.setSubmitParams(result);
				log.setReportId(seqId);
				log.setUpdateTime(new Date());
				if (Boolean.parseBoolean(resultMap.get("success").toString())) {//请求成功
					//获取同盾分
					int reScore=Integer.valueOf(String.valueOf(resultMap.get("final_score")));
					//同盾分>=80，拒绝处理,否则通过
					if(reScore >= 80){
						resultFinal = BorrowRuleResult.RESULT_TYPE_REFUSED;
					}else{
						resultFinal = BorrowRuleResult.RESULT_TYPE_PASS;
					}
					log.setQueryCode(String.valueOf(resultMap.get("success")));
					log.setState("20");
					log.setRsScore(reScore);// 最终的风险系数。权重模式下是策略中所有命中规则分数相加，首次匹配和最坏匹配时则由其中一条
					log.setRsState(finalDecision);
				} else {
					//@remarks: 修复同盾返回错误信息.异常问题. @date:20170801 @author:nmnl
					if(resultMap.get("reason_code") != null){
						String reasonCode = String.valueOf(resultMap.get("reason_code"));
						logger.info(userId + " 同盾决策引擎接口查询失败 " + reasonCode);
						log.setQueryCode(reasonCode.substring(0,3));// 如果success为false，此处显示错误码
					}
					log.setState("10");
					log.setRsScore(0);
					log.setRsState("同盾决策引擎接口查询失败-返回错误码："+log.getQueryCode()+"-seqId:"+seqId);
				}
				String tableName= ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
				int k = tongdunReqLogMapper.save(tableName,log);
				if(k > 0){
					//查询审核报告结果
					String report = api.report(bussiness, testState, seqId);
					TongdunRespDetail detail=new TongdunRespDetail();
					detail.setReqId(log.getId());
					detail.setOrderNo(log.getOrderNo());
					detail.setReportId(seqId);
					detail.setQueryParams(report);
					String tableNameDetail= ShardTableUtil.getTongdunRespDetailTableName(borrow.getId(),borrow.getCreateTime());
					tongdunRespDetailMapper.save(tableNameDetail,detail);
				}
			} else {
				log.setState("10");
				log.setRsScore(0);
				log.setRsState("同盾决策引擎接口异常-无返回结果");
				String tableName=ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
				tongdunReqLogMapper.save(tableName,log);
			}
		} catch (Exception e) {
			logger.error("saas风控风控请求出错,userId: " + userId, e.getMessage());
			log.setState("10");
			log.setRsScore(0);
			log.setRsState("同盾决策引擎接口查询失败-出现其他异常");
			String tableName=ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
			tongdunReqLogMapper.save(tableName,log);
		}
		//保存结果
		borrowRuleResultService.saveResultFilled(borrow.getId(),12L,resultFinal,"cl_tongdun_req_log","同盾第三方请求记录表","rs_score",
				"同盾分", Formula.include.getIndex());
		return resultFinal;
	}

	/**
	 * 同盾认证请求参数拼接

	 * @param userId
	 * @param bussiness
	 * @param mobileType
	 * @return
	 */
	/*@Override
	@Deprecated
	public int preTongdunAuthApplyRequest(Long userId,TppBusiness bussiness,String mobileType){
		//查询各种配置参数
		String borrowDay = Global.getValue("borrow_day");// 借款天数
		String[] days = borrowDay.split(",");
		int maxDays = Integer.parseInt(days[days.length-1]);// 最大借款期限
		int minDays = Integer.parseInt(days[0]);			// 最小借款期限
		String borrowCredit = Global.getValue("borrow_credit");// 借款额度
		String[] credits = borrowCredit.split(",");
		double maxCredit = Double.parseDouble(credits[credits.length-1]);// 最大借款额度
		double minCredit = Double.parseDouble(credits[0]);//最小借款额度

		//构造虚假borrow
		BorrowMain borrow = new BorrowMain();
		borrow.setAmount(maxCredit);//最大借款额度
		borrow.setTimeLimit(maxDays+"");//最大借款期限
		borrow.setCreateTime(new Date());
		borrow.setIp("");//没有IP

		int m=0;
		// 组装请求参数
		PreloanApplyModel model = initModel(userId,borrow);
		TongdunReqLog log = new TongdunReqLog();
		log.setOrderNo("auth_"+OrderNoUtil.getSerialNumber());//orderNo订单号个性化，同盾认证OrderNo
		//log.setBorrowId(borrow.getId()); //此处没有借款ID
		log.setUserId(userId);
		log.setCreateTime(new Date());

		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("body", JSON.toJSONString(model));

		PreloanApi api = new PreloanApi();
		boolean testState=false;//默认正式环境地址
		if(Global.getValue("tongdun_url_state")!=null){
			testState="0".equals(Global.getValue("tongdun_url_state"));
		}

		String result = api.preloan(payload, bussiness, testState, mobileType);
		logger.info("同盾贷前审核，同步响应结果："+result);
		if (StringUtil.isNotBlank(result)) {
			Map<String, Object> resultMap = JSONObject.parseObject(result,Map.class);
			log.setSubmitCode(String.valueOf(resultMap.get("code")));
			log.setSubmitParams(result);
			if ("200".equals(String.valueOf(resultMap.get("code")))) {
				log.setState("10");
				JSONObject data = (JSONObject) resultMap.get("data");
				String reportId = String.valueOf(data.get("report_id"));
				log.setReportId(reportId);
				String tableName=ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
				tongdunReqLogMapper.save(tableName,log);
				//查询审核报告结果
				final TppBusiness b = tppBusinessMapper.findByNid("TongdunPreloan", bussiness.getTppId());
				final String report_id = log.getReportId();
				m=reportTask(report_id,b,mobileType);
			} else {
				log.setState("10");
				log.setRsScore(0);
				if(!StringUtil.isBlank(resultMap.get("message"))){
					log.setRsState(String.valueOf(resultMap.get("message")));
				}else{
					log.setRsState("查询接口异常");
				}
				String tableName=ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
				tongdunReqLogMapper.save(tableName,log);
			}
		} else {
			log.setState("10");
			log.setRsScore(0);
			log.setRsState("查询接口异常");
			String tableName=ShardTableUtil.getTongdunReqLogTableName(borrow.getId(),borrow.getCreateTime());
			tongdunReqLogMapper.save(tableName,log);
		}
		return m;
	}*/

	/*@Override
	public String preloanReportRequest(String report_id,TppBusiness bussiness,String mobileType) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("report_id", report_id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("body", JSON.toJSONString(model));
	 
			PreloanApi api = new PreloanApi();
			boolean testState=false;//默认正式环境地址
			if(Global.getValue("tongdun_url_state")!=null){
				 testState="0".equals(Global.getValue("tongdun_url_state"));
			}
			PreloanReportResponse response= api.preloanReport(map,bussiness,testState,mobileType);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("reportId", 1);
			paramMap.put("tableName",ShardTableUtil.getTongdunReqLogTableName(1L,new Date()));
			TongdunReqLog log = tongdunReqLogMapper.findSelective(paramMap);
			
			if (response.getSuccess()) {
				String result = response.postResponseToJsonStr();
				int m = saveReport(result, report_id);
				logger.info(result);
				return m == 1 ? "保存成功" : "保存失败";
			} else {
				JSONObject result = response.getData();
				logger.info(result.getString("reason_desc"));
				log.setUpdateTime(new Date());
				log.setQueryCode(String.valueOf(result.get("reason_code")));
				log.setState("30");
				Borrow borrow=clBorrowMapper.findByPrimary(log.getBorrowId());
				String tableName=ShardTableUtil.getTongdunReqLogTableName(log.getBorrowId(),borrow.getCreateTime());
				int m = tongdunReqLogMapper.update(tableName,log);
				if (m > 0) {
					TongdunRespDetail detail=new TongdunRespDetail();
					detail.setReqId(log.getId());
					detail.setOrderNo(log.getOrderNo());
					detail.setReportId(report_id);
					detail.setQueryParams(result.toJSONString());
					String tableNameDetail= ShardTableUtil.getTongdunRespDetailTableName(log.getBorrowId(),borrow.getCreateTime());
					tongdunRespDetailMapper.save(tableNameDetail,detail);
				}
				return result.getString("reason_desc");
			}
		 
	}*/

	/**
	 * 轮询查询审核报告的结果
	 * @param report_id
     * @param bussiness
     * @param mobileType
	 * @return
	 * @throws Exception
	 */
	/*public int reportTask(String report_id,TppBusiness bussiness,String mobileType){
		 int m = 0;
		 String result = task(report_id,bussiness,mobileType);
		 if (!StringUtil.isBlank(result)) {
			m = saveReport(result, report_id);
		 }else{
			 logger.debug("没有查询出同盾审核报告结果"); 
		 }
		return m;
	}*/

	/*public String task(final String reportId,final TppBusiness bussiness,final String mobileType)  {
		logger.debug("执行轮询查询preloan===" + reportId);
		String report = "";
		*//* 轮询调用preloanReport接口，直到获取到结果信息 *//*
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("report_id", reportId);
		FutureTask<String> task = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				while (true) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("body", JSON.toJSONString(model));
					PreloanApi api = new PreloanApi();
					boolean testState="0".equals(String.valueOf(Global.getValue("tongdun_url_state")));
					PreloanReportResponse response= api.preloanReport(map,bussiness,testState,mobileType);
					if (response.getSuccess()) {
						String result = response.postResponseToJsonStr();
						return result;
					} else {
						String reasonCode = response.getData().getString("reason_code");
						if (!"204".equals(reasonCode)) {*//*204代表订单还未生成，需要继续轮询，其他状态说明查询已经失败 *//*
							logger.error("返回参数:"+response.getData().toJSONString());
							logger.error("code:"+reasonCode+",message:"+response.getData().getString("reason_desc"));
							throw new BaseRuntimeException("{'code':'"+reasonCode+"','message':'",response.getData().getString("reason_desc")+"'");
						}
					}
				}
			}
		});
		new Thread(task).start();

		while (!task.isDone()) {
			try {
				logger.info("查询同盾审核报告开始...");
				Thread.sleep(30000); // 单位毫秒  暂停30秒再执行
			} catch (InterruptedException e) {
				//logger.error("同盾审核报告查询异常或超时..."+reportId,e);
			}
		}
		try {
			report = task.get(300, TimeUnit.SECONDS);//300秒内如果没有结果返回继续轮询
			logger.info("查询同盾审核报告结束...");
		} catch (BaseRuntimeException e) {
			logger.error("同盾返回:"+e.getMessage());
			report=e.getMessage();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			logger.error("同盾审核报告查询异常或超时..."+reportId,e.getMessage());
			report="{'code':'404','message':'查询异常或超时'}";
		} finally {
			task.cancel(true);
		}

		return report;
	}*/
	/**
	 * 更新同盾第三方请求记录
	 * 更新征信管理信息以及审核结果
	 * @param result
	 * @param report_id
	 * @return
	 */
	/*public int saveReport(String result,String report_id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("reportId", report_id);
		paramMap.put("tableName",ShardTableUtil.getTongdunReqLogTableName(1L,new Date()));
		TongdunReqLog log = tongdunReqLogMapper.findSelective(paramMap);
		Map<String, Object> resultMap = JSONObject.parseObject(result,Map.class);
		log.setUpdateTime(new Date());
		log.setQueryCode(String.valueOf(resultMap.get("code")));
		if(log.getQueryCode().equals("200")){
			log.setState("20");
			JSONObject data = (JSONObject) resultMap.get("data");
			log.setRsScore(Integer.valueOf(String.valueOf(data.get("final_score"))));
			log.setRsState(PreloanApplyModel.getMessage(String.valueOf(data.get("final_decision"))));
		}else{
			log.setState("30");
			log.setRsScore(0);
			log.setRsState("建议审核");
		}
		Borrow borrow=clBorrowMapper.findByPrimary(log.getBorrowId());
		//更新同盾第三方请求记录
		String tableName=ShardTableUtil.getTongdunReqLogTableName(log.getBorrowId(),borrow.getCreateTime());
		int m = tongdunReqLogMapper.update(tableName,log);
		if(m>0){
			TongdunRespDetail detail=new TongdunRespDetail();
			detail.setReqId(log.getId());
			detail.setOrderNo(log.getOrderNo());
			detail.setReportId(report_id);
			detail.setQueryParams(result);
			String tableNameDetail= ShardTableUtil.getTongdunRespDetailTableName(1L,new Date());
			tongdunRespDetailMapper.save(tableNameDetail,detail);
		}
	    return m;
	}*/
 
	/**
	 * 拼装同盾审核请求信息
	 * @param userId
	 * @param borrow
	 * @return
	 */
	/*public PreloanApplyModel initModel(Long userId, BorrowMain borrow){
		PreloanApplyModel model = new PreloanApplyModel();
		//用户基本信息
		UserBaseInfo info = userBaseInfoMapper.findByUserId(userId);
		model.setName(info.getRealName());
		model.setMobile(info.getPhone());
		model.setId_number(info.getIdNo());
		model.setWork_phone(info.getCompanyPhone());
		model.setWork_time(info.getWorkingYears());
		model.setCompany_name(info.getCompanyName());
		model.setCompany_address(info.getCompanyAddr());
		model.setAnnual_income(info.getSalary());
		model.setDiploma(info.getEducation());
		model.setMarriage(info.getMarryState());
		model.setRegistered_address(info.getIdAddr());
		model.setHome_address(info.getLiveAddr());
		
		//银行卡信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		BankCard card = bankCardMapper.findSelective(paramMap);
		if (card != null) {
			model.setCard_number(card.getCardNo());
		}
	 
		//其他信息
		UserOtherInfo otherInfo = userOtherInfoMapper.findSelective(paramMap);
		if(otherInfo!=null){
			model.setQq(otherInfo.getQq());
			model.setEmail(otherInfo.getEmail());
		}
		//借款信息
		model.setLoan_amount(borrow.getAmount());
		model.setLoan_term(Integer.valueOf(borrow.getTimeLimit()));
		model.setLoan_term_unit("DAY");
		model.setLoan_date(DateUtil.dateStr(borrow.getCreateTime(),DateUtil.DATEFORMAT_STR_002));
		//begin pantheon 20170614 如果没有ip，不设置
		if(StringUtils.isNotEmpty(borrow.getIp())){
			model.setIp_address(borrow.getIp());
		}
		//end
		//是否实名认证
		UserAuth auth = userAuthMapper.findSelective(paramMap);
		if(auth!=null){
		  model.setIs_id_checked("30".equals(auth.getIdState()));
		}
		//联系人信息
		List<UserEmerContacts> contacts = userEmerContactsMapper.listSelective(paramMap);
		if (contacts != null) {
			for(int i=0;i<contacts.size();i++){
				UserEmerContacts c=contacts.get(i);
				if(i==0){
					 model.setContact1_name(c.getName());
					 model.setContact1_mobile(c.getPhone());
					 model.setContact1_relation(c.getRelation());
				}else if(i==1){
					 model.setContact2_name(c.getName());
					 model.setContact2_mobile(c.getPhone());
					 model.setContact2_relation(c.getRelation());
				}else if(i==2){
					 model.setContact3_name(c.getName());
					 model.setContact3_mobile(c.getPhone());
					 model.setContact3_relation(c.getRelation());
				}else if(i==3){
					 model.setContact4_name(c.getName());
					 model.setContact4_mobile(c.getPhone());
					 model.setContact4_relation(c.getRelation());
				}else if(i==4){
					 model.setContact5_name(c.getName());
					 model.setContact5_mobile(c.getPhone());
					 model.setContact5_relation(c.getRelation());
			    }
			}
		}
		//设备指纹
		UserEquipmentInfo equipmentInfo =userEquipmentInfoMapper.findSelective(paramMap);
		if(equipmentInfo!=null){
			model.setBlack_box(equipmentInfo.getBlackBox());
			
		}
		
		return model;
	}*/


	/**
	 * 拼装同盾审核请求信息，不能使用复杂对象
	 * @param userId
	 * @param borrow
	 * @param reBorrowUser     是否是复借用户   新客户传入0  老客户传入1
	 * @return
	 */
	public Map<String,String> initExtraTdRiskRequestParams(Long userId, BorrowMain borrow,boolean reBorrowUser){
		Map<String,String> result = new HashMap<String,String>();

		//1.新老用户
		int ext_regular_customer = reBorrowUser ? 1 : 0 ;//是否是复借用户   新客户传入0  老客户传入1
		result.put("ext_regular_customer", ext_regular_customer+"");


		//2.设备指纹
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserEquipmentInfo equipmentInfo =userEquipmentInfoMapper.findSelective(paramMap);
		if(equipmentInfo != null && StringUtils.isNotEmpty(equipmentInfo.getBlackBox())){
			result.put("black_box", equipmentInfo.getBlackBox());
		}

		//3.可选支持API实时返回设备或解析信息
		result.put("resp_detail_type", "device&geoip&attribution&credit_score&application_id");

		//4.refer_cust 	网页端请求来源 可选仅限网页(包括WAP、HTML5、微信公众号)js方式对接
		//5.可选 事件发生的真实时间 格式：2014-03-01 08:16:30
		result.put("event_occur_time", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));


		//6.借贷信息
//		result.put("apply_channel",null);//进件渠道
//		result.put("apply_province",null);//进件省份
//		result.put("apply_city",null);//进件城市
		if(borrow.getAmount() == null)     borrow.setAmount(Double.parseDouble("1000"));
		result.put("loan_amount",borrow.getAmount().toString());//贷款金额======

//		result.put("loan_purpose",null);//借款用途
		if(StringUtils.isEmpty(borrow.getTimeLimit()))    borrow.setTimeLimit("14");
		result.put("loan_term",borrow.getTimeLimit());//贷款期限

		result.put("loan_term_unit","DAY");//贷款期限
		if(borrow.getCreateTime() == null)  borrow.setCreateTime(new Date());
		result.put("loan_date",DateUtil.dateStr(borrow.getCreateTime(),DateUtil.DATEFORMAT_STR_002));//贷款日期

//		result.put("is_cross_loan",null);//是否曾跨平台借款

		if(StringUtils.isEmpty(borrow.getIp())) borrow.setIp("");
		result.put("ip_address",borrow.getIp());//ip

		//用户基本信息
		UserBaseInfo info = userBaseInfoMapper.findByUserId(userId);
		result.put("work_time",info.getWorkingYears());//工作时间
//		result.put("monthly_income",null);//月均收入
		result.put("annual_income",info.getSalary());//年均收入
		result.put("work_phone",info.getCompanyPhone());//借款人单位座机
//		result.put("applyer_type",null);//人员类别
//		result.put("org_code",null);//借款人组织机构代码
//		result.put("biz_license",null);//工商注册号

//		result.put("house_type",null);//房产类型
//		result.put("house_property",null);//房产情况

		result.put("account_name",info.getRealName());//字段借款人姓名
//		result.put("occupation",null);//职位
//		result.put("career",null);//职业
//		result.put("industry",null);//所属行业
//		result.put("company_type",null);//公司性质
		result.put("organization",info.getCompanyName());//借款人工作单位
		result.put("organization_address",info.getCompanyAddr());//借款人工作单位地址
		result.put("marriage",info.getMarryState());//婚姻情况
		result.put("diploma",info.getEducation());//学历
//		result.put("graduate_school",null);//借款人毕业院校
		result.put("contact_address",info.getRegisterAddr());//通讯地址
		result.put("registered_address",info.getIdAddr());//户籍地址
		result.put("home_address",info.getLiveAddr());//家庭地址

//		result.put("account_phone",null);//借款人座机

		result.put("id_number",info.getIdNo());//借款人身份证
		result.put("account_mobile",info.getPhone());//借款人手机

		//银行卡信息
		Map<String, Object> tempParams = new HashMap<String, Object>();
		tempParams.put("userId", userId);
		BankCard card = bankCardMapper.findSelective(tempParams);
		if (card != null) {
			result.put("card_number",card.getCardNo());//借款人卡号
		}

		//其他信息
		UserOtherInfo otherInfo = userOtherInfoMapper.findSelective(tempParams);
		if(otherInfo!=null){
			result.put("account_email",otherInfo.getEmail());//借款人邮箱
			result.put("qq_number",otherInfo.getQq());//借款人QQ
		}

		//联系人信息
		List<UserEmerContacts> contacts = userEmerContactsMapper.listSelective(paramMap);
		if (contacts != null) {
			for(int i=0;i<contacts.size();i++){
				UserEmerContacts c=contacts.get(i);
				if(i==0){
					result.put("contact1_name",c.getName());//字段第一联系人姓名
					result.put("contact1_mobile",c.getPhone());//第一联系人手机号
					//result.put("contact1_id_number",null);//第一联系人身份证
					result.put("contact1_relation",c.getRelation());//第一联系人社会关系
				}else if(i==1){
					result.put("contact2_name",c.getName());//字段第2联系人姓名
					result.put("contact2_mobile",c.getPhone());//第2联系人手机号
					//result.put("contact2_id_number",null);//第2联系人身份证
					result.put("contact2_relation",c.getRelation());//第2联系人社会关系
				}else if(i==2){
					result.put("contact3_name",c.getName());//字段第3联系人姓名
					result.put("contact3_mobile",c.getPhone());//第3联系人手机号
					//result.put("contact3_id_number",null);//第3联系人身份证
					result.put("contact3_relation",c.getRelation());//第3联系人社会关系
				}else if(i==3){
					result.put("contact4_name",c.getName());//字段第4联系人姓名
					result.put("contact4_mobile",c.getPhone());//第4联系人手机号
					//result.put("contact4_id_number",null);//第4联系人身份证
					result.put("contact4_relation",c.getRelation());//第4联系人社会关系
				}else if(i==4){
					result.put("contact5_name",c.getName());//字段第5联系人姓名
					result.put("contact5_mobile",c.getPhone());//第5联系人手机号
					//result.put("contact5_id_number",null);//第5联系人身份证
					result.put("contact5_relation",c.getRelation());//第5联系人社会关系
				}
			}
		}
		return  result;
	}

	/*@Override
	public Page<TongdunReqLogModel> pageListModel(Map<String, Object> params,
			int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		List<TongdunReqLogModel> resultList = tongdunReqLogMapper.listModelByMap(params);
		return (Page<TongdunReqLogModel>) resultList;
	}*/

	/*@Override
	public Map<String, Object>pageListModelPara(Map<String, Object> params){
		if (null != params && params.size() > 0){
			// 集合不为空则开始递归去除字符串的空格 zy
			for(Map.Entry<String, Object>  entry : params.entrySet())
			{
				if(null != params.get(entry.getKey()) && params.get(entry.getKey()) != "")
					params.put(entry.getKey(), params.get(entry.getKey()).toString().trim());
			}
		}else{
			params=new HashMap<String, Object>();
		}
		//params.put("tableName",ShardTableUtil.TONGDUN_REQ_LOG_TABLE);
		List<String> list=new LinkedList<String>();
		if(params==null||params.get("startTime")==null||params.get("endTime")==null){
			Calendar c=Calendar.getInstance();
			params.put("tableName",ShardTableUtil.TONGDUN_REQ_LOG_TABLE+"_"+c.get(Calendar.YEAR)+"_"+(c.get(Calendar.MONTH)+1));
			params.put("tableNames",list);
			//list.add(ShardTableUtil.TONGDUN_REQ_LOG_TABLE+"_"+c.get(Calendar.YEAR)+"_"+(c.get(Calendar.MONTH)+1));
		}else{
			int startMonth=Integer.valueOf(((String)params.get("startTime")).substring(5,7));
			int endMonth=Integer.valueOf(((String)params.get("endTime")).substring(5,7));
			String startTable=Integer.valueOf(((String)params.get("startTime")).substring(0,4))+"_"+startMonth;
			String endTable=Integer.valueOf(((String)params.get("endTime")).substring(0,4))+"_"+endMonth;
			//list.add(ShardTableUtil.TONGDUN_REQ_LOG_TABLE+"_"+startTable);
			params.put("tableName",(ShardTableUtil.TONGDUN_REQ_LOG_TABLE+"_"+startTable));
			if(startMonth!=endMonth) {
				list.add(ShardTableUtil.TONGDUN_REQ_LOG_TABLE + "_" + endTable);
			}
			if((endMonth-startMonth)>1){
				for(int i=startMonth+1;i<endMonth;i++) {
					list.add(ShardTableUtil.TONGDUN_REQ_LOG_TABLE + "_" +Integer.valueOf(((String)params.get("startTime")).substring(0,4))+"_"+i);
				}
			}
			//判断表名是否存在
			boolean isTableExist=true;
			//判断起始表
			List<String> existTables=tongdunReqLogMapper.showTable(String.valueOf(params.get("tableName")));
			if(existTables!=null&&existTables.size()>0){
				//判断其他表
				for(String existTableName:list){
					existTables=tongdunReqLogMapper.showTable(existTableName);
					if(existTables==null||existTables.size()<=0){
						isTableExist=false;
						break;
					}
				}
			}else{
				isTableExist=false;
			}
			//有不存在的表则查询当前月的数据
			if(!isTableExist){
				logger.info("-----cl_tongdun_req_log对应分表不存在，查询当前月的数据-----");
				list.clear();
				Calendar c=Calendar.getInstance();
				params.put("endTime",new SimpleDateFormat("yyyy-M-dd").format(c.getTime()));
				c.set(Calendar.DAY_OF_MONTH, 1);
				params.put("startTime",new SimpleDateFormat("yyyy-M-dd").format(c.getTime()));
				params.put("tableName",ShardTableUtil.TONGDUN_REQ_LOG_TABLE+"_"+c.get(Calendar.YEAR)+"_"+(c.get(Calendar.MONTH)+1));
			}
		}
		params.put("tableNames",list);
		return params;
	}*/



	
	/**
	 * 去除同盾风控信息中的data字段
	 * @description
	 * @param model
	 * @throws Exception

	 * @return void
	 * @since  1.0.0
	 */
	public void removeData(TongdunReqLogModel model) throws Exception {
		String  text=model.getQueryParams();
		if(StringUtil.isBlank(text)){
			return;
		}
		JSONObject obj = JSON.parseObject(text,Feature.OrderedField);
		JSONObject data = obj.getJSONObject("data");

		JSONArray risk = (JSONArray) (data.get("risk_items"));
		Iterator<Object> it = risk.iterator();
		
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			JSONObject oa = (JSONObject) ob.getJSONObject("item_detail");
			if (oa != null) {
				if (oa.get("frequency_detail_list") != null) {
					JSONArray oc = (JSONArray) (oa.get("frequency_detail_list"));
					if (oc != null) {
						Iterator<Object> ot = oc.iterator();
						while (ot.hasNext()) {
							JSONObject os = (JSONObject) ot.next();
							os.remove("data");  //移除data节点
						}
					}
				}
			}
		}
		 model.setQueryParams(obj.toString());
	}

	@Override
	public TongdunReqLog getReqLoglByBorrowId(BorrowMain borrow) {
		if (borrow == null){
			logger.error("borrow is null.");
			return null;
		}
		Long borrowId = borrow.getId();
		String tableName= ShardTableUtil.getTongdunReqLogTableName(borrowId,borrow.getCreateTime());
		Map<String,Object> map = new HashMap<>();
		map.put("tableName",tableName);
		map.put("borrowId",borrowId);
		return tongdunReqLogMapper.findSelective(map);

	}
	
}
