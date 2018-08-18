package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.service.UserAppsService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.rule.domain.UserApps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 ** @category 抓取用户手机已安装的app应用...
 ** @author qing.yunhui
 ** @email: qingyunhui@smoney.cc
 ** @createTime: 2017年9月11日下午7:25:14
 **/
@Scope("prototype")
@Controller
@RequestMapping("/api/userApps")
public class UserAppsController extends BaseController{
	@Resource
	private UserBaseInfoService userBaseInfoService;
	@Resource
	private DispatchRunDomain dispatchRunDomain;
	@Resource
	private UserEquipmentInfoService userEquipmentInfoService;
	
	Logger logger= LoggerFactory.getLogger(getClass());
	
	@Resource
	UserAppsService userAppsService;
	
	/**
	 * <p>将抓取到的app应用持久化到数据库</p>
	 *@param appJsonData
	 *@param appJsonData 已抓取到的app应用数据集(json串)
	 *@return void 
	 * */
	@RequestMapping("save.htm")
	public void saveUserApps(final String appJsonData) {
		logger.info("--------------->开始将抓取到的app应用持久化到数据库中...");
		Map<String,Object> result=new HashMap<>();
		if(StringUtil.isEmpty(appJsonData) || StringUtil.isEmpty(JSONObject.parseArray(appJsonData, UserApps.class))){
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "未抓取到app应用，跳过业务逻辑.");
			ServletUtils.writeToResponse(response, result);
		}else{
			List<UserApps> apps= JSONObject.parseArray(appJsonData, UserApps.class);
			userAppsService.saveBatchHandle(apps);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");

			if(apps.size()>0) {
				Long userId = apps.get(0).getUserId();
				//获取用户的全名/邮箱号
				ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(userId);
				//获取设备指纹
				UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(userId);
				String blackBox = "null";
				if (userEquipmentInfo != null) {
					blackBox = userEquipmentInfo.getDeviceFinger();
				}
				Map nodeMap = new HashMap();
				nodeMap.put("THIRD_PARTY_METADATA", appJsonData);
				//调起节点
				dispatchRunDomain.startNode(
						managerUserModel.getUserDataId(), "india_oloan_app_application", managerUserModel.getPhone(),
						managerUserModel.getLoginNameEmail(), managerUserModel.getIdNo(), managerUserModel.getRealName(),
						blackBox, nodeMap);
			}
			ServletUtils.writeToResponse(response, result);
		}
	}

}
