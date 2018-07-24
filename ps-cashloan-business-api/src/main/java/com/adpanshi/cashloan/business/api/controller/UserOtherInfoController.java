package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.UserAuthService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.UserOtherInfo;
import com.adpanshi.cashloan.business.core.service.UserOtherInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 用户更多信息Controller
*

* @version 1.0.0
* @date 2017-03-14 19:37:25
*
*
*
*
*/
@Scope("prototype")
@Controller
public class UserOtherInfoController extends BaseController {

   @Resource
   private UserOtherInfoService userOtherInfoService;

   @Resource
   private UserAuthService userAuthService;


   /**
    * 保存或修改用户其他信息
    * @param userId
    * @param taobao
    * @param email
    * @param qq
    * @param wechat
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/other/saveOrUpdate.htm", method = RequestMethod.POST)
   public void saveOrUpdate(
           @RequestParam(value = "userId", required = true) Long userId,
           @RequestParam(value = "taobao", required = true) String taobao,
           @RequestParam(value = "email", required = true) String email,
           @RequestParam(value = "qq", required = true) String qq,
           @RequestParam(value = "wechat", required = true) String wechat) {

       if(!StringUtil.isMail(email)){
           Map<String, Object> result = new HashMap<String, Object>();
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "邮箱格式错误");
           ServletUtils.writeToResponse(response, result);
           return ;
       }

       UserOtherInfo info = userOtherInfoService.getInfoByUserId(userId);
       boolean flag;
       if (null != info) {
           Map<String, Object> paramMap = new HashMap<String, Object>();
           paramMap.put("id", info.getId());
           paramMap.put("taobao", taobao);
           paramMap.put("email", email);
           paramMap.put("qq", qq);
           paramMap.put("wechat", wechat);
           flag = userOtherInfoService.updateSelective(paramMap);
       } else {
           UserOtherInfo otherInfo = new UserOtherInfo();
           otherInfo.setUserId(userId);
           otherInfo.setTaobao(taobao);
           otherInfo.setEmail(email);
           otherInfo.setQq(qq);
           otherInfo.setWechat(wechat);
           flag = userOtherInfoService.save(otherInfo);
       }


       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "保存失败");
       }

       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 保存或修改用户其他信息
    *
    * @param userId
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/other/findDetail.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
   public void findDetail(
           @RequestParam(value = "userId", required = true) Long userId) {
       UserOtherInfo info = userOtherInfoService.getInfoByUserId(userId);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, info);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

}
