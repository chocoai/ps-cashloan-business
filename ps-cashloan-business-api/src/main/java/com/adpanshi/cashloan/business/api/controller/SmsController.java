package com.adpanshi.cashloan.business.api.controller;


import com.adpanshi.cashloan.business.cl.service.ClSmsService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.context.MessageConstant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//	@RequestMapping(value = "/com.adpanshi.com.adpanshi.cashloan.api/smsBatch.htm")
//	public void smsBatch(){
//		String id = request.getParameter("ids");
//		Map<String,Object> result = new HashMap<String,Object>();
//		int r = clSmsService.smsBatch(id);
//		if(r == 1){
//			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
//		}
//		ServletUtils.writeToResponse(response,result);
//	}

   /**
    * 探测短信验证码是否可获取
    * @param phone
    * @param type
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/user/probeSms.htm")
   public void probeSms(@RequestParam(value="phone") String phone, @RequestParam(value="type") String type) throws Exception {
       logger.info("探测短信验证码是否可获取 -> 手机号码 "+ phone + " type "+ type);
       Map<String,Object> result = new HashMap<String,Object>();
       Map<String,Object> data = new HashMap<String,Object>();
       if(StringUtil.isBlank(phone) || StringUtil.isBlank(type)){
           data.put("message", "参数不能为空");
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "探测失败");
       } else if(!StringUtil.isPhone(phone)){
           data.put("message", "手机号码格式有误");
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "探测失败");
       } else {
           long countDown = clSmsService.findTimeDifference(phone, type);
           data.put("countDown", countDown);
           if (countDown == 0) {
               data.put("state", 10);
           } else {
               data.put("state", 20);
           }
           result.put(Constant.RESPONSE_DATA, data);
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "探测成功");
       }
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 获取短信验证码
    * @param phone
    * @param type
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/user/sendSms.htm")
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
                   //发送放款成功短信
//					TaskFactory factory = new TaskFactory();
//					Task task = factory.getTask("verifyCodeTask");
//					String resultJson = task.sendSMS(phone);
//					msg = clSmsService.chuanglanSms(resultJson,"verifyCode");
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
   @RequestMapping(value = "/com/adpanshi/cashloan/api/user/verifySms.htm")
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
     * 发送短信成功异步通知
     */
    @RequestMapping(value = "/sms/chuanglan/smsNotify.htm")
    public void smsNotify(){
        String msgid = request.getParameter("msgid");
        String status = request.getParameter("status");
        int i = clSmsService.updateByMsgid(msgid,status);
        if(i<1){
           logger.info("短信发送失败->msgid:"+msgid);
       }
        Map<String,Object> result = new HashMap<>(16);
        result.put(Constant.RESPONSE_CODE, "OK");
        ServletUtils.writeToResponse(response,result);
    }

   /**
    * 网页注册发送短信(含验证图片验证码)
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/user/h5SendSms.htm")
   public void h5SendSms(){
       //要求图片验证码重新开放，防止恶意刷发送短信 @author yecy 20180419
       String code = request.getParameter("code"); //需求N293 渠道注册优化-去掉图片验证码
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
