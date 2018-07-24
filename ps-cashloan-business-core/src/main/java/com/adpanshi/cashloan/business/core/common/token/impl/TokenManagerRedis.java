package com.adpanshi.cashloan.business.core.common.token.impl;


import com.adpanshi.cashloan.business.core.common.enums.AuthCookieEnum;
import com.adpanshi.cashloan.business.core.common.enums.RedisCacheExpireEnum;
import com.adpanshi.cashloan.business.core.common.model.AuthUserRole;
import com.adpanshi.cashloan.business.core.common.model.TokenModel;
import com.adpanshi.cashloan.business.core.common.token.TokenManager;
import com.adpanshi.cashloan.business.core.common.util.RandomNumUtils;
import com.adpanshi.cashloan.business.core.common.util.RedisCacheUtil;
import com.adpanshi.cashloan.business.core.common.util.RedisKey;
import com.adpanshi.cashloan.business.system.constant.SystemConstant;
import com.adpanshi.cashloan.business.system.domain.SysUser;
import com.adpanshi.cashloan.business.system.mapper.SysMenuMapper;
import com.adpanshi.cashloan.business.system.mapper.SysUserMapper;
import com.adpanshi.cashloan.business.system.model.MenuModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("tokenManagerRedis")
public class TokenManagerRedis implements TokenManager {

    @Autowired
    private RedisCacheUtil cacheUtil;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 登录
     * 创建token
     *
     * @author M
     * @date 20170310
     */
    @Override
    public TokenModel createToken(AuthUserRole aur) {
        String token = "" + aur.getUserId() + System.currentTimeMillis() + aur.getRoleId();
        //@20161025    使用时间限制
        cacheUtil.setExpire(RedisKey.getTokenKey(token), aur, RedisCacheExpireEnum.AuthTokenExpireSeconds.seconds());
        TokenModel model = new TokenModel(aur.getUserId() + "", aur.getUserName(), token);
        return model;
    }

    @Override
    public void deleteToken(String token) {
        cacheUtil.del(RedisKey.getTokenKey(token));
    }

    @Override
    public void deleteTokenByKey(String key) {
        cacheUtil.del(key);
    }

    @Override
    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 3) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        return new TokenModel(param[0], param[1], param[2]);
    }


    @Override
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies == null || cookies.length <= 0) {
            return token;
        }
        for (Cookie cookie : cookies) {
            if (AuthCookieEnum.M_TOKEN.cookieKey().equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    /**
     * @auther huangqin
     * @description 获取当前登录用户的权限用户,概率刷新时间
     * @param token
     * @return AuthUserRole
     * @data 2017-12-15
     */
    @Override
    public AuthUserRole getAuthUserRole(String token) {
        AuthUserRole arl = cacheUtil.get(RedisKey.getTokenKey(token), AuthUserRole.class);
        if (arl != null) {
            //概率刷新时间
            if (RandomNumUtils.getBoolByChance(SystemConstant.TOKEN_REFLESH_CHANCE)) {
                cacheUtil.expire(RedisKey.getTokenKey(token), RedisCacheExpireEnum.AuthTokenExpireSeconds.seconds());
            }
            return arl;
        }
        return null;
    }

    /**
     * @auther huangqin
     * @description 获取当前登录用户的权限用户,概率刷新时间
     * @param token
     * @return AuthUserRole
     * @data 2017-12-15
     */
    @Override
    public AuthUserRole getAuthUserRoleOnly(String token) {
        return cacheUtil.get(RedisKey.getTokenKey(token), AuthUserRole.class);
    }

    /**
     * @param token
     * @return SysUser
     * @auther huangqin
     * @des 获取登录的用户
     */
    @Override
    public SysUser getSysUser(String token) {
        AuthUserRole arl = cacheUtil.get(RedisKey.getTokenKey(token), AuthUserRole.class);
        if (arl != null) {
            long userId = arl.getUserId();
            SysUser sysUser = sysUserMapper.findByPrimary(userId);
            if (sysUser != null) {
                //概率刷新时间
                if (RandomNumUtils.getBoolByChance(SystemConstant.TOKEN_REFLESH_CHANCE)) {
                    cacheUtil.expire(RedisKey.getTokenKey(token), RedisCacheExpireEnum.AuthTokenExpireSeconds.seconds());
                }
                return sysUser;
            }
        }
        return null;
    }

    /**
     * @auther huangqin
     * @description 获取对应的所有redis的key
     * @param key
     * @return Set<String>
     * @data 2017-12-19
     */
    @Override
    public Set<String> getKeys(String key) {
        Set<String> keys = cacheUtil.keys(key);
        return keys;
    }

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
    @Override
    public boolean authentication(String userUrl,Long roleId,Long menuId) {
        String key = RedisKey.getManagerMenuByRoleId(roleId);
        String menuModels = cacheUtil.get(key,String.class);
        //查询reids
        if(StringUtils.isEmpty(menuModels)){
            //查询数据库.
            List<MenuModel> menuModelList = sysMenuMapper.getMenuListByRoleIds(Arrays.asList(roleId));
            StringBuffer sb = new StringBuffer();
            for (MenuModel mm : menuModelList){
                String url = mm.getLimitCode();
                if(StringUtils.isNotEmpty(url)) {
                    sb.append(url + "|");
                }
            }
            menuModels = sb.toString();
            cacheUtil.set(key,menuModels);
        }
        if(StringUtils.isEmpty(menuModels))
            return false;
        return menuModels.contains(userUrl);
    }

    /**
     * 创建首页信息的redis缓存
     * @author huangqin
     * @date 2018-01-03 15:04
     * */
    @Override
    public void createHomeInfoRedisCashe(String methodName,Map<String,Object> infoData){
        cacheUtil.setExpire(RedisKey.getManagerHomeInfoByMethodName(methodName), infoData, RedisCacheExpireEnum.AuthTokenExpireSeconds.seconds());
    }

    /**
     * 获取首页信息的redis缓存
     * @author huangqin
     * @date 2018-01-03 15:04
     * */
    @Override
    public Map<String,Object> getHomeInfoRedisCashe(String methodName){
        return cacheUtil.get(RedisKey.getManagerHomeInfoByMethodName(methodName), Map.class);
    }

//    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new Timestamp(System.currentTimeMillis()).getDateTime());
//    }

}
