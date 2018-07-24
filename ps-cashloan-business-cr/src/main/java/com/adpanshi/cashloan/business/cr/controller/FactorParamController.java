package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.domain.FactorParam;
import com.adpanshi.cashloan.business.cr.service.FactorParamService;
import com.github.pagehelper.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 评分因子参数Controller
*
* @author
* @version 1.0.0
* @date 2017-01-05 11:13:30
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class FactorParamController extends BaseController {

   @Resource
   private FactorParamService factorParamService;

   /**
    * 查询评分因子参数列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factorParam/page.htm", method=RequestMethod.POST)
   public void page(
           @RequestParam(value="search",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<FactorParam> page = factorParamService.page(secreditrankhMap,current, pageSize);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }


   /**
    * 删除评分因子参数
    * @param id
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factorParam/delete.htm", method=RequestMethod.POST)
   public void delete(
           @RequestParam(value = "id") long id) throws Exception {
       Map<String,Object> result = factorParamService.deleteSelective(id);
       ServletUtils.writeToResponse(response,result);
   }

}
