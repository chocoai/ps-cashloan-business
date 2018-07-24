package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.domain.ProfitAmount;
import com.adpanshi.cashloan.business.cl.service.ProfitAmountService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 分润资金Controller
*
* @author
* @version 1.0.0
* @date 2017-02-18 16:29:51
*
*
*
*
*/
@Scope("prototype")
@Controller
public class ProfitAmountController extends BaseController {

   @Resource
   private ProfitAmountService profitAmountService;


   /**
    * 我的奖金
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/profitAmount/find.htm", method = RequestMethod.POST)
   public void find(
           @RequestParam(value="userId") long userId) throws Exception {
       ProfitAmount data = profitAmountService.find(userId);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "修改成功");
       ServletUtils.writeToResponse(response,result);
   }
}
