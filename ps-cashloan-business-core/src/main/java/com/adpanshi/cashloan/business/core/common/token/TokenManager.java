package com.adpanshi.cashloan.business.core.common.token;


import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.core.common.model.TokenModel;
import com.adpanshi.cashloan.business.system.domain.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * Token管理
 * @author Chen
 *
 */
public interface TokenManager {
	
	/**
	 * 登录
	 * 创建token
	 * @author M
	 * @date 20170310
	 */
	TokenModel createToken(AuthUserRole aur);

	/**
	 * Token解析
	 * @param authorization 加密字符串
	 * @return TokenModel
	 */
	TokenModel getToken(String authorization);

	/**
	 * 获取token
	 * @param request 加密字符串
	 * @return String
	 */
	String getToken(HttpServletRequest request);

	/**
	 * Token清除
	 * @param userid 用户id
	 */
	void deleteToken(String userid);

	/**
	 * 根据key清楚缓存
	 * @param key
	 */
	void deleteTokenByKey(String key);
	
	/**
	 * token取得当前用户权限  仅用户ID 权限ID
	 * @return 当前用户
	 */
	AuthUserRole getAuthUserRole(String token);

	AuthUserRole getAuthUserRoleOnly(String token);
	/**
	 * token取得当前用户SysUser
	 * @return 当前用户
	 */
	SysUser getSysUser(String token);

	/**
	 * @auther huangqin
	 * @description 获取对应的所有redis的key
	 * @param key
	 * @return Set<String>
	 * @data 2017-12-19
	 */
	Set<String> getKeys(String key);

	/**
	 *
	 * @description 鉴权.用户是否有当前权限.
	 * 1.用户访问url
	 * 2.用户角色
	 * 3.用户访问菜单id
	 * @param userUrl
	 * @param roleId
	 * @param menuId
	 * @return boolean
	 * @auther nmnl
	 * @data 2017-12-23
	 */
	boolean authentication(String userUrl, Long roleId, Long menuId);

	/**
	 * 创建首页信息的redis缓存
	 * @author huangqin
	 * @date 2018-01-02 15:04
	 * */
	void createHomeInfoRedisCashe(String methodName, Map<String, Object> infoData);

	/**
	 * 获取首页信息的redis缓存
	 * @author huangqin
	 * @date 2018-01-03 15:04
	 * */
	Map<String,Object> getHomeInfoRedisCashe(String methodName);
}
