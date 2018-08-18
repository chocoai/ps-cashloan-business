package com.adpanshi.cashloan.business.controller;


import com.adpanshi.cashloan.cl.domain.Sms;
import com.adpanshi.cashloan.cl.service.ClSmsService;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.context.MessageConstant;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.service.CloanUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
* 短信记录Controller
* @author
* @version 1.0.0
* @date 2017-03-09 14:48:24
*
*
*
*
*/
@Scope("prototype")
@Controller
public class SmsController extends BaseController {
   private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

   @Resource
   private ClSmsService clSmsService;
   @Resource
   private CloanUserService cloanUserService;

   /**
    * 获取短信验证码
    * @param phone
    * @param type
    * @throws Exception
    */
   @RequestMapping(value = "/api/user/sendSms.htm")
   public void sendSms(final String phone, final String type, final String email) throws Exception {
       logger.info("获取短信验证码 -> 手机号码/邮箱 " + phone + email + " type " + type);
       Map<String,Object> result = new HashMap<>(16);
       Map<String,Object> data = new HashMap<>(16);
       String message = this.check(phone, type, email);
       if (message==null) {
           long countDown = 0;
           if(StringUtil.isNotBlank(phone)){
               countDown =	clSmsService.findTimeDifference(phone, type);
           }else{
               countDown =	clSmsService.findTimeDifference(email, type);
           }

           if (countDown != 0) {
               data.put("countDown", countDown);
               data.put("state", "20");
               message = MessageConstant.SMS_FREQUENTLY;
           } else {
               long msg = 0;
               if(StringUtil.isNotBlank(phone)){
                   msg = clSmsService.sendSms(phone, type);
               }else{
                   msg = clSmsService.sendEmailSms(email,type);
               }

               if (msg == 1) {
                   data.put("state", "10");
                   result.put(Constant.RESPONSE_DATA, data);
                   result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                   result.put(Constant.RESPONSE_CODE_MSG, "发送成功");
               } else {
                   result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                   result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.VERIFICATION_CODE_SENDING_FAILED);
               }
           }

       }
       if (message!=null) {
           data.put("state", "20");
           data.put("message", message);
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, message);
       }

       logger.info(result.toString());
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 短信验证
    * @param phone
    * @param vcode
    * @throws Exception
    */
   @RequestMapping(value = "/api/user/verifySms.htm")
   public void verifyMsg(final String phone,final String email,final String type,final String vcode) throws Exception {
       int msg = clSmsService.verifySms(phone, email, type, vcode);
       Map<String,Object> result = new HashMap<>(16);
       Map<String,Object> data = new HashMap<>(16);
       if (msg == 1) {
           logger.info("短信验证码成功 -> 手机号码/邮箱 "+ phone+email + " type "+ type + " code "+ vcode);
           data.put("state", "10");
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "验证成功");
       } else if(msg == -1){
           logger.info("短信验证码过期 -> 手机号码/邮箱 "+ phone+email + " type "+ type + " code "+ vcode);
           data.put("message", "验证码已过期");
           data.put("state", "20");
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, MessageConstant.VERIFICATION_CODE_EXPIRED);
       } else {
           if(StringUtil.isNotBlank(phone)){
               logger.info("短信验证码错误 -> 手机号码 "+ phone + " type "+ type + " code "+ vcode);
               data.put("message", MessageConstant.VERIFICATION_CODE_ERROR);
               data.put("state", "20");
           }else{
               logger.info("短信验证码错误 -> 邮箱 "+ email + " type "+ type + " code "+ vcode);
               data.put("message", MessageConstant.VERIFICATION_CODE_ERROR);
               data.put("state", "20");
           }
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, data.get("message"));
       }
       ServletUtils.writeToResponse(response,result);
   }
   /**
    * 网页注册发送短信(含验证图片验证码)
    */
   @RequestMapping(value = "/api/user/h5SendSms.htm")
   public void h5SendSms(){
       //要求图片验证码重新开放，防止恶意刷发送短信 @author yecy 20180419
       //需求N293 渠道注册优化-去掉图片验证码
       String code = request.getParameter("code");
       String phone = request.getParameter("phone");
       String type = request.getParameter("type");
       logger.info("网页注册发送短信(含验证图片验证码) -> 手机号码 "+ phone + " type "+ type + " code "+ code);
       long countDown = 0;
       HttpSession session = request.getSession();
       String sessionCode = (String) session.getAttribute("code");
       Map<String,Object> resultMap = new HashMap<String,Object>();
       String result;
       if (StringUtil.isNotBlank(code)&&code.length()==4&&code.equals(sessionCode)) {
           result = this.check(phone, type);
           if (result==null) {
               if ("register".equals(type)) {
                   countDown = clSmsService.findTimeDifference(phone, type);
                   if (countDown != 0) {
                       result = "获取短信验证码过于频繁，请稍后再试";
                   } else {
                       long msg = clSmsService.sendSms(phone, type);
                       if (msg == 1) {
                           result = "10";
                       } else {
                           result = "短信发送失败";
                       }
                   }
               }else {
                   result = "短信类型错误";
               }
           }
           resultMap.put("countDown", countDown);
           if ("10".equals(result)) {
               resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
               resultMap.put(Constant.RESPONSE_CODE_MSG, "短信发送成功");
           }else {
               resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
               resultMap.put(Constant.RESPONSE_CODE_MSG, result);
           }
       }else {
           resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           resultMap.put(Constant.RESPONSE_CODE_MSG, "图片验证码错误");
       }
       ServletUtils.writeToResponse(response,resultMap);
   }
     /**
        *  保存短信
        * @param phone
        * @param orderNo,
        * @param content
        * @param vcode
        * @param smsPlatform
        * @param code
        * @param msgid
        * @param smsType
        * @param error
        * @return void
        * @throws
        * @author Vic Tang
        * @date 2018/8/8 11:46
        * */
   @RequestMapping(value = "/sms/save.htm")
   public void smsSave(@RequestParam(value = "phone") String phone,@RequestParam(value = "orderNo") String orderNo,
                       @RequestParam(value = "content") String content, @RequestParam(value = "vcode") String vcode,
                       @RequestParam(value = "smsPlatform") String smsPlatform,@RequestParam(value = "code")Integer code,
                       @RequestParam(value = "msgid") String msgid,@RequestParam(value = "smsType") String smsType,
                       @RequestParam(value = "error")String error){
       Map<String,Object> result = new HashMap<String,Object>();
       Sms sms = new Sms();
       sms.setCode(vcode);
       sms.setContent(content);
       sms.setPhone(phone);
       sms.setOrderNo(orderNo);
       sms.setMsgid(msgid);
       sms.setSmsType(smsType);
       sms.setSendTime(DateUtil.getNow());
       sms.setVerifyTime(0);
       sms.setSmsPlatform(smsPlatform);
       if(code != null && code == 0){
           sms.setResp("短信已发送");
           sms.setState("10");
       } else {
           sms.setResp(error);
           sms.setState("20");
       }
       int i = clSmsService.insert(sms);
       if(i > 0){
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "保存失败");
       }

       ServletUtils.writeToResponse(response,result);
   }
   private String check(String phone,String type){
       return check(phone, type, null);
   }
   private String check(String phone,String type, String email){
       String msg = null;
       if(StringUtil.isBlank(type)){
           return "参数不能为空";
       }
       if(StringUtil.isBlank(phone)){
           if(StringUtil.isBlank(email)){
               return "参数不能为空";
           }else if(!StringUtil.isMail(email)){
               return MessageConstant.EMAIL_ERROR;
           }
       }
       // 当日最大注册用户数
       long todayCount = cloanUserService.todayCount();
       String dayRegisterMax_ = Global.getValue("day_register_max");
       if(StringUtil.isNotBlank(dayRegisterMax_)){
           int dayRegisterMax = Integer.parseInt(dayRegisterMax_);
           if(dayRegisterMax > 0 && todayCount >= dayRegisterMax){
               return "今天注册人数已达到上限";
           }
       }

       //找回登录密码
       if (StringUtil.equals("findReg", type)) {
           if(StringUtil.isNotBlank(phone)){
               if (clSmsService.findUser(phone)<1) {
                   return MessageConstant.PHONE_NOT_EXIST;
               }
           }else{
               String keyType = "email";
               if (clSmsService.findUser(email,keyType)<1) {
                   return MessageConstant.EMAIL_NOT_EXIST;
               }
           }
       }

       if (msg==null&&clSmsService.countDayTime(phone,email, type) <= 0) {
           msg = MessageConstant.SMS_FREQUENTLY;
       }
       return msg;
   }

}
