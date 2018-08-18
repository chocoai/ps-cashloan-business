package com.adpanshi.cashloan.business.aspects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.adpanshi.cashloan.core.domain.ClLog;
import com.adpanshi.cashloan.core.mapper.ClLogMapper;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//@Aspect
public class LogAspect {
    @Autowired
    private ClLogMapper logMapper;

    /**
     *
     * @Description: 方法调用成功后触发   记录结束时间
     * @author tq
     */
    public  void after(JoinPoint joinPoint, Object rtv) throws Exception {
        //获取类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取入参
        Object[] arguments = joinPoint.getArgs();

        String  url = null;
        String userId = null;
        String loginName = null;
        String ip = null;
        String input = "";
        //获取request
        if((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()!=null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            url = request.getRequestURL().toString();
            input = JSON.toJSONString(request.getParameterMap()).replace("[","").replace("]","");
            ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if(!url.contains("login")&&request.getSession().getAttribute("userId")!=null){

                userId = request.getSession().getAttribute("userId").toString();
                JSONObject obj = (JSONObject) JSON.toJSON(request.getSession().getAttribute("userData"));
                JSONObject userData = obj.getJSONObject("userData");
                loginName = userData.getString("loginName");
            }
        }

//        for (Object info : arguments) {
//            if(info!=null){
//                input +=  JSON.toJSONString(info);
//            }
//        }

        String result = "";
        if(rtv!=null){
            result = rtv.toString();
        }
        ClLog log = new ClLog();
        log.setCreateTime(new Date());
        log.setInputParam(input);
        log.setLoginName(loginName);
        log.setOutputParam(result);
        log.setOptionMethod(url);
        log.setUserId(userId!=null?Long.valueOf(userId):null);
        log.setOptionIp(ip);
        logMapper.save(log);
    }


}
