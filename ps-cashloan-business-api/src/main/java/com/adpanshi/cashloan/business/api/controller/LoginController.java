package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.facade.UserFacade;
import com.adpanshi.cashloan.business.cl.model.user.AppAbsActionWrapper;
import com.adpanshi.cashloan.business.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.business.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.business.cl.service.impl.SmsService;
import com.adpanshi.cashloan.business.cl.service.impl.UserService;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lsk on 2016/7/27.
 */
@Scope("prototype")
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("/com/adpanshi/cashloan/api/user")
public class LoginController{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private UserFacade userFacade;

	@Resource(name = "clUserService_")
	private UserService userService;

	@Resource
	private SmsService smsService;
	
	@Resource
	private UserEquipmentInfoService userEquipmentInfoService;
	
	@Resource
	UserBaseInfoService userBaseInfoService;

	/**
	 * @param request
	 * @param response
	 * @param loginName
	 * @param loginPwd
	 * @param signMsg
	 * @param blackBox
	 * @param mobileType
	 * @param deviceTokens
	 */
	@RequestMapping("login")
	public void login(final HttpServletRequest request,
					  HttpServletResponse response,
					  final String loginName,
					  final String loginPwd,
					  final String signMsg,
					  final String blackBox,
					  final Integer mobileType,
					  final String deviceTokens) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				return userService.login(request, loginName, loginPwd, blackBox,mobileType,deviceTokens);
			}
		};
	}

	@RequestMapping("autoLogin")
	public void autoLogin(final HttpServletRequest request,
			final HttpServletResponse response, final String refresh_token) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = new LinkedHashMap();

				if (StringUtil.isEmpty(refresh_token)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "非法请求");
					return ret;
				}

//				if (Math.abs(new Date().getTime() - subtime) > 60000) {
//					Map ret = new LinkedHashMap();
//					ret.put("success", false);
//					ret.put("msg", "请确认参数subtime是否正确");
//					return ret;
//				}

				/*
				 * String verify = MD5.encode(Global.getValue("app_key") +
				 * refreshToken + subtime); if
				 * (!verify.equalsIgnoreCase(signMsg)) { Map ret = new
				 * LinkedHashMap(); ret.put("success", false); ret.put("msg",
				 * "签名未通过"); return ret; } else {
				 */
				Object obj = userFacade.autoLogin(refresh_token,1);
				if (!(obj instanceof AppSessionBean)) {
					return obj;
				}
				AppSessionBean bean = (AppSessionBean) obj;
				result.put("success", true);
				result.put("data", bean.getFront());
				result.put("msg", "成功自动登录");
				return result;
			}
		};
	}

	// FORGET_PWD
	// REGISTER_VCODE

	/**
	 * @param request
	 * @param response
	 * @param phone
	 *            REGISTER_VCODE FORGET_PWD
	 */
	@RequestMapping("fetchSmsVCode")
	public void fetchSmsVCode(final HttpServletRequest request,
			final HttpServletResponse response, final String phone) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				if (StringUtil.isEmpty(phone)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "手机号码不能为空");
					return ret;
				}
				smsService.sendSmsByTpl(request, phone, "vcode", randomNum(4));
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "验证码已发送");
				return ret;
			}
		};
	}

	private static String randomNum(int len) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	@RequestMapping("register")
	public void register(final HttpServletRequest request,
			final HttpServletResponse response,
			final String firstName,
			final String lastName,
			final String middleName,
			final String phone,
			final String email,
			final String loginPwd,
			final String invitationCode, 
			final String client,
			final String registerCoordinate,
			final String registerAddr,
			final String blackBox,
			final String deviceTokens,
			final Integer mobileType 
			) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = userService.registerUser(request, firstName, lastName, middleName, phone, email,
						loginPwd, invitationCode, registerCoordinate,
						registerAddr, client,"");
				if ((Boolean) result.get("success")) {
					String loginName = phone;
					if(StringUtil.isBlank(loginName)){
						loginName = email;
					}
					result = userService.login(request, loginName, loginPwd,blackBox,mobileType,deviceTokens);
					result.put("msg", result.get("msg"));
				}
				return result;
			}
		};
	}

	@RequestMapping("login/forgetPwd.htm")
	public void forgetPwd(final HttpServletRequest request,
			HttpServletResponse response, final String phone, final String email,
			final String newPwd, final String vcode, final String signMsg) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				return userService.forgetPwd(phone, email, newPwd, vcode, signMsg);
			}
		};
	}

	@RequestMapping("validateSmsCode")
	public void validateSmsCode(final HttpServletRequest request,
			HttpServletResponse response, final String phone,
			final String type, final String vcode, final String signMsg) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(vcode)
						|| StringUtil.isEmpty(signMsg)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "非法参数");
					return ret;
				}

				/*
				 * String _signMsg = MD5.encode(Global.getValue("app_key") +
				 * phone + vcode); if (!_signMsg.equalsIgnoreCase(signMsg)) {
				 * Map ret = new LinkedHashMap(); ret.put("success", false);
				 * ret.put("msg", "签名验签不通过"); return ret; }
				 */

				String msg = smsService.validateSmsCode(phone, null, type, vcode);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", msg);

				Map data = new LinkedHashMap();
				data.put("pass", msg == null);
				ret.put("data", data);
				return ret;
			}
		};

	}

	@RequestMapping("isPhoneExists")
	public void isPhoneExists(final HttpServletRequest request,
			HttpServletResponse response, final String phone) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				boolean exists =userFacade.isPhoneExists(phone);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				Map data = new LinkedHashMap();
				data.put("isExists", exists ? "20" : "10");
				ret.put("data", data);
				ret.put("msg", exists ? "该手机号码已注册!" : "");
				return ret;
			}
		};
	}
}
