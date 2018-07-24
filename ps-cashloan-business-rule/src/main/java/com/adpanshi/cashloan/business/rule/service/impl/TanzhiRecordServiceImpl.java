package com.adpanshi.cashloan.business.rule.service.impl;

import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.business.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.business.core.common.util.OrderNoUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.BorrowMain;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.model.TianChuangResponse;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.business.rule.domain.BorrowRuleResult;
import com.adpanshi.cashloan.business.rule.domain.TanzhiRecord;
import com.adpanshi.cashloan.business.rule.mapper.TanzhiRecordMapper;
import com.adpanshi.cashloan.business.rule.service.TanzhiRecordService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//import com.fans.api.tanzhi.TanzhiClient;
//import com.fans.api.tanzhi.TanzhiReportParams;

/**
 * @author qing.yunhui 
 * @Since 2011-2018
 * @create 2018-03-16 17:29:30
 * @history
 */
@Service("tanzhiRecordService")
public class TanzhiRecordServiceImpl extends BaseServiceImpl<TanzhiRecord,Long> implements TanzhiRecordService{

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
    private TanzhiRecordMapper tanzhiRecordMapper;
	
	@Resource
	UserBaseInfoService userBaseInfoService;
	
	@Override
	public BaseMapper<TanzhiRecord, Long> getMapper() {
		return tanzhiRecordMapper;
	}

	@Override
	public TanzhiRecord multiplebonds(BorrowMain borrowMain) {
		String resId=OrderNoUtil.geUUIDOrderNo();
		TanzhiRecord record=new TanzhiRecord(borrowMain.getUserId(), borrowMain.getId(), resId, 1);
		try {
			String account =Global.getValue("sms_account");//"shanyin456";// 用户名（必填）-
			String secret = Global.getValue("sms_secret");//"F4M8aUzJ7v8tHstPS3ewLA==";// 密码（必填）
			String saasRequestURL =Global.getValue("sms_masterUrl");
//			TanzhiClient TanzhiClient = new TanzhiClient(saasRequestURL);
//			TanzhiReportParams params = new TanzhiReportParams();
//			params.setUserId(record.getUserId());//非必填 reqId
//			params.setOutId(record.getResId());//流水号-非必填  resId
			UserBaseInfo baseInfo=userBaseInfoService.findByUserId(borrowMain.getUserId());
//			params.setMobile(baseInfo.getPhone());
//			params.setUsername(baseInfo.getRealName());
//			params.setIdcard(baseInfo.getIdNo());
//			String result = TanzhiClient.getMultipleBondsReport(account,secret,params);
			String result = "";
			if(StringUtil.isNotEmptys(result)){
				TianChuangResponse response=JSONObject.parseObject(result, TianChuangResponse.class);
				if(response.isSuccess()){
					String data=response.getData();
					JSONObject jsonObj=JSONObject.parseObject(data);
					String reportInfo=jsonObj.getString("report_info");
					String basicInfo=jsonObj.getString("basic_info");
					String relationPhoneInfos=jsonObj.getString("relationPhone_infos");
					record.setBasicInfo(basicInfo);
					record.setCode(response.getCode());
					record.setMsg(response.getMsg());
					record.setRelationPhoneInfos(relationPhoneInfos);
					record.setReportInfo(reportInfo);
					//mbInfos -> 多头借贷分析(当前版本只有一个元素)
					String mbInfos=jsonObj.getString("mb_infos");
					JSONArray mbInfosArray=JSONObject.parseArray(mbInfos);
					JSONObject object=mbInfosArray.getJSONObject(0);
					String phoneNumber=object.getString("phone_number");
					String creditInfo=object.getString("credit_info");
					JSONObject creditInfoObj=JSONObject.parseObject(creditInfo);
					String sections=creditInfoObj.getString("sections");
					String eveSums=creditInfoObj.getString("eveSums");
					String platformInfos=creditInfoObj.getString("platform_Infos");
					String refInfos=creditInfoObj.getString("refInfos");
					record.setSection(sections);
					record.setRefInfos(refInfos);
					record.setPlatformInfos(platformInfos);
					record.setPhoneNumber(phoneNumber);
					record.setEveSums(eveSums);
				}
			}
		} catch (Exception e) {
			logger.error("【调用探知征信接口】异常.",e);
		}
		return record;
	}

	@Override
	public String executeByMultiplebonds(BorrowMain borrowMain) {
		try {
			TanzhiRecord record= multiplebonds(borrowMain);
			if(null!=record&&"0".equals(record.getCode())){
				tanzhiRecordMapper.save(record);
				logger.info("==============> 【探知征信接口】需求要求先空跑一段时间、暂不做决策、调用探知接口后直接放行[通过].");
			}
		} catch (Exception e) {
			logger.error("【调用探知征信接口】异常!",e);
		}
		return BorrowRuleResult.RESULT_TYPE_PASS;
	}
}