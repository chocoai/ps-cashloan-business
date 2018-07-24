package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.model.FactorModel;
import com.adpanshi.cashloan.business.cr.model.FactorParamModel;
import com.adpanshi.cashloan.business.cr.service.FactorParamService;
import com.adpanshi.cashloan.business.cr.service.FactorService;
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
* 评分因子Controller
*
* @author
* @version 1.0.0
* @date 2017-01-04 15:11:15
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class FactorController extends BaseController {

   @Resource
   private FactorService factorService;

   @Resource
   private FactorParamService factorParamService;

   /**
    * 查询评分因子列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factor/page.htm", method=RequestMethod.POST)
   public void page(
           @RequestParam(value="secreditrankh",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<FactorModel> page = factorService.page(secreditrankhMap,current, pageSize);
       for (FactorModel factorModel : page) {
           factorModel.setTab(factorModel.getCtable()+","+factorModel.getCcolumn());
           Map<String,Object> param = new HashMap<String,Object>();
           param.put("factorId", factorModel.getId());
           List<FactorParamModel> paramList = factorParamService.listSelect(param);
           factorModel.setChildren(paramList);
       }
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 新增修改评分因子及参数
    * @throws Exception
    */
   @SuppressWarnings({ "unchecked"})
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factor/save.htm", method=RequestMethod.POST)
   public void save(
           @RequestParam(value="factorModel") String factorModel,
           @RequestParam(value="secreditrankh") String secreditrankh
           ) throws Exception {
       List<Map<String,Object>> list = JsonUtil.parse(secreditrankh, List.class);
       Map<String,Object> factorMap = JsonUtil.parse(factorModel, Map.class);

       Map<String,Object> result = factorService.save(factorMap,list);
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 修改评分因子
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factor/update.htm", method=RequestMethod.POST)
   public void update(
           @RequestParam(value="factorModel") String factorModel,
           @RequestParam(value="secreditrankh",required=false) String secreditrankh)
                   throws Exception {
       List<Map<String,Object>> list = JsonUtil.parse(secreditrankh, List.class);
       Map<String,Object> factorMap = JsonUtil.parse(factorModel, Map.class);
       Map<String,Object> result = factorService.updateSelective(factorMap,list);
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 删除评分因子
    * @param id
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/factor/delete.htm", method=RequestMethod.POST)
   public void delete(
           @RequestParam(value = "id") long id) throws Exception {
       Map<String,Object> result = factorService.deleteSelective(id);
       ServletUtils.writeToResponse(response,result);
   }

}
