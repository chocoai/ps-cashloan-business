package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.UserEmerContactsService;
import com.adpanshi.cashloan.business.core.common.anno.Checking;
import com.adpanshi.cashloan.business.core.common.anno.Validate;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.model.BindingResult;
import com.adpanshi.cashloan.business.rule.domain.UserEmerContacts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 紧急联系人表Controller
*
* @author
* @version 1.0.0
* @date 2017-02-14 11:24:05
*
*
*
*
*/
@Scope("prototype")
@Controller
public class UserEmerContactsController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(UserEmerContactsController.class);
   @Resource
   private UserEmerContactsService userEmerContactsService;

    /**
    * 保存联系人以及设备信息
    * @param name
    * @param phone
    * @param relation
    * @param type
    * @param userId
    * @param operatingSystem
    * @param systemVersions
    * @param phoneType
    * @param phoneBrand
    * @param phoneMark
    * @param versionName
    * @param versionCode
    * @param mac
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/contact/saveOrUpdate.htm", method = RequestMethod.POST)
   @Validate
   public void saveOrUpdate(
           BindingResult bindingResult,
           @Checking(value="name",required=true,json=true,maxSize=20,truncate=true) String name,
           @RequestParam(value="phone",required=true) String phone,
           @RequestParam(value="relation",required=true) String relation,
           @RequestParam(value="type",required=true) String type,
           @RequestParam(value="userId",required=true) String userId,
           @RequestParam(value = "operatingSystem", required = false) String operatingSystem,
           @RequestParam(value = "systemVersions", required = false) String systemVersions,
           @RequestParam(value = "phoneType", required = false) String phoneType,
           @RequestParam(value = "phoneBrand", required = false) String phoneBrand,
           @RequestParam(value = "phoneMark", required = false) String phoneMark,
           @RequestParam(value = "versionName", required = false) String versionName,
           @RequestParam(value = "versionCode", required = false) String versionCode,
           @RequestParam(value = "mac", required = false) String mac
           ){
       Map<String, Object> result=null;
       logger.info("用户紧急联系人信息——name:"+name+",phone:"+phone+",relation:"+relation+",type:"+type+",userId:"
               +userId+",operatingSystem:"+operatingSystem+",systemVersions:"+systemVersions+",phoneType:"+phoneType
               +",phoneBrand:"+phoneBrand+",phoneMark:"+phoneMark+",versionName:"+versionName+",versionCode:"+versionCode+",mac:"+mac);
       if(bindingResult.isPass()){
           result=bindingResult.resetResult(result);
       }else{
           result = userEmerContactsService.saveOrUpdate(name, phone, relation, type, userId,
                   operatingSystem, systemVersions, phoneType, phoneBrand, phoneMark, versionName, versionCode, mac);
       }
       // 添加银程端用户信息更新操作 @author yecy 20171128
//		Integer code = MapUtils.getInteger(result, Constant.RESPONSE_CODE);
//		if (code != null && code.equals(Constant.SUCCEED_CODE_VALUE)) {
//			yinChengFacade.updateInvestor(Long.parseLong(userId));
//		}
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * @description  获取单个用户的紧急联系人
    * @param userId 用户的id

    * @return void
    * @since  1.0.0
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/contact/getContactInfoList.htm", method = RequestMethod.GET)
   public void getContactInfoList(@RequestParam(value="userId",required=true) String userId){
       Map<String,Object> paramMap=new HashMap<String,Object>();
       paramMap.put("userId", userId);
       List<UserEmerContacts> userEmerContacts=userEmerContactsService.getUserEmerContactsByUserId(paramMap);
       Map<String,Object> result=new HashMap<String,Object>();
       Map<String,Object> temp=new HashMap<String,Object>();
       temp.put("list", userEmerContacts);
       result.put(Constant.RESPONSE_CODE,Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_DATA, temp);
       result.put(Constant.RESPONSE_CODE_MSG,"获取成功");
       ServletUtils.writeToResponse(response,result);
   }
}
