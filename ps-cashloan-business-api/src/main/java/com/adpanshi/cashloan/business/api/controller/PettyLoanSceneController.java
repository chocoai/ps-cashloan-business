package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.extra.BorrowIntent;
import com.adpanshi.cashloan.business.cl.extra.HandleBorrowIntent;
import com.adpanshi.cashloan.business.cl.service.impl.UserService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.core.umeng.beans.Extra;
import com.adpanshi.cashloan.business.system.domain.SysDictDetail;
import com.adpanshi.cashloan.business.system.service.SysDictDetailService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 ** @category 场景控件器...
 ** @author qing.yunhui
 ** @email: qingyunhui@fentuan360.com
 ** @createTime: 2018年2月2日下午5:31:22
 **/
@Controller
@Scope("prototype")
@RequestMapping(PettyLoanSceneController.ACTION_PATH)
public class PettyLoanSceneController extends BaseController{

	final Logger logger = LoggerFactory.getLogger(PettyLoanSceneController.class);

    final static String ACTION_PATH= "/api/act/pettyLoanScene";
    
    @Autowired
    SysDictDetailService sysDictDetailService;
    
    @Autowired
    UserService userService;
    


    /**
     * <p>进入借款意图初始化页面</p>
     * */
    @RequestMapping(value = "/intoBorrowIntent.htm", method = RequestMethod.POST)
  	public void intoBorrowIntent(@RequestParam(value="userId")Long userId)throws Exception {
    	List<SysDictDetail> dictDetails=sysDictDetailService.listByTypeCode(new Extra("typeCode", "BORROW_TYPE"));
    	List<BorrowIntent> borrowIntentList= HandleBorrowIntent.handlDictDetail(dictDetails, userId);
    	Map<String,Object> data=new HashMap<>();
    	logger.info("----------->借款场景:{}.",new Object[]{JSONObject.toJSONString(borrowIntentList)});
		Map<String,Object> result = new HashMap<String,Object>();
		boolean isPwd=userService.setUserPassword(userId);
		data.put("isPwd", isPwd);
		data.put("list", borrowIntentList);
		result.put(Constant.RESPONSE_DATA, data); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
    }
    
}
