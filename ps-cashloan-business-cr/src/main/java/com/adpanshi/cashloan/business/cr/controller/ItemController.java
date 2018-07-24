package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.model.FactorModel;
import com.adpanshi.cashloan.business.cr.model.FactorParamModel;
import com.adpanshi.cashloan.business.cr.model.ItemModel;
import com.adpanshi.cashloan.business.cr.service.FactorParamService;
import com.adpanshi.cashloan.business.cr.service.FactorService;
import com.adpanshi.cashloan.business.cr.service.ItemService;
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
* 评分项目Controller
*
* @author
* @version 1.0.0
* @date 2017-01-04 15:09:22
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class ItemController extends BaseController {

   @Resource
   private ItemService itemService;
   @Resource
   private FactorParamService factorParamService;
   @Resource
   private FactorService factorService;

   /**
    * 查询评分项目列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/item/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value="search",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<ItemModel> page = itemService.page(secreditrankhMap,current, pageSize);
       for (ItemModel itemModel : page) {
           Map<String,Object> factor = new HashMap<String,Object>();
           factor.put("itemId", itemModel.getId());
           List<FactorModel> FactorList = factorService.listFactorModel(factor);

           for (FactorModel factorModel : FactorList) {
               Map<String,Object> param = new HashMap<String,Object>();
               param.put("factorId", factorModel.getId());
               List<FactorParamModel> paramList = factorParamService.listSelect(param);
               factorModel.setChildren(paramList);
           }
           itemModel.setArticle("items");
           itemModel.setChildren(FactorList);
       }
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 新增评分项目
    * @param itemName
    * @param cardId
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/item/save.htm", method = RequestMethod.POST)
   public void save(
           @RequestParam(value = "itemName") String itemName,
           @RequestParam(value = "cardId") long cardId) throws Exception {
       Map<String,Object> result = itemService.save(itemName,cardId);
       ServletUtils.writeToResponse(response,result);
   }


   /**
    * 查询评分项目列表
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/item/list.htm", method = RequestMethod.POST)
   public void list(@RequestParam(value = "cardId") long cardId)throws Exception {
       Map<String,Object> result = itemService.list(cardId);
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 删除评分项目
    * @param id
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/item/delete.htm", method = RequestMethod.POST)
   public void delete(@RequestParam(value = "id") long id)throws Exception {
       Map<String,Object> result = itemService.deleteSelective(id);
       ServletUtils.writeToResponse(response,result);
   }
}
