package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.UserInviteService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.domain.User;
import com.adpanshi.cashloan.business.core.service.CloanUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
* 邀请记录Controller
*
* @author
* @version 1.0.0
* @date 2017-02-18 15:54:41
*
*
*
*
*/
@Scope("prototype")
@Controller
public class UserInviteController extends BaseController {

   @Resource
   private UserInviteService userInviteService;
   @Resource
   private CloanUserService cloanUserService;

   /**
    * 电话查询
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/userInvite/findPhone.htm")
   public void findPhone(
           @RequestParam(value="userId") long userId) throws Exception {
       Map<String,Object> data = userInviteService.findPhone(userId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 邀请页面
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/userInvite/findInvite.htm")
   public void findInvite(@RequestParam(value="userId") long id)  {
       String serverHost = Global.getValue("server_host");
       int rate = (int)Double.parseDouble(Global.getValue("levelThree"));
       String path =  request.getSession().getServletContext().getRealPath("/") ;
       StringBuilder buffer = new StringBuilder(path).append(File.separatorChar)
               .append("static").append(File.separatorChar).append("images")
               .append(File.separator).append("invite_logo.png");
       User user = cloanUserService.getById(id);

       Map<String,Object> data = new HashMap<>();
       String title  = Global.getValue("title");
       String remark_invite  = Global.getValue("remark_invite");
       String url = Global.getValue("h5_invite");//邀请注册地址
       String filePath = StringUtil.isNull(buffer);

       data.put("url", url+"?invitationCode="+user.getInvitationCode());
       data.put("title",title);
       data.put("remark",remark_invite);
       data.put("inviteLogo",serverHost +"/readFile.htm?path="+filePath);
       data.put("rate", rate+"%");

       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }


}
