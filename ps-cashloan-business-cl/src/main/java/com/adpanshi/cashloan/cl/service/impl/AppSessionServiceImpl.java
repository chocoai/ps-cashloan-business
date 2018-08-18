package com.adpanshi.cashloan.cl.service.impl;

import com.adpanshi.cashloan.core.common.context.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.cl.domain.AppSession;
import com.adpanshi.cashloan.cl.mapper.AppSessionMapper;
import com.adpanshi.cashloan.cl.model.user.AppSessionBean;
import com.adpanshi.cashloan.cl.service.AppSessionService;
import com.adpanshi.cashloan.cl.util.RedisApiSessionUtil;
import com.adpanshi.cashloan.core.common.mapper.BaseMapper;
import com.adpanshi.cashloan.core.common.service.impl.BaseServiceImpl;
import com.adpanshi.cashloan.core.common.util.*;
import com.adpanshi.cashloan.core.domain.User;
import com.adpanshi.cashloan.core.mapper.UserMapper;
import com.adpanshi.cashloan.core.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author qing.yunhui 
 * @Since 2011-2017
 * @create 2017-12-18 16:42:01
 * @history
 */
@Service("appSessionService")
public class AppSessionServiceImpl extends BaseServiceImpl<AppSession,Long> implements AppSessionService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
    private AppSessionMapper appSessionMapper;
	
	@Resource
	private UserMapper userMapper;
	
	/**session有效期7天*/
	private int liveMin = 60 * 24 * 7;
	
	@Override
	public BaseMapper<AppSession,Long> getMapper() {
		return appSessionMapper;
	}

	@Override
	public AppSessionBean create(String loginname, int loginType) {
		User user;
		if(StringUtil.isMail(loginname)){
			user = userMapper.getUserByEmail(loginname);
		}else{
			user=userMapper.getUserByPhone(loginname);
		}
		long userId = user.getId();
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		String refreshToken = user.getUuid();
		@SuppressWarnings("rawtypes")
		Map session = MapUtil.array2Map(new Object[][]{{"front",new Object[][]{{"userId", userId },{"token", token },{"refreshToken", refreshToken }}},{"userData", user}});
		AppSession oSession=appSessionMapper.queryLateAppSessionByUserId(userId);
		if (oSession != null) {
			RedisApiSessionUtil.remove(oSession.getToken());
			oSession.setStatus(UserModel.USER_STATE_UNVALID);
			oSession.setErrData(JSONObject.toJSONString(new Object[][]{{ "code", 410 },{ "msg","您的账号已在其他设备登录" }}));
			Map<String,Object> updateMap=ReflectUtil.clzToMap(oSession);
			appSessionMapper.updateSelective(updateMap);
		}
		Date now = new Date();
		AppSession saveSession=new AppSession();
		saveSession.setToken(token);
		saveSession.setRefreshToken(refreshToken);
		saveSession.setUserId(user.getId());
		saveSession.setExpireTime(DateUtil.dateAddMins(now, liveMin));
		saveSession.setLastAccessTime(now);
		saveSession.setStatus(UserModel.USER_STATE_VALID);
		saveSession.setLoginType(loginType);
		saveSession.setSession(JsonUtil.toString(session));
		appSessionMapper.save(saveSession);
		RedisApiSessionUtil.put(token, ReflectUtil.clzToMap(saveSession), liveMin*60);
		return new AppSessionBean(session);
	}
	
	@SuppressWarnings("rawtypes")
	private Map toMap(String data) {
		return JSONObject.parseObject(data, LinkedHashMap.class);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object access(String token) {
		Map rec = (Map) RedisApiSessionUtil.get(token);
		Date now = new Date();
		if (rec == null) {
			logger.info("query db session");
			rec=ReflectUtil.clzToMap(appSessionMapper.queryLateAppSessionByToken(token));
		}
		if (rec == null){return new Object[][] {
				{Constant.RESPONSE_CODE, Constant.TOKEN_NONENTITY_CODE }, { Constant.RESPONSE_CODE_MSG, Constant.TOKEN_NONENTITY} };
		}
		AppSession appSession= JSONObject.parseObject(JSONObject.toJSONString(rec), AppSession.class);
		
		if (null==appSession || null==appSession.getStatus()) {
			appSessionMapper.deleteByToken(token);
			return new Object[][] { { Constant.RESPONSE_CODE, Constant.TOKEN_HAVE_EXPIRED_CODE }, { Constant.RESPONSE_CODE_MSG, Constant.TOKEN_HAVE_EXPIRED} };
		}
		if(UserModel.USER_STATE_VALID!=appSession.getStatus().intValue()){
			appSessionMapper.deleteByToken(token);
			return JSONArray.parseObject(appSession.getErrData(), Object[][].class);
		}
		if (now.getTime() > appSession.getExpireTime().getTime()) {
			appSessionMapper.deleteByToken(token);
			return new Object[][] { { Constant.RESPONSE_CODE, Constant.TOKEN_HAVE_EXPIRED_CODE }, { Constant.RESPONSE_CODE_MSG, Constant.TOKEN_HAVE_EXPIRED} };
		}
		Date expireTime=DateUtil.dateAddMins(now, liveMin);
		RedisApiSessionUtil.put(token, rec, liveMin*60);
		appSession.setExpireTime(expireTime);
		appSession.setLastAccessTime(now);
		appSessionMapper.updateSelective(ReflectUtil.clzToMap(appSession));
		return new AppSessionBean(toMap(appSession.getSession()));
	}

	@Override
	public boolean remove(String token) {
		RedisApiSessionUtil.remove(token);
		AppSession session=appSessionMapper.queryLateAppSessionByToken(token);
		if(null==session)return false;
		appSessionMapper.deleteById(session.getId());
		return true;
	}
      
}
