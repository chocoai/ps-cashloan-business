package com.adpanshi.cashloan.business.cr.controller;


import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.domain.CrResult;
import com.adpanshi.cashloan.business.cr.service.CrResultService;
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
* 评分结果Controller
*

* @version 1.0.0
* @date 2017-01-05 16:22:54
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class CrResultController extends BaseController {

   @Resource
   private CrResultService crResultService;

    /**
     * @description 评分结果查询
     * @param userId
     * */
   @RequestMapping(value="/modules/manage/com.adpanshi.cashloan.api.cr/result/findUserResult.htm",method=RequestMethod.POST)
   public void findUserResult(@RequestParam(value = "userId")Long userId){
       crResultService.findUserResult(userId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
       ServletUtils.writeToResponse(response,result);
   }

    /**
     * @description 评分类型查询
     * @param userId
     * */
   @RequestMapping(value="/modules/manage/com.adpanshi.cashloan.api.cr/result/findAllBorrowTypeResult.htm",method=RequestMethod.POST)
   public void findAllBorrowTypeResult(@RequestParam(value = "userId")Long userId){
       List<CrResult> resultList = crResultService.findAllBorrowTypeResult(userId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, resultList);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
       ServletUtils.writeToResponse(response,result);
   }

    /**
     * @description 评分类型查询
     * @param userId
     * @param borrowTypeId
     * */
   @RequestMapping(value="/modules/manage/com.adpanshi.cashloan.api.cr/result/findBorrowTypeResult.htm",method=RequestMethod.POST)
   public void findBorrowTypeResult(@RequestParam(value = "userId")Long userId,
           @RequestParam(value = "borrowTypeId")Long borrowTypeId){
       List<CrResult> resultList = crResultService.findAllBorrowTypeResult(userId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, resultList);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
       ServletUtils.writeToResponse(response,result);
   }

}
