package com.adpanshi.cashloan.business.api.controller;

import com.adpanshi.cashloan.business.cl.model.ProfitLogModel;
import com.adpanshi.cashloan.business.cl.service.ProfitLogService;
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
* 分润记录Controller
*
* @author
* @version 1.0.0
* @date 2017-02-18 17:04:10
*
*
*
*
*/
@Scope("prototype")
@Controller
public class ProfitLogController extends BaseController {

   @Resource
   private ProfitLogService profitLogService;

   /**
    * 分润记录查询
    * @param borrowId
    * @param userId
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @RequestMapping(value = "/com/adpanshi/cashloan/api/act/mine/profitLog/page.htm", method = RequestMethod.POST)
   public void page(
           @RequestParam(value="userId") long agentId,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> searchMap = new HashMap<>();
       searchMap.put("agentId", agentId);
       Page<ProfitLogModel> page = profitLogService.page(searchMap,current, pageSize);
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
