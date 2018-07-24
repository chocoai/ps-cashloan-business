package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.facade.UserFacade;
import com.adpanshi.cashloan.business.cl.model.SmsModel;
import com.adpanshi.cashloan.business.cl.model.user.AppAbsActionWrapper;
import com.adpanshi.cashloan.business.cl.model.user.AppLoginedActionWraper;
import com.adpanshi.cashloan.business.cl.service.AppSessionService;
import com.adpanshi.cashloan.business.cl.service.ClSmsService;
import com.adpanshi.cashloan.business.cl.service.impl.SmsService;
import com.adpanshi.cashloan.business.cl.service.impl.UserService;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.MapUtil;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.BeanUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lsk on 2017/2/15.
 */
@Scope("prototype")
@Controller
@RequestMapping("/com/adpanshi/cashloan/api/act/user")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController {

	@Resource
	private SmsService smsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AppSessionService appSessionService;
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private UserBaseInfoService userBaseInfoService;
	
	@RequestMapping("changeLoginPwd")
	public void changeLoginPwd(final HttpServletRequest request,HttpServletResponse response, final String oldPwd,final String newPwd) {
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

	@RequestMapping("changeTradePwd")
	public void changeTradePwd(final HttpServletRequest request,HttpServletResponse response, final String oldPwd,final String newPwd) {
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
				String oldTradePwd = user.getTradePwd();
				if (oldTradePwd == null) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "请先上设置初始交易密码");
					return ret;
				}
				if (!oldPwd.equals(oldTradePwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "原密码不正确");
					return ret;
				}
				user.setTradePwd(newPwd);
				user.setTradepwdModifyTime(new Date());
				userFacade.updateSelective(user);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "交易密码修改成功");
				return ret;
			}
		};
	}

	@RequestMapping("setTradePwd")
	public void setTradePwd(final HttpServletRequest request,HttpServletResponse response, final String pwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				if (StringUtil.isEmpty(pwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				User user=userFacade.queryUserById(Long.parseLong(userId));
				if (!StringUtil.isEmpty(user.getTradePwd())) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "交易密码已设置,不能重复设置");
					return ret;
				}
				user.setTradePwd(pwd);
				user.setTradepwdModifyTime(new Date());
				userFacade.updateSelective(user);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "交易密码已设置");
				return ret;
			}
		};
	}

	@RequestMapping("validateUser")
	public void validateUser(final HttpServletRequest request,HttpServletResponse response, final String idNo,final String realName, final String vcode) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				if (StringUtil.isEmpty(idNo) || StringUtil.isEmpty(realName)|| StringUtil.isEmpty(vcode)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				UserBaseInfo detail=userBaseInfoService.findByUserId(Long.parseLong(userId));
				if (!idNo.equalsIgnoreCase(detail.getIdNo())|| !realName.equals(detail.getRealName())) {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "身份证或姓名验证不通过");
					Map data = new LinkedHashMap();
					data.put("pass", false);
					ret.put("data", data);
					return ret;
				}
				ClSmsService clSmsService = (ClSmsService) BeanUtil.getBean("clSmsService");
				String loginName = (String) userData.get("loginName");
				String msg;
				int result = clSmsService.verifySms(loginName,null,SmsModel.SMS_TYPE_FINDPAY, vcode);
				logger.info("------------------->短信验证:idNo={},realName={},vcode={},result={},userData={}.",new Object[]{idNo,realName,vcode,result,JSONObject.toJSONString(userData)});
				if (result == 1) {
					msg = null;
				} else if (result == -1) {
					msg = "验证码已过期";
				} else {
					msg = "手机号码或验证码错误";
				}
				if (msg != null) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", msg);
					return ret;
				}
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "验证通过");
				Map data = new LinkedHashMap();
				data.put("pass", true);
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("resetTradePwd")
	public void resetTradePwd(final HttpServletRequest request,HttpServletResponse response, final String newPwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				if (StringUtil.isBlank(newPwd) || StringUtil.isBlank(userId)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				User updateUser=userFacade.queryUserById(Long.parseLong(userId));
				if (null==updateUser) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "系统异常.");
					return ret;
				}
				updateUser.setTradePwd(newPwd);
				updateUser.setTradepwdModifyTime(new Date());
				userFacade.updateSelective(updateUser);
				logger.info("==============>userId={}的用户，交易密码已修改。",new Object[]{userId});
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "交易密码已重置");
				return ret;
			}
		};
	}

	@RequestMapping("logout.htm")
	public void logout(final HttpServletRequest request,HttpServletResponse response) {
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

	@RequestMapping("info")
	public void accountInfo(final HttpServletRequest request,HttpServletResponse response) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				Map rec=userFacade.info(userId);
				boolean dj = "20".equals(rec.get("state"));
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "账户信息获取成功");
				Object profitRate = null;
				String key = (String) MapUtil.array2Map(new Object[][] {{"1","levelOne" },{ "2", "levelTwo" },{"3","levelThree"},}).get(rec.get("level").toString());
				if (key != null) {
					profitRate=Global.getValue(key);
				}
				Map data = new LinkedHashMap();
				data.put("creditTotal", rec.get("total"));
				data.put("creditUnused", dj ? 0 : rec.get("unuse"));
				data.put("creditUsed", rec.get("used"));
				data.put("invitationCode", rec.get("invitation_code"));
				data.put("phone", rec.get("login_name"));
				data.put("profitRate", profitRate);
				data.put("idState", rec.get("id_state"));
				data.put("bankCardState", rec.get("bank_card_state"));
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("getTradeState")
	public void getTradeState(final HttpServletRequest request,
			HttpServletResponse response) {
		new AppLoginedActionWraper(response, request) {
			private boolean containsEmpty(Map rec, String... keys) {
				for (String key : keys) {
					String value = (String) rec.get(key);
					if (StringUtil.isEmpty(value)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public Object doAction(Map userData, String userId) {
				Map<String,Object> user=userFacade.getTradeState(Long.parseLong(userId));
				boolean infoEmpty = containsEmpty(user, "real_name", "id_no");
				boolean tradeEmpty = StringUtil.isEmpty((String) user.get("trade_pwd"));
				Map ret = new LinkedHashMap();
				ret.put("msg", "交易密码是否可设置状态获取成功");
				Map data = new LinkedHashMap();
				data.put("setable", tradeEmpty);
				data.put("forgetable", !infoEmpty && !tradeEmpty);
				data.put("changeable", !tradeEmpty);
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("validateTradePwd")
	public void validateTradePwd(final HttpServletRequest request,
			HttpServletResponse response, final String tradePwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "操作成功");
				Map data = new LinkedHashMap();
				userFacade.queryUserById(Long.parseLong(userId));
				data.put("pass",userFacade.validateTradePwd(Long.parseLong(userId), tradePwd)!=null);
				ret.put("data", data);
				return ret;
			}
		};
	}

	/**
	 * <p>邀请记录</p>
	 * **/
	@RequestMapping("inviteList")
	public void inviteList(final HttpServletRequest request,HttpServletResponse response, final String invitId,final int pageNo, final int pageSize) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Map userData, String userId) {
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "下级代理商获取成功");
				String _id = StringUtil.isEmpty(invitId) ? userId : invitId;
				Map data = new LinkedHashMap();
				data.put("list",userFacade.inviteList(Long.parseLong(_id), (pageNo - 1) * pageSize , pageSize));
				long total = Long.valueOf(userFacade.inviteListCnt(Long.parseLong(_id)).get("cnt").toString());
				ret.put("data", data);
				Map page = new LinkedHashMap();
				page.put("current", pageNo);
				page.put("pageSize", pageSize);
				page.put("total", total);
				page.put("pages", total % pageSize == 0 ? total / pageSize: total / pageSize + 1);
				ret.put("page", page);
				return ret;
			}
		};
	}
	
	/**
	 * @Title: updateBlackBox
	 * @Description: 更新用户设备指纹
	 * @return void
	 * @throws
	 */
	@RequestMapping("updateBlackBox")
	public void updateBlackBox(final HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="blackBox",required=true) final String blackBox,
			@RequestParam(value="userId",required=true) final Long userId) {
		ServletUtils.writeToResponse(response, userService.updateBlackBox(userId, blackBox));
	}

}
