package com.adpanshi.cashloan.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.cl.service.AppSessionService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.enums.SwitchEnum;
import com.adpanshi.cashloan.core.common.util.JsonUtil;
import com.adpanshi.cashloan.core.common.util.MapUtil;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lsk on 2017/2/14.
 */
public class ApiInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

	/***用于将输出生成为十六进制*/
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	@Autowired
	private AppSessionService AppSessionService;

	public static Map<String,Object> getParams(HttpServletRequest request) {
		Map<String, String[]> rec = request.getParameterMap();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		for (Map.Entry<String, String[]> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue()[0];
			result.put(name, value);
		}
		return result;
	}

	public static String paramsString(Map<String,Object> map) {
		Map<String, Object> rec = MapUtil.simpleSort(map);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			sb.append(name + "=" + value).append("|");
		}
		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		logger.debug("签名验签" + sb.toString());
		return sb.toString();
	}

	private static String md5(String data) throws NoSuchAlgorithmException,UnsupportedEncodingException {
		return encodeToString(MessageDigest.getInstance("MD5").digest(data.getBytes("utf8")));
	}
	
	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
//			br.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return str;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String,Object> during=during();
		if(null!=during){
			JsonUtil.writeJson(during, response);
			return false;
		}
		String uri = request.getRequestURI();
		Map< String, Object>  requestMap = getParams(request);
		logger.info(uri+"; requestMap="+requestMap);
		//@remarks: 重写强制更新版本. @date:20170818 @author:nmnl
		Map< String, Object> returnMap = checkVersion(requestMap);
//		if(null != returnMap) {
//			JsonUtil.writeJson(returnMap, response);
//			return false;
//		}
		String token = request.getHeader("token");
		String signMsg = request.getHeader("signMsg");
		Map<String, Object> rec = new LinkedHashMap<String, Object>();
		String _signMsg;
		// 登录后的请求地址都带有/act/
		boolean flag = true;
		if (uri.contains("/act/")) {
			if (StringUtil.isEmpty(token) || StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "没有token或signMsg");
				JsonUtil.writeJson(rec, response);
				logger.error(rec.toString());
				return false;
			}
			_signMsg = md5(Global.getValue("app_key") + token + paramsString(requestMap));
			flag = signMsg.equalsIgnoreCase(_signMsg);
			// 不需要登录的地址可能没有token
		} else {
			if (StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "没有signMsg");
				JsonUtil.writeJson(rec, response);
				logger.error(rec.toString());
				return false;
			}
			String data=Global.getValue("app_key") + (token == null ? "" : token) + paramsString(requestMap);
			_signMsg = md5(data);
			flag =signMsg.equalsIgnoreCase(_signMsg);
		}
		// 根据地址是否带/act/生成的_signMsg，校验
		if (!flag) {
			rec.put("code", 400);
			rec.put("msg", "验签不通过");
			JsonUtil.writeJson(rec, response);
			logger.error(rec.toString());
			return false;
		}
		// 如果带有token，则说明已经登陆，将用户数据放入session中
		if (StringUtil.isNotBlank(token) ) {
			if(uri.contains("/act/")||uri.contains("findIndex")) {
				Object result = AppSessionService.access(token);
				if (result instanceof AppSessionBean) {
					AppSessionBean sessionBean = (AppSessionBean) result;
					String userId = request.getParameter("userId");
					if (StringUtil.isNotBlank(userId) && !userId.equals(sessionBean.getUserId())) {
						rec.put("code", 400);
						rec.put("msg", "数据非法");
						JsonUtil.writeJson(rec, response);
						logger.error(rec.toString());
						return false;
					}
					request.getSession().setAttribute("userData", sessionBean.getSession());
					request.getSession().setAttribute("userId", sessionBean.getUserId());
				} else {
					Map json = (result instanceof Map) ? (Map) result : MapUtil.array2Map((Object[][]) result);
					JsonUtil.writeJson(json, response);
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {}

	/**
	 * 版本控制
	 * @method: checkVersion
	 * @param requestMap
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @throws
	 * @Author: Mr.Wange
	 * @Date: 2018/7/31 11:57
	 */
	private Map< String, Object> checkVersion(Map< String, Object> requestMap) throws Exception{
		String check_version = Global.getValue("check_version");
		JSONObject jsonObject = JSONObject.parseObject(check_version);
		JSONObject jsonIos = jsonObject.getJSONObject("ios");
		JSONObject jsonAndroid = jsonObject.getJSONObject("android");
		//返回信息
		//msg 不能变更.
		Map<String, Object> m = new HashMap<>();
		String androidRemark=jsonAndroid.getString("remark");
		String iosRemark=jsonIos.getString("remark");
		if((null == requestMap.get("mobileType")) || (null == requestMap.get("versionNumber"))) {
			logger.error(" 获取用户信息异常 Interceptor ");
			m.put("code", 400);
			m.put("msg", androidRemark);
			m.put("version",999);
			m.put("downloadUrl", "");
			return m;
		}
		//手机类型,版本
		String mobileType = requestMap.get("mobileType").toString();
		String mobileVersion = requestMap.get("versionNumber").toString();
		//ios
		if("10".equals(jsonIos.getString("state")) && "1".equals(mobileType)){
			String sysVersion = jsonIos.getString("version");
			int result = StringUtil.compareVersion(sysVersion, mobileVersion);
			if (result > 0) {
				m.clear();
				m.put("code", 400);
				m.put("msg", iosRemark);
				m.put("version",sysVersion);
				m.put("downloadUrl", "");
				return m;
			}
		}
		//android
		if("10".equals(jsonAndroid.getString("state")) && "2".equals(mobileType)){
			String sysVersion = jsonAndroid.getString("version");
			String sysDownloadUrl = jsonAndroid.getString("downloadUrl");
			int result = StringUtil.compareVersion(sysVersion, mobileVersion);
			if (result > 0) {
				m.clear();
				m.put("code", 400);
				m.put("msg", androidRemark);
				m.put("version",sysVersion);
				m.put("androidDownloadUrl", sysDownloadUrl);
				return m;
			}
		}
		return null;
	}

	/**
	 * <p>将指定的字节数组编码为字符数组，然后将该字符数组作为字符串返回</p>
	 * @param bytes 待编码的数组
	 * @return 编码后的字符串
	 * */
	static String encodeToString(byte[] bytes) {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }

	/**
	 * 将一个字节数组转换为一个字符数组，以便按顺序表示每个字节的十六进制值。
     * 返回的数组将是传递数组的长度的两倍，因为它需要两个字符来表示任何数组
     * 给定字节。
	 * @param data 待转换的数组
	 * @return 转换后的字符数组
	 * */
	static char[] encode(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // 将二个字符组成十六进制
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

	/**<p>系统升级</p>*/
    static Map<String,Object> during(){
		String switches=Global.getValue(Constant.SYSTEM_UPGRADE);
		String toast=Global.getValue(Constant.SYSTEM_UPGRADE_MSG);
		if(StringUtil.isBlank(switches)) {return null;}
		if(!switches.equals(SwitchEnum.ON.getCode())){return null;}
		Map<String,Object> data=new HashMap<>(16);
		data.put(Constant.RESPONSE_CODE, Constant.SYSTEM_UPGRADE_CODE);
		data.put(Constant.RESPONSE_CODE_MSG, StringUtil.isBlank(toast)?"系统正在升级中...":toast);
		return data;
	}

}
