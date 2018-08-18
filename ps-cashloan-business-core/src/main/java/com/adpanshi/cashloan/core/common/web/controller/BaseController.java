package com.adpanshi.cashloan.core.common.web.controller;


import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.enums.AuthCookieEnum;
import com.adpanshi.cashloan.core.common.exception.BussinessException;
import com.adpanshi.cashloan.core.common.exception.ParamValideException;
import com.adpanshi.cashloan.core.common.exception.ServiceException;
import com.adpanshi.cashloan.core.common.exception.SimpleMessageException;
import com.adpanshi.cashloan.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.core.common.model.URLConfig;
import com.adpanshi.cashloan.core.common.token.TokenManager;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.ValidateCode;
import com.adpanshi.cashloan.system.domain.SysUser;
import com.adpanshi.cashloan.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 基类action
 * @version 1.0
 * @author 8452
 * @created 2014年9月23日 下午1:48:28
 */
@Controller
@Scope("prototype")
public abstract class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	protected URLConfig mlmsAppServerConfig;
	@Resource
	protected SysUserService sysUserService;
	@Resource
	private TokenManager manager;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }

	/**
	 * 获取当前登录用户的用户
	 * 
	 * @param request
	 * @return
	 * @see
	 */
	protected SysUser getLoginUser(HttpServletRequest request) {
		String token=getToken(request);
		SysUser obj=manager.getSysUser(token);
		if(obj != null){
			return obj;
		}
		return null;
	}

	/**
	 * 获取当前登录用户的用户
	 * @param request
	 * @return AuthUserRole
	 */
	protected AuthUserRole getAuthUserRole(HttpServletRequest request) {
		String token=getToken(request);
		AuthUserRole authUserRole = manager.getAuthUserRole(token);;
		if(authUserRole != null){
			return authUserRole;
		}
		return null;
	}

	public  String getToken(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		String token="";
		for(Cookie cookie:cookies){
			if(AuthCookieEnum.M_TOKEN.cookieKey().equals(cookie.getName())) {token=cookie.getValue();}
		}
		return token;
	}

	/**
	 * 获得当前登录用户信息
	 * 
	 * @return SystemUser
	 * @throws ServiceException
	 */
	protected SysUser getSysUser() throws ServiceException {
		// 增加用户登录判断
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SysUser sysUser = sysUserService.getUserByUserName(user.getUsername());
		return sysUser;
	}


	protected String redirect(String url) {
		return "redirect:" + mlmsAppServerConfig + url;
	}

	protected String redirectLogin() {
		return redirect("/modules/login.htm");
	}

	protected String success() {
		return redirect("/success.htm");
	}

	protected String error() {
		return redirect("/error.htm");
	}

	protected String success(ModelMap model) {
		return "success";
	}

	protected String error(ModelMap model) {
		return "error";
	}

	@ExceptionHandler({Exception.class})
	public void exceptionHandler(Exception e, HttpServletResponse response) {
		 Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "400");
		res.put(Constant.RESPONSE_CODE_MSG, "系统出错了，请检查参数是否正确");
		logger.error("Exception:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public void notNullException(MethodArgumentNotValidException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "400");
		BindingResult bindingResult = e.getBindingResult();
		if (bindingResult.hasErrors()) {
			String msg = bindingResult.getFieldError().getDefaultMessage();
			res.put(Constant.RESPONSE_CODE_MSG, msg);
		} else {
			res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		}
		logger.error("MethodArgumentNotValidException:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	@ExceptionHandler({ParamValideException.class})
	public void paramValideException(ParamValideException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, "404");
		res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
		logger.error("MethodArgumentNotValidException:", e);
		ServletUtils.writeToResponse(response, res);
	}
	
	@ExceptionHandler({ ServiceException.class })
	public void excptionDispose(ServiceException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
		res.put(Constant.RESPONSE_CODE, e.getCode());
		res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());

		logger.error("ServiceException:", e);

		ServletUtils.writeToResponse(response, res);
	}

    @ExceptionHandler({RuntimeException.class})
    public void runtimeExcptionDispose(RuntimeException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, "系统出错了");

		logger.error("RuntimeException", e);

        ServletUtils.writeToResponse(response, res);
    }
    
    
    @ExceptionHandler({BussinessException.class})
    public void bussinessException(BussinessException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>(16);
        if (StringUtils.isNotBlank(e.getCode())) {
        	res.put(Constant.RESPONSE_CODE, e.getCode());
        } else {
        	 res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        }
        res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
	
		logger.error("BussinessException", e);

        ServletUtils.writeToResponse(response, res);
    }
    
    @ExceptionHandler({SimpleMessageException.class})
    public void simpleMessageException(SimpleMessageException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>(16);
        if (StringUtils.isNotBlank(e.getCode())) {
        	res.put(Constant.RESPONSE_CODE, e.getCode());
        } else {
        	 res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        }
        res.put(Constant.RESPONSE_CODE_MSG, e.getMessage());
	
		logger.error("SimpleMessageException", e);

		ServletUtils.writeToResponse(response, res);
    }
    
    @ExceptionHandler({BindException.class})
    public void bindException(BindException e, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
        res.put(Constant.RESPONSE_CODE_MSG, "数据保存失败，请稍后重试");
    
		logger.error("参数校验失败"+ e.getFieldError().getDefaultMessage(), e);

        ServletUtils.writeToResponse(response, res);
    }

    
	/**
	 * 使用 request.getInputStream()读取回调数据流
	 *
	 * @param request
	 * @return
	 */
	public String getRequestParams(HttpServletRequest request) {
		String params = "";
		try {
			request.setCharacterEncoding("UTF-8");
			InputStream in = request.getInputStream();
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			params = sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return params;
	}

	/**
	 * <p>生成验证码</p>
	 * @param numberCode true:纯数字
	 * @param lineCount  干扰线数量
	 * */
	protected void generateImgCode(boolean numberCode,int lineCount) throws Exception{
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    
	    HttpSession session = request.getSession();
	  
	    ValidateCode vCode = new ValidateCode(numberCode,120,40,4,lineCount);
	    session.setAttribute("code", vCode.getCode());
	    vCode.write(response.getOutputStream());
	    response.getOutputStream().flush();
	}
	
}
