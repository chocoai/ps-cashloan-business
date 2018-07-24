package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.model.BorrowRepayLogModel;
import com.adpanshi.cashloan.business.cl.service.BorrowRepayLogService;
import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
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
* 还款计录Controller
*
* @author
* @version 1.0.0
* @date 2017-02-14 13:46:12
*
*
*
*
*/
@Scope("prototype")
@Controller
public class BorrowRepayLogController extends BaseController {

   @Resource
   private BorrowRepayLogService borrowRepayLogService;

   /**
    * 还款计录查询
    * @param userId
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/borrowRepayLog/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value="userId") long userId,
           @RequestParam(value="state") String state,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> searchMap = new HashMap<>();
       searchMap.put("userId", userId);
       searchMap.put("state", state);
       Page<BorrowRepayLogModel> page = borrowRepayLogService.page(searchMap,current, pageSize);
       Map<String, Object> data = new HashMap<>();
       data.put("list", page.getResult());
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, data);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }
}
