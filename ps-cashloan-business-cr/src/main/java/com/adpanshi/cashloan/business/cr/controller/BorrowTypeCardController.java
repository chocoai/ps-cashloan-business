package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.domain.BorrowTypeCard;
import com.adpanshi.cashloan.business.cr.service.BorrowTypeCardService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 评分卡类型绑定表Controller
*
* @author
* @version 1.0.0
* @date 2017-01-12 10:50:10
* Copyright 粉团网路  creditrank All Rights Reserved
*
*
*
*/
@Controller
public class BorrowTypeCardController extends BaseController {

   @Resource
   private BorrowTypeCardService borrowTypeCardService;

   /**
    * 借款类型关联分页列表
    * @param secreditrankh
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/borrowTypeCard/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value="search",required=false) String secreditrankh,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> secreditrankhMap = JsonUtil.parse(secreditrankh, Map.class);
       Page<BorrowTypeCard> page = borrowTypeCardService.page(secreditrankhMap,current, pageSize);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 借款类型关联新增
    * @param borrowTypeId
    * @param borrowTypeName
    * @param cardId
    * @param cardName
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/borrowTypeCard/save.htm", method = RequestMethod.POST)
   public void save(
           @RequestParam(value = "borrowTypeId") long borrowTypeId,
           @RequestParam(value = "borrowTypeName") String borrowTypeName,
           @RequestParam(value = "cardId") long cardId,
           @RequestParam(value = "cardName") String cardName
           ) throws Exception {
       int msg = borrowTypeCardService.save(borrowTypeId,borrowTypeName,cardId,cardName);
       Map<String,Object> result = new HashMap<String,Object>();
       if (msg>0) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "成功");
       }else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "成功");
       }
       ServletUtils.writeToResponse(response,result);
   }

   /**
    * 借款类型关联修改
    * @param id
    * @param borrowTypeId
    * @param borrowTypeName
    * @param cardId
    * @param cardName
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/com.adpanshi.cashloan.api.cr/borrowTypeCard/update.htm", method = RequestMethod.POST)
   public void update(
           @RequestParam(value = "id") long id,
           @RequestParam(value = "borrowTypeId") long borrowTypeId,
           @RequestParam(value = "borrowTypeName") String borrowTypeName,
           @RequestParam(value = "cardId") long cardId,
           @RequestParam(value = "cardName") String cardName
           ) throws Exception {
       int msg = borrowTypeCardService.update(id,borrowTypeId,borrowTypeName,cardId,cardName);
       Map<String,Object> result = new HashMap<String,Object>();
       if (msg>0) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "成功");
       }else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, "成功");
       }
       ServletUtils.writeToResponse(response,result);
   }
}
