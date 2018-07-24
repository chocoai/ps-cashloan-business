package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.YoudunLog;
import com.adpanshi.cashloan.business.cl.model.UserAuthModel;
import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.cl.service.YoudunLogService;
import com.adpanshi.cashloan.business.cl.util.youdun.MD5Utils;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.UserBaseInfo;
import com.adpanshi.cashloan.business.core.service.UserBaseInfoService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 有盾Controller
 * 

 * @since 20170629
 */
@Scope("prototype")
@Controller
public class YoudunController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(YoudunController.class);

	/**
	 * 获取商户生产的SecurityKey（没有测试的key）
	 * 有盾商户号	201707126216
	 * 有盾secretkey	08fe189f-0ca7-4b4b-a5f3-c3bed9059efb
	 * 有盾pubkey、Apikey、authKey	11220614-98e2-42f8-abe4-f0f10037f6ae
	 */
	static final String YOUDUN_PUB_KEY = Global.getValue("yd_pub_key");
	static final String YOUDUN_SECURITY_KEY = Global.getValue("yd_security_key");
	static final String CHARSET_UTF_8 = "UTF-8";

	@Resource
	private UserAuthService userAuthService;
	
	@Resource
	private UserBaseInfoService userBaseInfoService;

	@Resource
	private YoudunLogService youdunLogService;

	/**
	 * 生成MD5签名
	 *
	 * @param pub_key          商户公钥
	 * @param partner_order_id 商户订单号
	 * @param sign_time        签名时间
	 * @param security_key     商户私钥
	 */
	public static String getMD5Sign(String pub_key, String partner_order_id, String sign_time, String security_key) throws UnsupportedEncodingException {
		String signStr = String.format("pub_key=%s|partner_order_id=%s|sign_time=%s|security_key=%s", pub_key, partner_order_id, sign_time, security_key);
		return MD5Utils.MD5Encrpytion(signStr.getBytes(CHARSET_UTF_8));
	}
	
	 /**
	  * 有盾活体回调地址 不可加拦截

	  * @since 20170629
	  * @param request
	  * @param response
	  * @throws Exception
	  */
	 @RequestMapping(value = "/com/adpanshi/cashloan/api/actzm/mine/youdun/livingCallBack.htm")
	 public void livingCallBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 final JSONObject reqObject = getRequestJson(request);
		 JSONObject respJson = new JSONObject();
		 //验签
		 String sign = reqObject.getString("sign");
         String sign_time = reqObject.getString("sign_time");
         String partner_order_id = reqObject.getString("partner_order_id");
         String signMD5 = getMD5Sign(YOUDUN_PUB_KEY, partner_order_id, sign_time, YOUDUN_SECURITY_KEY);
		 if (!sign.equals(signMD5)) {
			 respJson.put("code", "0");
			 respJson.put("message", "签名错误");
			 logger.error("签名错误-sign:"+sign+"-signMD5:"+signMD5);
		 } else {
			 respJson.put("code", "1");
			 respJson.put("message", "收到通知");

			 Thread asyncThread = new Thread("asyncDataHandlerThread") {
				 public void run() {
                     String idNo = reqObject.getString("id_number");//身份证号码
                     String authResult =  reqObject.getString("auth_result");//T 认证通过 F 认证不通过
                     String partnerOrderId = reqObject.getString("partner_order_id");//商户订单号
                     logger.info(" 有盾活体异步回调开始 : orderNo " + partnerOrderId);
                     try {
						 Map<String, Object> ubiMap = new HashMap<String, Object>();
						 ubiMap.put("idNo", idNo);
						 UserBaseInfo ubi = userBaseInfoService.findSelective(ubiMap);
						 if(ubi != null){
							 //更新表cl_user_auth中活体状态
							 Map<String, Object> paramMap = new HashMap<String, Object>();
							 paramMap.put("userId", ubi.getUserId());
							 if("T".equals(authResult)){
								 paramMap.put("livingIdentifyState", UserAuthModel.STATE_VERIFIED);//认证成功
							 }else {
								 paramMap.put("livingIdentifyState", UserAuthModel.STATE_FAIL);//认证失败
							 }
							 int num  = userAuthService.updateByUserId(paramMap);
							 if (num < 0) {
								 logger.error("用户userId：" + ubi.getUserId() + "有盾回调-活体认证更新出错 " + partnerOrderId);
							 }
						 }
						 //记录同盾请求

						 //begin pantheon 20170629 新增同盾请求日志
						 YoudunLog youdunLog = new YoudunLog();
						 youdunLog.setOrderNo(partnerOrderId);
                         if(ubi.getUserId() != null){
                             youdunLog.setUserId(ubi.getUserId());
                         }else{
                             youdunLog.setUserId(0L);
                         }
						 youdunLog.setBorrowId(0L);
						 youdunLog.setType(YoudunLog.TYPE_LIVING);
						 if("T".equals(authResult)){
							 youdunLog.setState("认证通过");
							 youdunLog.setRemark("人像对比认证通过");
						 }else {
							 youdunLog.setState("认证无效");
                             String failReason = reqObject.getString("fail_reason");//认证无效原因
                             youdunLog.setRemark(failReason);
						 }

						 youdunLog.setCreateTime(new Date());
						 youdunLogService.save(youdunLog);
						 logger.info("用户userId:" + ubi.getUserId() + "有盾回调-活体认证更新成功orderNo: " + partnerOrderId);
						 //end
					 } catch (Exception e) {
						 logger.error("YoudunContoller-livingCallBack()-订单号："+partnerOrderId+"有盾活体异常：",e);
					 }
				 }
			 };
			 asyncThread.start();
		 }

		 logger.error("YoudunController-livingCallBack-有盾活体请求返回参数:"+respJson.toJSONString());

		 //返回结果
		 response.setCharacterEncoding(CHARSET_UTF_8);
		 response.setContentType("application/json; charset=utf-8");
		 response.getOutputStream().write(respJson.toJSONString().getBytes(CHARSET_UTF_8));
	 }

	/**
	 * 获取请求json对象
	 * @param request
	 * @return
	 * @throws IOException
	 */
    private JSONObject getRequestJson(HttpServletRequest request) throws IOException {
        InputStream in = request.getInputStream();
        byte[] b = new byte[10240];
        int len;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = in.read(b)) > 0) {
            baos.write(b, 0, len);
        }
        String bodyText = new String(baos.toByteArray(), CHARSET_UTF_8);
        JSONObject json = (JSONObject) JSONObject.parse(bodyText);
        return json;
    }

}
