package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.facade.UserFacade;
import com.adpanshi.cashloan.cl.model.user.AppAbsActionWrapper;
import com.adpanshi.cashloan.cl.model.user.AppLoginedActionWraper;
import com.adpanshi.cashloan.cl.service.AppSessionService;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lsk on 2017/2/15.
 * @author 8452
 */
@Scope("prototype")
@Controller
@RequestMapping("/api/act/user")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController {

	@Autowired
	private AppSessionService appSessionService;
	
	@Autowired
	private UserFacade userFacade;
	
	@RequestMapping("changeLoginPwd.htm")
	public void changeLoginPwd(final HttpServletRequest request, HttpServletResponse response, final String oldPwd, final String newPwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				User user=userFacade.queryUserById(Long.parseLong(userId));
				if (!oldPwd.equals(user.getLoginPwd())) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "原密码不正确");
					return ret;
				}
				user.setLoginPwd(newPwd);
				user.setLoginpwdModifyTime(new Date());
				userFacade.updateSelective(user);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "登录密码修改成功");
				return ret;
			}
		};
	}

	@RequestMapping("logout.htm")
	public void logout(final HttpServletRequest request, HttpServletResponse response) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				String token = request.getHeader("token");
				if (appSessionService.remove(token)) {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "已注销");
					return ret;
				} else {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "token不存在，无需注销");
					return ret;
				}
			}
		};
	}

}
