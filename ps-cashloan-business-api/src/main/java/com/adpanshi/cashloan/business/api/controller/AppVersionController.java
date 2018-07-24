package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.Channel;
import com.adpanshi.cashloan.business.cl.domain.ChannelApp;
import com.adpanshi.cashloan.business.cl.service.ChannelAppService;
import com.adpanshi.cashloan.business.cl.service.ChannelService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * app版本控制

 * @version 1.0
 * @date 2017年5月15日
 *
 *
 *
 *
 */
@Scope("prototype")
@Controller
public class AppVersionController extends BaseController {
	
	@Resource
	private ChannelAppService channelAppService;

	@Resource
	private ChannelService channelService;
	
	@RequestMapping(value = "/com/adpanshi/cashloan/api/app/checkVersion.htm", method = RequestMethod.POST)
	public void listVersion() {
		List<Channel> listChannel = channelService.listChannel();
		List<ChannelApp> listChannelApp = channelAppService.listChannelApp();
		Map<String, Object> data = new HashMap<String, Object>();
		for (Channel channel : listChannel) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for (ChannelApp channelApp : listChannelApp) {
				if (channel.getId().equals(channelApp.getChannelId()) && channel.getState().equals("10")
						&& channelApp.getState().equals("10")) { //渠道和app信息都是启用状态
					Map<String,Object> map = new HashMap<String,Object>();
					ChannelApp appVersion =  new ChannelApp();
					if (channelApp.getAppType().equals("10")) {
						appVersion.setAppType(channelApp.getAppType());
						appVersion.setlatestVersion(channelApp.getlatestVersion());
						appVersion.setMinVersion(channelApp.getMinVersion());
						appVersion.setDownloadUrl(channelApp.getDownloadUrl());
						map.put("android", appVersion);
					} else if (channelApp.getAppType().equals("20")) {
						appVersion.setAppType(channelApp.getAppType());
						appVersion.setlatestVersion(channelApp.getlatestVersion());
						appVersion.setMinVersion(channelApp.getMinVersion());
						appVersion.setDownloadUrl(channelApp.getDownloadUrl());
						map.put("ios", appVersion);
					}
					list.add(map);
				}
			}
			data.put(channel.getCode(), list);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 获取用户当前APP版本号
	 * 考虑到后期Android和IOS可能更新不一致，这里要做区分
	 * URL路径暂时不考虑用户是否登录
	 */
	@RequestMapping(value = "/com/adpanshi/cashloan/api/app/getVersion.htm", method = RequestMethod.POST)
	public void getVersion() {
		String mobileType = request.getParameter("mobileType");
		Map<String, Object> data = new HashMap<String, Object>();
		String check_version = Global.getValue("check_version");
		JSONObject jsonObject = JSONObject.parseObject(check_version);
		JSONObject jsonIos = jsonObject.getJSONObject("ios");
		JSONObject jsonAndroid = jsonObject.getJSONObject("android");
		//从Redis中获取配置信息，根据mobileType判断（1：IOS）
		if("10".equals(jsonIos.getString("state")) && "1".equals(mobileType)){
			String sysVersion = jsonIos.getString("version");
			data.clear();
			data.put("version",sysVersion);
		}
		//从Redis中获取配置信息，根据mobileType判断（2：Android）
		if("10".equals(jsonAndroid.getString("state")) && "2".equals(mobileType)){
			String sysVersion = jsonAndroid.getString("version");
			data.clear();
			data.put("version",sysVersion);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
}
