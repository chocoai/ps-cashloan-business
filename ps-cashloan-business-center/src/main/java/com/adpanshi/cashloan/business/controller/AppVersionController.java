package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.data.thirdparty.pancard.domain.PanCardDomain;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;
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

	/**
	 * 获取用户当前APP版本号
	 * 考虑到后期Android和IOS可能更新不一致，这里要做区分
	 * URL路径暂时不考虑用户是否登录
	 */
	@RequestMapping(value = "/api/app/getVersion.htm", method = RequestMethod.POST)
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
