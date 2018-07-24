package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.domain.CrResultDetail;
import com.adpanshi.cashloan.business.cr.model.CrResultDetailModel;
import com.adpanshi.cashloan.business.cr.service.CrResultDetailService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
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
* @date 2017-01-05 16:46:34
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class CrResultDetailController extends BaseController {

   @Resource
   private CrResultDetailService crResultDetailService;

   /**
    * 查询评分结果列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/resultDetail/page.htm", method=RequestMethod.POST)
   public void page(
           @RequestParam(value="search",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<CrResultDetail> page = crResultDetailService.page(secreditrankhMap,current, pageSize);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 查询用户评分树形结构表
    * @param resultId
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/resultDetail/detailTree.htm", method=RequestMethod.POST)
   public void detailTree(@RequestParam(value = "resultId") Long resultId){
       List<CrResultDetailModel> detail =  crResultDetailService.listDetailTree(resultId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, JSONObject.toJSON(detail));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

}
