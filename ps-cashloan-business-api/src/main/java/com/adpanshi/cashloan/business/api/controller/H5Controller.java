package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.constant.FirstbankConstant;
import com.adpanshi.cashloan.business.cl.enums.AppTypeEnum;
import com.adpanshi.cashloan.business.cl.service.ClSmsService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import com.adpanshi.cashloan.business.rule.extra.tongdun.api.ExtraTdRiskApi;
import com.adpanshi.cashloan.business.system.constant.SysConfigConstant;
import com.adpanshi.cashloan.business.system.domain.SysConfig;
import com.adpanshi.cashloan.business.system.service.SysConfigService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * H5页面Controller

 * @version 1.0.0
 * @date 2017年2月24日 下午4:34:51
 * Copyright 粉团网路 金融创新事业部 此处填写项目英文简称  All Rights Reserved
 *
 *
 */
@Scope("prototype")
@Controller
public class H5Controller extends BaseController{
	public static final Logger logger = LoggerFactory.getLogger(H5Controller.class);
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private ClSmsService clSmsService;
	@Resource
	private CloanUserService cloanUserService;
	
	/**
	 * 获取H5页面清单
	 * @param search
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/h5/list.htm", method = RequestMethod.GET)
	public void list(@RequestParam(value="userId",required=false) String userId) throws Exception {
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		List<SysConfig> list = sysConfigService.listByCode("h5_");
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> pro =new HashMap<>();
			String code=list.get(i).getCode();
			String value=list.get(i).getValue();
			if(SysConfigConstant.H5_ABOUT_US.equals(code)){
				if(value.endsWith(SysConfigConstant.BINDING_PARAMS)){
					if(StringUtil.isNotBlank(userId))value+=userId;
				}
			}
			pro.put("code",code);
			pro.put("value",value);
			pro.put("name",list.get(i).getName());
			dataList.add(pro);
		}
		Map<String,Object> data = new HashMap<>();
		data.put("list", dataList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 生成图片验证码
	 * @param search
	 * @throws Exception
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/h5/imgCode/generate.htm", method = RequestMethod.GET)
	public void generate() throws Exception {
		super.generateImgCode(true,0);
	}
	
	/*
	@RequestMapping(value = "/com.adpanshi.com.adpanshi.cashloan.api/h5/app/latestDownload.htm", method = RequestMethod.GET)
	public void getLatestDownloadUrl(String systemType){
		String check_version = Global.getValue("check_version");
		JSONObject jsonObject = JSONObject.parseObject(check_version);
		JSONObject resultData=null;
		if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.ANDROID.toString())){
			resultData = jsonObject.getJSONObject("android");
		}else if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.IOS.toString())){
			resultData = jsonObject.getJSONObject("ios");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, resultData);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}*/
	
	/**
	 * <p>提供h5下载最新app接口</p>
	 * @param systemType 客户端类型(IOS、Android)
	 * @param appType 类型其值可以为:1.闪银(小额钱袋)，2.银程金服
	 * */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/h5/app/latestDownload.htm", method = RequestMethod.GET)
	@ResponseBody
	public String latestDownload(HttpServletRequest request,HttpServletResponse response,@RequestParam String systemType,Integer appType){
		try {
			String check_version = null;
			if(appType.equals(AppTypeEnum.SHAN_YIN.getCode())){
				check_version=Global.getValue("check_version");//小额钱袋
			}else{
				check_version=Global.getValue("check_version_yc");//银程
			}
			JSONObject jsonObject = JSONObject.parseObject(check_version);
			JSONObject resultData=null;
			String text=null;
			Map<String,Object> returnData=new HashMap<>();
			String jsonp=request.getParameter("callback");//callback为回调函数名，要和js名保持一致
			if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.ANDROID.toString())){
				resultData = jsonObject.getJSONObject("android");
				returnData.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnData.put(Constant.RESPONSE_CODE_MSG, "请求成功");
			}else if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.IOS.toString())){
				resultData = jsonObject.getJSONObject("ios");
				returnData.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				returnData.put(Constant.RESPONSE_CODE_MSG, "请求成功");
			}else{
				returnData.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnData.put(Constant.RESPONSE_CODE_MSG, "下载失败!目前仅提供android、ios下载.");
			}
			if(!StringUtil.isEmpty(resultData)){
				returnData.put("url",resultData.get("downloadUrl"));
			}
			text=jsonp+"("+JSONObject.toJSONString(returnData)+")";
			return text;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		return null;
	}
	
	/**
	 * <p>小额钱袋引导第一钱庄</p>
	 * @param systemType 客户端类型(IOS、Android)
	 * */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/h5/app/firstbank.htm", method = RequestMethod.GET)
	public void firstbank(HttpServletRequest request,HttpServletResponse response,@RequestParam String systemType){
		String firstbankURL = Global.getValue(FirstbankConstant.FIRST_BANK_APP_URL);
		Map<String,Object> returnData=new HashMap<>();
		Map<String,Object> result = new HashMap<String,Object>();
		JSONObject resultData=new JSONObject();
		if(StringUtil.isBlank(firstbankURL)){
			returnData.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnData.put(Constant.RESPONSE_CODE_MSG, "第一钱庄下载地址配置未开启或不存在...");
		}else{
			JSONObject jsonObject = JSONObject.parseObject(firstbankURL);
			if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.ANDROID.toString())){
				resultData = jsonObject.getJSONObject("android");
				returnData.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
			}else if(systemType.equalsIgnoreCase(ExtraTdRiskApi.EnumMobileType.IOS.toString())){
				resultData = jsonObject.getJSONObject("ios");
				returnData.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
			}else{
				returnData.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				returnData.put(Constant.RESPONSE_CODE_MSG, "下载失败!目前仅提供android、ios下载.");
			}
		}
		returnData.put("url",resultData.get("downloadUrl"));
		result.put(Constant.RESPONSE_DATA, returnData);
		ServletUtils.writeToResponse(response,result);
	}

}
