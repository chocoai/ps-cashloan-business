package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.service.BorrowRepayService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.context.Global;
import com.adpanshi.cashloan.business.core.common.enums.BorrowRepayLogEnum;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 还款计划Controller
*
* @author
* @version 1.0.0
* @date 2017-02-14 13:42:32
*
*
*
*
*/
@Scope("prototype")
@Controller
public class BorrowRepayController extends BaseController {
    final Logger logger = LoggerFactory.getLogger(BorrowRepayController.class);

   @Resource
   private BorrowRepayService borrowRepayService;

   /**
    * 获取平台收款账号信息
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/borrow/repay/collectionInfo.htm", method = RequestMethod.GET)
   public void collectionInfo() throws Exception {
       Map<String,Object>  data = new HashMap<>();
       data.put("name", Global.getValue("repay_collection_info_name"));
       data.put("bank",  Global.getValue("repay_collection_info_bank"));
       data.put("bankCard", Global.getValue("repay_collection_info_bank_card"));
       data.put("alipayAccount",  Global.getValue("repay_collection_info_alipay_account"));
       data.put("repayWay", BorrowRepayLogEnum.REPAY_WAY.EnumValueS());
//		data.put("type", type);
       logger.info("平台收款信息:"+data);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }
}
