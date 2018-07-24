package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.domain.Card;
import com.adpanshi.cashloan.business.cr.service.CardService;
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
* 评分卡Controller
*
* @author
* @version 1.0.0
* @date 2017-01-04 15:06:51
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Scope("prototype")
@Controller
public class CardController extends BaseController {

   @Resource
   private CardService cardService;

   /**
    * 查询评分卡列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/card/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value="search",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<Card> page = cardService.page(secreditrankhMap,current, pageSize);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 查询所有有效评分卡
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/card/findAll.htm", method = RequestMethod.POST)
   public void findAll() throws Exception {
       List<Card> list = cardService.findAll();
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, list);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 新增评分卡
    * @param cardName
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/card/save.htm", method = RequestMethod.POST)
   public void save(
           @RequestParam(value = "cardName") String cardName
           ) throws Exception {
       Map<String,Object> result = cardService.save(cardName);
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 编辑评分卡
    * @param cardName
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/card/update.htm", method = RequestMethod.POST)
   public void update(
           @RequestParam(value = "id") long id,
           @RequestParam(value = "cardName") String cardName
           ) throws Exception {
       Map<String,Object> result = cardService.update(id,cardName);
       ServletUtils.writeToResponse(response,result);
   }


   /**
    * 启用禁用评分卡
    * @param state
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/card/updateState.htm", method = RequestMethod.POST)
   public void updateState(
           @RequestParam(value = "id") long id,
           @RequestParam(value = "state") String state) throws Exception {
       Map<String,Object> result = cardService.updateState(id,state);
       ServletUtils.writeToResponse(response,result);
   }
}
