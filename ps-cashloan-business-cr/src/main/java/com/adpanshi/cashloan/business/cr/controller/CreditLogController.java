package com.adpanshi.cashloan.business.cr.controller;

import com.adpanshi.cashloan.business.core.common.context.Constant;
import com.adpanshi.cashloan.business.core.common.util.JsonUtil;
import com.adpanshi.cashloan.business.core.common.util.RdPage;
import com.adpanshi.cashloan.business.core.common.util.ServletUtils;
import com.adpanshi.cashloan.business.core.common.web.controller.BaseController;
import com.adpanshi.cashloan.business.cr.model.CreditLogModel;
import com.adpanshi.cashloan.business.cr.service.CreditLogService;
import com.github.pagehelper.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
* 授信额度记录Controller
*
* @author
* @version 1.0.0
* @date 2016-12-16 10:31:23
*
*
*
*
*/
@Scope("prototype")
@Controller
public class CreditLogController extends BaseController {

   @Resource
   private CreditLogService creditLogService;

   /**
    * 查询用户额度变动记录列表
    * @param search
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/user/creditLog/page.htm")
   public void page(
           @RequestParam(value="search",required=false) String search,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String,Object> searchMap = JsonUtil.parse(search, Map.class);
       Page<CreditLogModel> page = creditLogService.page(searchMap,current, pageSize);
       Map<String,Object> result = new HashMap<String,Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response,result);
   }

}
