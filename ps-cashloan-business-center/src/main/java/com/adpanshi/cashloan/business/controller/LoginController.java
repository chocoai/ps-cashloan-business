package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.model.user.AppAbsActionWrapper;
import com.adpanshi.cashloan.cl.service.impl.UserService;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lsk on 2016/7/27.
 * @author 8452
 */
@Scope("prototype")
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("/api/user")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private UserService userService;

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
	@RequestMapping("login.htm")
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

	@RequestMapping("register.htm")
	public void register(final HttpServletRequest request,
			final HttpServletResponse response,
			final String firstName,
			final String lastName,
			final String middleName,
			final String realName,
			final String phone,
			final String email,
			final String loginPwd,
			final String invitationCode, 
			final String client,
			final String registerCoordinate,
			final String registerAddr,
			final String blackBox,
			final String deviceTokens,
			final Integer mobileType,
			final  String vcode
			) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = userService.registerUser(request, realName, firstName, lastName, middleName, phone, email,
						loginPwd, invitationCode, registerCoordinate,
						registerAddr, client,"",vcode);
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
						  final String newPwd, final String vcode) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				return userService.forgetPwd(phone, email, newPwd, vcode);
			}
		};
	}

}
