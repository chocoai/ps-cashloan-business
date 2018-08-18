package com.adpanshi.cashloan.business.controller;

import com.adpanshi.cashloan.cl.domain.OperatorReqLog;
import com.adpanshi.cashloan.cl.service.OperatorReqLogService;
import com.adpanshi.cashloan.cl.service.UserEquipmentInfoService;
import com.adpanshi.cashloan.cl.util.JSONHelper;
import com.adpanshi.cashloan.core.common.context.Constant;
import com.adpanshi.cashloan.core.common.context.Global;
import com.adpanshi.cashloan.core.common.util.ServletUtils;
import com.adpanshi.cashloan.core.common.util.StringUtil;
import com.adpanshi.cashloan.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.core.model.ManagerUserModel;
import com.adpanshi.cashloan.core.service.UserBaseInfoService;
import com.adpanshi.cashloan.dispatch.business.constant.TaskParamConstant;
import com.adpanshi.cashloan.dispatch.run.bo.DispatchRunResponseBo;
import com.adpanshi.cashloan.dispatch.run.domain.DispatchRunDomain;
import com.adpanshi.cashloan.dispatch.run.enums.StatusCode;
import com.adpanshi.cashloan.rule.domain.UserEquipmentInfo;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 8470
 * @version 1.0.1
 * @date 2018/07/14 15:44:11
 * @desc 魔蝎获取用户社交软件信息认证
 * Copyright 浙江盘石 All Rights Reserved
 */
@Controller
@Scope("prototype")
public class MXChatController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(MXChatController.class);

    @Resource
    private OperatorReqLogService operatorReqLogService;
    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;
    @Resource
    private DispatchRunDomain dispatchRunDomain;


   /**
    * @description 魔蝎FaceBook认证
    * @return void
    * @since 1.0.0
    */
   @RequestMapping(value = "/api/act/mx/chat/chatCheck.htm")
       public void chatCheck(@RequestParam(value = "userId") String userId)  {
       Map<String, Object> respMap = new HashMap<String, Object>();
       //判断每天的认证次数是否超标
       boolean isCanCredit = operatorReqLogService.checkUserOperator(Long.valueOf(userId));
       if (!isCanCredit) {
           respMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           respMap.put(Constant.RESPONSE_CODE_MSG, "今天请求认证次数超过,请明日再来认证");
       }else{
           //保存用户的请求记录
           OperatorReqLog operatorReqLog = new OperatorReqLog(Long.valueOf(userId),"","400");
           operatorReqLog.setBusinessType(OperatorReqLog.BUSINESS_TYPE_MX_CHA);
           operatorReqLogService.insert(operatorReqLog);
           respMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           respMap.put(Constant.RESPONSE_CODE_MSG, "用户请求成功");
       }

       ServletUtils.writeToResponse(response, respMap);
    }

   /**
    * 魔蝎社交爬取异步回调
    * @param request
    * @param report
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping("/api/act/mx/chatMoxieCallback.htm")
   public void chatMoxieCallback(HttpServletRequest request, String report) throws Exception {
       logger.info("------>魔蝎FaceBook认证异步回调" + request.getRequestURL()+"==report:"+report);

       //拆分存储运营商信息
       net.sf.json.JSONObject reportJson = net.sf.json.JSONObject.fromObject(report);
       Map<String, Object> reportMap = JSONHelper.JsonToMap(reportJson);
       String taskId = null;
       String userId = null;
       //第三方获取FaceBook信息成功时，code返回值为137
       if(String.valueOf(reportMap.get("code")).equals("137")){
           //获取FaceBook信息taskId
           taskId = (String)reportMap.get("task_id");
           //获取返回的数据data,获取用户userId
           Map<String,Object> dataMap = JSON.parseObject(reportMap.get("data").toString(),Map.class);
           userId = String.valueOf(dataMap.get("user_mobile"));
       }

       //根据用户userId获取最新的请求记录并更新taskId
       Map<String,Object> param = new HashMap<>();
       param.put("userId",userId);
       OperatorReqLog operatorReqLog = operatorReqLogService.findLastRecord(param);
       if(operatorReqLog!=null){
           if(!StringUtil.isEmpty(taskId)){
               param.put("respCode", "200");
               param.put("orderNo", taskId);
           }
           param.put("createTime", DateUtil.getNow());
           param.put("id", operatorReqLog.getId());
           operatorReqLogService.updateSelectRecord(param);
       }

       //调用数据调度中心转发任务,配置请求参数
       DispatchRunResponseBo dispatchRunResponseBo = null;
       if(!StringUtil.isEmpty(taskId)){
           Map<String,String> nodeParam = new HashMap<>();
           //获取用户基本信息传入到调度中心
           ManagerUserModel managerUserModel = userBaseInfoService.getBaseModelByUserId(Long.valueOf(userId));
           UserEquipmentInfo userEquipmentInfo = userEquipmentInfoService.findSelective(Long.valueOf(userId));
           //获取用户注册邮箱/设备指纹
           String email = managerUserModel.getLoginNameEmail();
           String mobile = managerUserModel.getPhone();
           String userName = managerUserModel.getRealName();
           String addNo = managerUserModel.getIdNo();
           int userDataId = managerUserModel.getUserDataId();
           String fingerPrint = userEquipmentInfo.getDeviceFinger();
           //设置节点名称
           String nodeNumber = "india_oloan_moxie_sns";
           //传入参数
           nodeParam.put("url",Global.getValue("moxie_url"));
           nodeParam.put("partner_code",Global.getValue("moxie_partner_code"));
           nodeParam.put("partner_key",Global.getValue("moxie_partner_key"));
           nodeParam.put("moxie_time_out",Global.getValue("moxie_time_out"));
           nodeParam.put("task_id",taskId);
           nodeParam.put(TaskParamConstant.THIRD_PARTY_REQUEST_PARAMJSON,nodeParam.toString());
           dispatchRunResponseBo = dispatchRunDomain.startNode(userDataId, nodeNumber, mobile, email, addNo, userName, fingerPrint, nodeParam);
       }

       Map<String,Object> result= new HashMap<String,Object>();

       if(dispatchRunResponseBo!=null &&  StatusCode.SUCCESS.getValue().equals(dispatchRunResponseBo.getRet_code())){
           result.put("message", "success");
           logger.info("用户："+userId+"-->魔盒FaceBook信息解析保存成功："+taskId);
       }else{
           result.put("message", "failure");
           logger.info("用户："+userId+"-->魔盒FaceBook信息解析保存失败："+taskId);
       }
       result.put("code", 0);
       PrintWriter writer = response.getWriter();
       writer.print(result);
       writer.flush();
       writer.close();
   }

}
